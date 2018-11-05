//package com.ly.service.config;
//
//import java.io.IOException;
//
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.ly.service.context.ErrorCode;
//import com.ly.service.context.HandleException;
//import com.ly.service.context.InternalErrorMessage;
//import com.ly.service.utils.JSONUtils;
//import com.netflix.hystrix.exception.HystrixBadRequestException;
//
//import feign.FeignException;
//import feign.Response;
//import feign.Util;
//import feign.codec.ErrorDecoder;
//
//@Configuration
//public class FeignConfiguration {
//
//	@Bean
//	public ErrorDecoder errorDecoder(){
//		return new CustomDecoder();
//	}
//	
//	public class CustomDecoder implements ErrorDecoder{
//
//		public Exception decode(String methodKey, Response response){
//			int status = response.status();
//			if(status == 500){
//				InternalErrorMessage errorMsg = null;
//				try{
//					String body = Util.toString(response.body().asReader());
//					JsonNode bodynode = JSONUtils.getJsonObject(body);
//					String msg = bodynode.get("message").asText();
//					errorMsg = JSONUtils.getObjectByJson(msg, InternalErrorMessage.class);
//				}catch (Exception e) {
//					return new ErrorDecoder.Default().decode(methodKey, response);
//				}
//				//throw new HystrixBadRequestException("wrap HandleException", new HandleException(errorMsg.getCode(), errorMsg.getMsg()));
//				return new HandleException(errorMsg.getCode(), errorMsg.getMsg());
//			}	
//			return new ErrorDecoder.Default().decode(methodKey, response);
//		}
//	}
//}
