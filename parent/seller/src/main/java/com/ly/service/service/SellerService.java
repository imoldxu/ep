package com.ly.service.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Seller;
import com.ly.service.mapper.SellerMapper;
import com.ly.service.utils.RedissonUtil;
import com.ly.service.utils.WxUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class SellerService {

	@Autowired
	SellerMapper sellerMapper;
	@Autowired
	RedissonUtil redissonUtil;
	
	public Seller loginByWx(String wxCode) throws IOException{
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
		//String wxnick = wxUserInfo.get("nickname").asText();
		//String nick = WxUtil.converWxNick(wxnick);
		// 获取微信账号对应的账号
		Example ex = new Example(Seller.class);
		ex.createCriteria().andEqualTo("wxunionid", unionID);
		ex.setOrderByClause("id asc");
		Seller seller = sellerMapper.selectOneByExample(ex);
		if (seller ==  null) {
			// 微信用户未注册
			seller = new Seller();
			seller.setWxunionid(unionID);
			seller.setCreatetime(new Date());
			sellerMapper.insert(seller);
		} else {
			// 微信用户已经注册
			// 更新用户的昵称和头像
			sellerMapper.updateByPrimaryKey(seller);			
		}
		
//		String idcard = user.getIdCard();
//		if(idcard!=null && !idcard.isEmpty()){
//			try{
//				int age = IdCardUtil.getAge(idcard);
//				user.setAge(age);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		JsonNode subscribeNode = wxUserInfo.get("subscribe");
		if(subscribeNode!=null){
			int subscribe = subscribeNode.intValue();
			seller.setSubscribe(subscribe);
		}else{
			seller.setSubscribe(0);//视为没有关注
		}
		
		return seller;
	}

	public Seller login(String phone, String password) {
		Example ex = new Example(Seller.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Seller seller = sellerMapper.selectOneByExample(ex);
		if(seller == null){
			throw new HandleException(-1, "用户不存在");
		}else{
			if(seller.getPassword().equals(password)){
				return seller;
			}else{
				throw new HandleException(-1, "用户密码错误");
			}
		}
	}
	
}
