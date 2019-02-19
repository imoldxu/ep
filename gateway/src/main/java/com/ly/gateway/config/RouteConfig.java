package com.ly.gateway.config;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouteConfig {

	/**
	   * 依赖于webflux的断言路由器，屏蔽掉所有内部的接口
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> consulFunRouterFunction() {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.path("/*/internal/**"),
                request -> ServerResponse.ok().body(BodyInserters.fromObject("sorry, the api can not be accessed")));
        return route;
    }
	
//	@Bean
//	public RouteLocator addHanderRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes().route("internal", r->r.path("/*/internal/**").uri("http://www.baidu.com")).build();
//	}
	
	/**
	 * 与服务发现结合，配置路由
	 * @param discoveryClient
	 * @param properties
	 * @return
	 */
	@Bean
    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient, DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
    }
}
