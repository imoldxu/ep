package com.ly.service.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.User;
import com.ly.service.mapper.UserMapper;
import com.ly.service.utils.IdCardUtil;
import com.ly.service.utils.PasswordUtil;
import com.ly.service.utils.RedissonUtil;
import com.ly.service.utils.ValidDataUtil;
import com.ly.service.utils.WxUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserService {

	@Autowired
	RedissonUtil redissonUtil;
	@Autowired
	UserMapper userMapper;
	
	public User loginByWx(String wxCode) throws IOException{

		JsonNode wxOauthInfo = WxUtil.getOauthInfo(wxCode);
		String accessToken = null;
		accessToken = (String)redissonUtil.get("wechat_access_token");
		
		JsonNode wxUserInfo = WxUtil.getUserInfo2(accessToken, wxOauthInfo);
		// 获得微信的数据
		String unionID = wxUserInfo.get("unionid").asText();
		String headerImgURL = wxUserInfo.get("headimgurl").asText();
		if(null != headerImgURL &&  !headerImgURL.isEmpty()){
			//去掉微信图片为0的标号
			headerImgURL = headerImgURL.substring(0, headerImgURL.length()-3);
			//设置微信图片为64的标号
			headerImgURL = headerImgURL+"64";
		}
		String wxnick = wxUserInfo.get("nickname").asText();
		String nick = WxUtil.converWxNick(wxnick);
		// 获取微信账号对应的账号
		Example wxUserExample = new Example(User.class);
		wxUserExample.createCriteria().andEqualTo("wxunionid", unionID);
		wxUserExample.setOrderByClause("id asc");
		User user = userMapper.selectOneByExample(wxUserExample);
		if (user == null) {
			// 微信用户未注册
			user = new User();
			user.setHeadimgurl(headerImgURL);
			user.setNick(nick);
			user.setWxunionid(unionID);
			user.setCreatetime(new Date());
			userMapper.insertUseGeneratedKeys(user);
		} else {
			// 微信用户已经注册
			// 更新用户的昵称和头像
			user.setHeadimgurl(headerImgURL);
			user.setNick(nick);
			userMapper.updateByPrimaryKey(user);			
		}
		
		if(user.getIdcardtype() == User.TYPE_IDCARD){
			String idcard = user.getIdcardnum();
			if(idcard!=null && !idcard.isEmpty()){
				try{
					int age = IdCardUtil.getAge(idcard);
					user.setAge(age);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		JsonNode subscribeNode = wxUserInfo.get("subscribe");
		if(subscribeNode!=null){
			int subscribe = subscribeNode.intValue();
			user.setSubscribe(subscribe);
		}else{
			user.setSubscribe(0);//视为没有关注
		}
		
		return user;
	}

	public void updateInfo(int uid, String name, String phone, int idcardtype, String idcardnum) {
		
		User user = userMapper.selectByPrimaryKey(uid);
		if(user == null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "系统异常,用户不存在");
		}
		if(idcardtype != User.TYPE_IDCARD && idcardtype != User.TYPE_JG){
			throw new HandleException(ErrorCode.ARG_ERROR, "证件类型异常,请检查客户端");
		}
		if(idcardtype==User.TYPE_IDCARD){
			if(!IdCardUtil.isIDCard(idcardnum)){
				throw new HandleException(ErrorCode.NORMAL_ERROR, "身份证号有误,请检查");
			}
		}
		if(!ValidDataUtil.isPhone(phone)){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号有误,请检查"); 
		}
		
		user.setName(name);
		user.setPhone(phone);
		user.setIdcardtype(idcardtype);
		user.setIdcardnum(idcardnum);
		
		userMapper.updateByPrimaryKey(user);
	}

	public User login(String phone, String password) {
		Example ex = new Example(User.class);
		ex.createCriteria().andEqualTo("phone", phone);
		User user = userMapper.selectOneByExample(ex);
		if(user == null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}else{
			if(PasswordUtil.isEqual(user.fetchPassword(), password, user.fetchPwdnonce())){
				return user;
			}else{
				throw new HandleException(ErrorCode.NORMAL_ERROR, "密码错误");
			}
		}
	}

	public void register(String phone, String password) {
		if(!ValidDataUtil.isPhone(phone)){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号有误,请检查"); 
		}
		
		Example ex = new Example(User.class);
		ex.createCriteria().andEqualTo("phone", phone);
		User user = userMapper.selectOneByExample(ex);
		if(user != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户已存在");
		}else{
			user = new User();
			user.setPhone(phone);
			
			String nonce = PasswordUtil.generateNonce();
			user.setPwdnonce(nonce);
			
			String newPwd = PasswordUtil.generatePwd(password, nonce);
			user.setPassword(newPwd);
			
			userMapper.insertUseGeneratedKeys(user);
		}
	}

	public List<User> getAllUser(Integer pageIndex, Integer pageSize) {
		Example ex = new Example(User.class);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize,pageSize);
		List<User> ret = userMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return ret;
	}
}
