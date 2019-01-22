package com.yyg.eprescription.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import com.google.gxp.org.apache.xerces.impl.dv.util.Base64;
import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.entity.Prescription;



public class WSAPIProxy {
	
	private static final String API_URL = "http://192.168.3.118:8088/ExternalServices/ZL_InformationService.asmx/Custom?ReData=";
	private static final String SECRET_KEY = "26BE506066B907C4";
	private static final String TOKEN = "2358188399139E7BD8594078EBDAD9AD";
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
	 * @throws Exception 
     * @throws UnsupportedEncodingException 
     */
	private static String sendGet(String url, String param) {
		String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url +  URLEncoder.encode(param,"GBK");
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            throw new HandleException(4, "网络异常");
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //http://132.147.160.58:70/MDService/DocService.asmx
        return result;
    }
	
	
	 /// 加密
    /// </summary>
    /// <param name="sSrc">加密前明文</param>
    /// <param name="sKey">16位密钥<</param>
    /// <returns></returns>
	private static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64.encode(encrypted);
}
	
  /// 解密
    /// </summary>
    /// <param name="sSrc">密文</param>
    /// <param name="sKey">16位密钥</param>
    /// <returns></returns>
    private static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
  }

    
    //ServiceName  服务名(Ewell.bks.dept)
    //sqlwhere  条件语句（<KSID>76<KSID/>）
    
   private static String ReturnXml(String ServiceName,String sqlwhere) throws Exception{
    	String secretkey=SECRET_KEY;
     	StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?><ROOT>"
				+ "<TOKEN><![CDATA["+Encrypt(TOKEN, secretkey)+"]]>"
				+ "</TOKEN><SERVICE><![CDATA["+ServiceName+"]]></SERVICE><INSIDEKEY><![CDATA[]]>"
				+ "</INSIDEKEY><DATAPARAM><![CDATA["+ Encrypt(sqlwhere, secretkey)+"]]></DATAPARAM></ROOT>");
		String result = sendGet(API_URL,sb.toString());
		//测试
		//String result = "<ROOT> <STATE><![CDATA[T]]></STATE> <DATAPARAM><![CDATA[4gx2xeyFVVAD+TJe22Yu2MN77MTPoaBbg15Eus56JJ9eInLZV/uCWjfyzAXBoAksCWi0egGlx/cYms7l5vPT/fJY37wme7CDZRmWONIirGWUgKvcyaAopEw9y92uuWVEy/D1sk/xyLBQXfFecMCaM3LjHNTpHbtO2lZDKQ/cX2HXbQlQJ8AnEypvTYnvs1VN5uG587zOGbL19GQqxHHNEcf2n9BNEL8rly2Xvcb4PSyZ7nYbU0yX9CeGpa8CGTwT39XzpZ8CvtoU06acPYxESwieOC+Wia0DfKW2yF+D8XsLDMogbwQzK2qXNxLhdkqgNWNgooNojekeOPRAUGNGM3FSve9i0Aykxv9TRI70t4odrTjcVjTFpWflF+9vbe1NOOedjm7ryPVeTgTc+DkOnw==]]></DATAPARAM> </ROOT>";
		if(result.contains("ERROR")){
			System.out.println("请求 ===>"+sqlwhere+",响应错误===>"+result);
     		throw new HandleException(4, "HIS接口响应错误");
     	}else{
     		result=result.substring(result.indexOf("<DATAPARAM>")+11,result.indexOf("</DATAPARAM>")).replace("<![CDATA[","").replace("]]>","");
     	}
     	String xml= Decrypt(result,secretkey);
     	if(xml.startsWith("<OUTPUT/>")){
     		System.out.println("请求===>"+sqlwhere+"===>门诊号不存在或有误");
     		throw new HandleException(4, "门诊/住院号不存在或有误");
     	}
     	return xml;
   }
   
   public static Prescription getHospitalInfo(String type, String number) {
	   try{
		   String sqlwhere = "<type>"+type+"</type><num>"+number+"</num>"; //1806190791
		   String xml = ReturnXml("bianm.get.byMZH", sqlwhere);
		   Prescription ret = xml2Prescription(number, xml);
		   return ret;
	   }catch (Exception e) {
		   throw new HandleException(4, e.getMessage());
	   }
   }
   
   
   private static Prescription xml2Prescription(String number, String xml) throws Exception{
	   Prescription ret = new Prescription();
	   
	   SAXBuilder builder = new SAXBuilder(); 
	    try { 
	        Document document = builder.build(new StringReader(xml)); 
	        Element data = document.getRootElement();
	 
//	        Element numElement = data.getChild("num");
//	        String num = numElement.getValue();
//	        if(num == null || num.trim().isEmpty()){
//	        	System.out.println("num is miss, xml ="+xml);
//	        }
	        //ret.setNum(number);
	        Element doctornameElement = data.getChild("doctorname");
	        ret.setDoctorname(doctornameElement.getValue());
	        Element departElement = data.getChild("department");
	        ret.setDepartment(departElement.getValue());
	        Element patientnameElement = data.getChild("patientname");
	        ret.setPatientname(patientnameElement.getValue());
	        Element patientsexElement = data.getChild("patientsex");
	        ret.setPatientsex(patientsexElement.getValue());
	        Element patientageElement = data.getChild("patientage");
	        String ageStr = patientageElement.getValue();
	        String age = "0";
	        if(ageStr.contains("岁")){
	        	age = ageStr.substring(0, ageStr.indexOf("岁"));
	        } else if(ageStr.contains("月")){
	        	age = "0";
	        } else if(ageStr.contains("天")){
	        	age = "0";
	        } else {
	        	System.out.println("his return ageStr like ====>"+ageStr);
	        	age = "0";
	        }
	        ret.setPatientage(Integer.valueOf(age));
	        Element patientphoneElement = data.getChild("patientphone");
	        ret.setPatientphone(patientphoneElement.getValue());
	        Element diagnosisElement = data.getChild("diagnosis");
	        ret.setDiagnosis(diagnosisElement.getValue());
	        
	    } catch (JDOMException e) {
	    	System.out.println("his return error xml =====>"+xml);
	        //e.printStackTrace();
	    	throw new HandleException(4, "医院HIS返回信息异常,请使用无诊断号开方");
	    } catch (IOException e) { 
	    	System.out.println("his return error xml =====>"+xml);
	    	//e.printStackTrace(); 
	    	throw new HandleException(4, "医院HIS返回信息异常,请使用无诊断号开方");
	    } 
	   return ret;
   }
}
