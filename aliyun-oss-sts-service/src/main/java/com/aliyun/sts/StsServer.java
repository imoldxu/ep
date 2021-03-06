package com.aliyun.sts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import net.sf.json.JSONObject;

//import org.junit.Assert;

@SuppressWarnings("deprecation")
@WebServlet(asyncSupported = true)
public class StsServer extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5522372203700422672L;

	/**
	 * Get请求 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String endpoint = "sts.aliyuncs.com";
        String accessKeyId = "LTAIaTjgSfIm9bhz";
        String accessKeySecret = "Rap48G4AxTEhicylx2mS22UsbkG6cu";
        String roleArn = "acs:ram::1165236721947216:role/osssts";
        String roleSessionName = "zm";
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "", "Sts", endpoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest req = new AssumeRoleRequest();
            req.setMethod(MethodType.POST);
            req.setRoleArn(roleArn);
            req.setRoleSessionName(roleSessionName);
            req.setPolicy(policy); // 若policy为空，则用户将获得该角色下所有权限
            req.setDurationSeconds(1200L); // 设置凭证有效时间
            final AssumeRoleResponse resp = client.getAcsResponse(req);
            System.out.println("Expiration: " + resp.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + resp.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + resp.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + resp.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + resp.getRequestId());
            response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST");
			response.setContentType("application/json");
			Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("SecurityToken", resp.getCredentials().getSecurityToken());
            respMap.put("AccessKeySecret", resp.getCredentials().getAccessKeySecret());
            respMap.put("AccessKeyId", resp.getCredentials().getAccessKeyId());
            respMap.put("Expiration", resp.getCredentials().getExpiration());
      
			JSONObject ja1 = JSONObject.fromObject(respMap);
			response(request, response, ja1.toString());

        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
	}

//	/**
//	 * 获取public key
//	 * 
//	 * @param url
//	 * @return
//	 */
//	@SuppressWarnings({ "finally" })
//	public String executeGet(String url) {
//		BufferedReader in = null;
//
//		String content = null;
//		try {
//			// 定义HttpClient
//			@SuppressWarnings("resource")
//			DefaultHttpClient client = new DefaultHttpClient();
//			// 实例化HTTP方法
//			HttpGet request = new HttpGet();
//			request.setURI(new URI(url));
//			HttpResponse response = client.execute(request);
//
//			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//			StringBuffer sb = new StringBuffer("");
//			String line = "";
//			String NL = System.getProperty("line.separator");
//			while ((line = in.readLine()) != null) {
//				sb.append(line + NL);
//			}
//			in.close();
//			content = sb.toString();
//		} catch (Exception e) {
//		} finally {
//			if (in != null) {
//				try {
//					in.close();// 最后要关闭BufferedReader
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			return content;
//		}
//	}
//
//	/**
//	 * 获取Post消息体
//	 * 
//	 * @param is
//	 * @param contentLen
//	 * @return
//	 */
//	public String GetPostBody(InputStream is, int contentLen) {
//		if (contentLen > 0) {
//			int readLen = 0;
//			int readLengthThisTime = 0;
//			byte[] message = new byte[contentLen];
//			try {
//				while (readLen != contentLen) {
//					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
//					if (readLengthThisTime == -1) {// Should not happen.
//						break;
//					}
//					readLen += readLengthThisTime;
//				}
//				return new String(message);
//			} catch (IOException e) {
//			}
//		}
//		return "";
//	}
//
//	/**
//	 * 验证上传回调的Request
//	 * 
//	 * @param request
//	 * @param ossCallbackBody
//	 * @return
//	 * @throws NumberFormatException
//	 * @throws IOException
//	 */
//	protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
//			throws NumberFormatException, IOException {
//		boolean ret = false;
//		String autorizationInput = new String(request.getHeader("Authorization"));
//		String pubKeyInput = request.getHeader("x-oss-pub-key-url");
//		byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
//		byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
//		String pubKeyAddr = new String(pubKey);
//		if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
//				&& !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
//			System.out.println("pub key addr must be oss addrss");
//			return false;
//		}
//		String retString = executeGet(pubKeyAddr);
//		retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
//		retString = retString.replace("-----END PUBLIC KEY-----", "");
//		String queryString = request.getQueryString();
//		String uri = request.getRequestURI();
//		String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
//		String authStr = decodeUri;
//		if (queryString != null && !queryString.equals("")) {
//			authStr += "?" + queryString;
//		}
//		authStr += "\n" + ossCallbackBody;
//		ret = doCheck(authStr, authorization, retString);
//		return ret;
//	}
//
//	/**
//	 * Post请求
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String ossCallbackBody = GetPostBody(request.getInputStream(),
//				Integer.parseInt(request.getHeader("content-length")));
//		boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
//		System.out.println("verify result : " + ret);
//		// System.out.println("OSS Callback Body:" + ossCallbackBody);
//		if (ret) {
//			response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
//		} else {
//			response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
//		}
//	}
//
//	/**
//	 * 验证RSA
//	 * 
//	 * @param content
//	 * @param sign
//	 * @param publicKey
//	 * @return
//	 */
//	public static boolean doCheck(String content, byte[] sign, String publicKey) {
//		try {
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
//			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//			java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
//			signature.initVerify(pubKey);
//			signature.update(content.getBytes());
//			boolean bverify = signature.verify(sign);
//			return bverify;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return false;
//	}

	/**
	 * 服务器响应结果
	 * 
	 * @param request
	 * @param response
	 * @param results
	 * @param status
	 * @throws IOException
	 */
	private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
			throws IOException {
		String callbackFunName = request.getParameter("callback");
		response.addHeader("Content-Length", String.valueOf(results.length()));
		if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
			response.getWriter().println(results);
		else
			response.getWriter().println(callbackFunName + "( " + results + " )");
		response.setStatus(status);
		response.flushBuffer();
	}

	/**
	 * 服务器响应结果
	 */
	private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
		String callbackFunName = request.getParameter("callback");
		if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
			response.getWriter().println(results);
		else
			response.getWriter().println(callbackFunName + "( " + results + " )");
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}
}
