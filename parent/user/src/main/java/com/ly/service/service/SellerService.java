//package com.ly.service.service;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.ly.service.context.ErrorCode;
//import com.ly.service.context.HandleException;
//import com.ly.service.entity.Seller;
//import com.ly.service.mapper.SellerMapper;
//import com.ly.service.utils.PasswordUtil;
//import com.ly.service.utils.RedissonUtil;
//import com.ly.service.utils.WxUtil;
//
//import tk.mybatis.mapper.entity.Example;
//
//@Service
//public class SellerService {
//
//	@Autowired
//	SellerMapper sellerMapper;
//	@Autowired
//	RedissonUtil redissonUtil;
//	
//	public Seller loginByWx(String wxCode) throws IOException{
//		JsonNode wxOauthInfo = WxUtil.getOauthInfo(wxCode);
//		String accessToken = null;
//		accessToken = (String)redissonUtil.get("wechat_access_token");
//		
//		JsonNode wxUserInfo = WxUtil.getUserInfo2(accessToken, wxOauthInfo);
//		// 获得微信的数据
//		String unionID = wxUserInfo.get("unionid").asText();
//		String headerImgURL = wxUserInfo.get("headimgurl").asText();
//		if(null != headerImgURL &&  !headerImgURL.isEmpty()){
//			//去掉微信图片为0的标号
//			headerImgURL = headerImgURL.substring(0, headerImgURL.length()-3);
//			//设置微信图片为64的标号
//			headerImgURL = headerImgURL+"64";
//		}
//		//String wxnick = wxUserInfo.get("nickname").asText();
//		//String nick = WxUtil.converWxNick(wxnick);
//		// 获取微信账号对应的账号
//		Example ex = new Example(Seller.class);
//		ex.createCriteria().andEqualTo("wxunionid", unionID);
//		ex.setOrderByClause("id asc");
//		Seller seller = sellerMapper.selectOneByExample(ex);
//		if (seller ==  null) {
//			// 微信用户未注册
//			seller = new Seller();
//			seller.setWxunionid(unionID);
//			seller.setCreatetime(new Date());
//			sellerMapper.insertUseGeneratedKeys(seller);
//		} else {
//			// 微信用户已经注册
//			// 更新用户的昵称和头像
//			sellerMapper.updateByPrimaryKey(seller);			
//		}
//		
////		String idcard = user.getIdCard();
////		if(idcard!=null && !idcard.isEmpty()){
////			try{
////				int age = IdCardUtil.getAge(idcard);
////				user.setAge(age);
////			}catch (Exception e) {
////				e.printStackTrace();
////			}
////		}
//		
//		JsonNode subscribeNode = wxUserInfo.get("subscribe");
//		if(subscribeNode!=null){
//			int subscribe = subscribeNode.intValue();
//			seller.setSubscribe(subscribe);
//		}else{
//			seller.setSubscribe(0);//视为没有关注
//		}
//		
//		return seller;
//	}
//
//	public Seller login(String phone, String password) {
//		Example ex = new Example(Seller.class);
//		ex.createCriteria().andEqualTo("phone", phone);
//		Seller seller = sellerMapper.selectOneByExample(ex);
//		if(seller == null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
//		}else{
//			if(PasswordUtil.isEqual(seller.fetchPassword(), password, seller.fetchPwdnonce())){
//				return seller;
//			}else{
//				throw new HandleException(ErrorCode.NORMAL_ERROR, "用户密码错误");
//			}
//		}
//	}
//	
//	public boolean modifypassword(Integer sellerId, String oldPassword, String newPassword) {
//		Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
//		if(seller == null){
//			throw new HandleException(ErrorCode.ARG_ERROR, "用户不存在");
//		}else{
//			if(PasswordUtil.isEqual(seller.fetchPassword(),oldPassword, seller.fetchPwdnonce())){
//				String newPwd = PasswordUtil.generatePwd(newPassword, seller.fetchPwdnonce());
//				seller.setPassword(newPwd);
//				sellerMapper.updateByPrimaryKey(seller);
//				return true;
//			}else{
//				throw new HandleException(ErrorCode.NORMAL_ERROR, "用户密码错误");
//			}
//		}
//	}
//
//	public Seller register(String name, String phone, String password) {
//		Example ex = new Example(Seller.class); 
//		ex.createCriteria().andEqualTo("phone", phone);
//		Seller seller = sellerMapper.selectOneByExample(ex);
//		if(seller!=null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户已存在");
//		}
//		seller = new Seller();
//		seller.setCreatetime(new Date());
//		seller.setName(name);
//		seller.setPhone(phone);
//		
//		String nonce = PasswordUtil.generateNonce();
//		seller.setPwdnonce(nonce);
//		String newPwd = PasswordUtil.generatePwd(password, nonce);
//		seller.setPassword(newPwd);
//		
//		sellerMapper.insertUseGeneratedKeys(seller);
//		return null;
//	}
//
//	public Seller bind(Integer sellerId, String phone, String password) {
//		Example ex = new Example(Seller.class);
//		ex.createCriteria().andEqualTo("phone", phone);
//		Seller seller = sellerMapper.selectOneByExample(ex);
//		if(seller == null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "绑定账户不存在");
////			seller = sellerMapper.selectByPrimaryKey(sellerId);
////			if(seller == null){
////				throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
////			}else{
////				seller.setPhone(phone);
////				String nonce = PasswordUtil.generateNonce();
////				seller.setPwdnonce(nonce);
////				
////				String newPwd = PasswordUtil.generatePwd(password, nonce);
////				seller.setPassword(newPwd);
////				
////				sellerMapper.updateByPrimaryKey(seller);
////			}
//		}else{	
//			if(sellerId == seller.getId()){//若绑定的账号与账户对应的账号相同，则不做任何处理
//				return seller;
//			}
//			Seller wxSeller = sellerMapper.selectByPrimaryKey(sellerId);
//			
//			if(PasswordUtil.isEqual(seller.fetchPassword(), password, seller.fetchPwdnonce())){
//				seller.setWxunionid(wxSeller.fetchWxunionid());
//				sellerMapper.updateByPrimaryKey(seller);
//				sellerMapper.deleteByPrimaryKey(wxSeller);
//			}else{
//				throw new HandleException(ErrorCode.NORMAL_ERROR, "用户密码错误");
//			}
//		}
//		return seller;
//	}
//
//	public List<Seller> getAllSeller(Integer pageIndex, Integer pageSize) {
//		Example ex = new Example(Seller.class);
//		ex.setOrderByClause("id DESC");
//		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize,pageSize);
//		List<Seller> ret = sellerMapper.selectByExampleAndRowBounds(ex, rowBounds);
//		return ret;
//		
//	}
//
//	public Seller getSeller(Integer sellerid) {
//		Seller ret = sellerMapper.selectByPrimaryKey(sellerid);
//		if(ret == null){
//			throw new HandleException(ErrorCode.ARG_ERROR, "该销售不存在");
//		}
//		return ret;
//	}
//}
