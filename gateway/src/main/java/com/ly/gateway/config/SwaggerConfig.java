//package com.ly.gateway.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.web.reactive.config.ResourceHandlerRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
//
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebFluxConfigurationSupport{
//
//	@Autowired
//	private GatewayProperties properties;
//
//	@Primary
//    @Bean
//    public SwaggerResourcesProvider swaggerResourcesProvider() {
//		return new SwaggerResourcesProvider(){
//
//			@Override
//			public List<SwaggerResource> get() {
//				List<SwaggerResource> resources = new ArrayList<>();
//				List<RouteDefinition> routes = properties.getRoutes();
//	            for (RouteDefinition route : routes) {
//	            	String serviceid =route.getId().trim();
//	            	resources.add(createResource(serviceid, serviceid, "2.0"));	
//				}
//				return resources;
//			}
//			
//		};
//    }
//
//    private SwaggerResource createResource(String name, String location, String version) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation("/" + location + "/v2/api-docs");
//        swaggerResource.setSwaggerVersion(version);
//        return swaggerResource;
//    }
//	
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//    
//}
