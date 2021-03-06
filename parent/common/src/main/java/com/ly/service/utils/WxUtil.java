package com.ly.service.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class WxUtil {
	
	public static final String grant_type = "client_credential";
	public static final String appid = "wxda3d0bef1e385508";
	public static final String secret = "74703e0869ecad07aaa45c2cd3d427e8";
	
	public static final String app_appid = "";
	public static final String app_secret = "";

//	
//	public static Map<String, String> sign(String url) {
//		String token = getToken();
//		String ticket = getTicket(token);
//		return sign(ticket, url);
//	}

	private static String getToken(String grant_type, String appid, String secret) {
		HttpClientUtil h = new HttpClientUtil();
		JsonNode node = null;
		try {
			h.open("https://api.weixin.qq.com/cgi-bin/token", "get");
			h.addParameter("grant_type", grant_type);
			h.addParameter("appid", appid);
			h.addParameter("secret", secret);

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if(status == 200){
				String context = h.getResponseBodyAsString("utf-8");
				node = JSONUtils.getJsonObject(context);
				return node.get("access_token").asText();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			h.close();
		}
		return null;
	}

	public static String getTicket(String token) {
		HttpClientUtil h = new HttpClientUtil();
		JsonNode node = null;
		try {
			h.open("https://api.weixin.qq.com/cgi-bin/ticket/getticket", "get");
			h.addParameter("access_token", token);
			h.addParameter("type", "jsapi");

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if(status==200){
				String context = h.getResponseBodyAsString("utf-8");
				node = JSONUtils.getJsonObject(context);
				System.out.println(context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			h.close();
		}
		return node.get("ticket").asText();
	}

	/**
	   * 公众号调用wx，jsapi需要先进行签名
	 * @jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		//System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("appId", appid);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 公众号获取access_token
	 * @return
	 */
	public static String getToken() {
		return getToken(grant_type, appid, secret);
	}

	/**
	   * 服务号获取授权
	 * @param wxCode
	 * @return
	 * @throws IOException
	 */
	public static JsonNode getOauthInfo(String wxCode) throws IOException {
		HttpClientUtil h = new HttpClientUtil();
		JsonNode ret = null;
		try {
			h.open("https://api.weixin.qq.com/sns/oauth2/access_token", "get");
			h.addParameter("appid", appid);
			h.addParameter("secret", secret);
			h.addParameter("code", wxCode);
			h.addParameter("grant_type", "authorization_code");

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if (200 == status) {
				String response = h.getResponseBodyAsString("utf-8");
				ret = JSONUtils.getJsonObject(response);
				JsonNode errorcode = ret.get("errcode");
				if (null != errorcode) {
					String errMsg = ret.get("errmsg").asText();
					throw new IOException("微信授权请求错误:" + errMsg);
				} else {
					return ret;
				}
			} else {
				throw new IOException("微信服务器请求失败");
			}
		} finally {
			h.close();
		}
	}

	public static JsonNode getOauthInfobyAPP(String wxCode) throws IOException {
		HttpClientUtil h = new HttpClientUtil();
		JsonNode ret = null;
		try {
			h.open("https://api.weixin.qq.com/sns/oauth2/access_token", "get");
			h.addParameter("appid", app_appid);
			h.addParameter("secret", app_secret);
			h.addParameter("code", wxCode);
			h.addParameter("grant_type", "authorization_code");

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if (200 == status) {
				String response = h.getResponseBodyAsString("utf-8");
				ret = JSONUtils.getJsonObject(response);
				JsonNode errorcode = ret.get("errcode");
				if (null != errorcode) {
					System.out.println("weChat errorCode is:"+errorcode);
					String errMsg = ret.get("errmsg").asText();
					throw new IOException("微信授权请求错误:" + errMsg);
				} else {
					return ret;
				}
			} else {
				throw new IOException("微信服务器请求失败");
			}
		} finally {
			h.close();
		}
	}
	
	public static JsonNode getUserInfo(JsonNode wxOauthInfo) throws IOException {
		HttpClientUtil h = new HttpClientUtil();
		JsonNode ret = null;
		try {
			h.open("https://api.weixin.qq.com/sns/userinfo", "get");
			h.addParameter("access_token", wxOauthInfo.get("access_token").asText());
			h.addParameter("openid", wxOauthInfo.get("openid").asText());
			h.addParameter("lang", "zh_CN");

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if (200 == status) {
				String response = h.getResponseBodyAsString("utf-8");
				ret = JSONUtils.getJsonObject(response);
				JsonNode errorcode = ret.get("errcode");
				if (null != errorcode) {
					String errmsg = ret.get("errmsg").asText();
					throw new IOException("微信获取用户信息错误:" + errmsg);
				} else {
					return ret;
				}
			} else {
				throw new IOException("微信服务器请求失败");
			}
		}  finally {
			h.close();
		}
	}
	
	//使用关注了公众号的用户来获取用户信息
	public static JsonNode getUserInfo2(String accessToken, JsonNode wxOauthInfo) throws IOException {
		HttpClientUtil h = new HttpClientUtil();
		JsonNode ret = null;
		try {
			h.open("https://api.weixin.qq.com/cgi-bin/user/info", "get");
			h.addParameter("access_token", accessToken);
			h.addParameter("openid", wxOauthInfo.get("openid").asText());
			h.addParameter("lang", "zh_CN");

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if (200 == status) {
				String response = h.getResponseBodyAsString("utf-8");
				ret = JSONUtils.getJsonObject(response);
				JsonNode errorcode = ret.get("errcode");
				if (null != errorcode) {
					String errmsg = ret.get("errmsg").asText();
					throw new IOException("微信获取用户信息错误:" + errmsg);
				} else {
					int subscribe = ret.get("subscribe").intValue();
					if(subscribe==0){
						//若没有关注，就从SNS方式获取用户信息
						return getUserInfo(wxOauthInfo); 
					}else{
						return ret;
					}
				}
			} else {
				throw new IOException("微信服务器请求失败");
			}
		}  finally {
			h.close();
		}
	}
	
	/**
	    * 处理微信昵称中的特殊符号
	 * @param wxnick
	 * @return
	 */
	public static String converWxNick(String wxnick){
		String regEx = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(wxnick);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 发送模板消息
	 * @param openid
	 * @param access_token
	 * @param userNick
	 * @param orderid
	 * @param giftName
	 * @param money
	 * @param time
	 * @return
	 * @throws IOException
	 */
	public static boolean pushTemplateMsg(String openid, String access_token, String userNick, String orderid,String giftName, String money, String time ) throws IOException{
		HttpClientUtil h = new HttpClientUtil();
		JsonNode ret = null;
		try {
			h.open("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, "post");
			
			HashMap<String, Object> map = new HashMap<>();
			
			String msg = "{\"first\": {\"value\":\"携玩会员:"+ userNick +"完成了兑换\",\"color\":\"#0000FF\"},"
					+ "\"keyword1\":{\"value\":\""+orderid+"\",\"color\":\"#0000FF\"},"
					+ "\"keyword2\":{\"value\":\""+giftName+"\",\"color\":\"#0000FF\"},"
					+ "\"keyword3\":{\"value\":\""+money+"\",\"color\":\"#0000FF\"},"
					+ "\"keyword4\":{\"value\":\""+time+"\",\"color\":\"#0000FF\"},"
					+"\"remark\":{\"value\":\"请提供礼品\",\"color\":\"#0000FF\"}}";
			
			JsonNode msgObj = JSONUtils.getJsonObject(msg);
			
			//h.addParameter("touser", openid);
			//h.addParameter("template_id", "-QqtuXZZlcYVTQm3_sRSb_tgGymougL7onz1EbjB0rY");
			//h.addParameter("url", "http://weixin.qq.com/download");
			//h.addParameter("data", xxx);
			map.put("touser", openid);
			map.put("template_id", "-QqtuXZZlcYVTQm3_sRSb_tgGymougL7onz1EbjB0rY");
			map.put("url", "http://weixin.qq.com/download");
			map.put("data", msgObj);
			
			JsonNode postData = JSONUtils.getJsonObject(map);
			
			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			
			int status = h.postJson(postData.toString());
			if (200 == status) {
				String response = h.getResponseBodyAsString("utf-8");
				ret = JSONUtils.getJsonObject(response);
				JsonNode errorcode = ret.get("errcode");
				if (errorcode != null && 0 != errorcode.asInt()) {
					String errmsg = ret.get("errmsg").asText();
					System.out.println("微信推送模板消息失败:" + errmsg);
					return false;
				} else {
					return true;
				}
			} else {
				throw new IOException("微信服务器请求失败");
			}
		} finally {
			h.close();
		}
	}
}
