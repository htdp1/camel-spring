package com.htdp1.camelspring.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class RedisPublishRoute extends RouteBuilder {
	public @Value("${spring.redis.host}") String host;
	public @Value("${spring.redis.port}") int port;

	@Override
	public void configure() throws Exception {
		String redisParameter = host + ":" + port // default
				+ "?command=PUBLISH" // command
				+ "&serializer=#redisSerializer" // serializer
				+ "&redisTemplate=#redisTemplate" // redisTemplate
		;

		from("timer:rest-client?period=10s") //
				.setHeader("CamelRedis.Channel", simple("departments")) //
				.setHeader("CamelRedis.Message",
						simple("{\n" + "  \"deptNo\":\"t3\",\n" + "  \"deptName\":\"test3 text add\"\n" + "}")) //
				.to("spring-redis://" + redisParameter) //
		;

	}
}