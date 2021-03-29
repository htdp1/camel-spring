package com.htdp1.camelspring.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class RedisSubscriberRoute extends RouteBuilder {
	public @Value("${spring.redis.host}") String host;
	public @Value("${spring.redis.port}") int port;

	@Override
	public void configure() throws Exception {
		String redisParameter = host + ":" + port // default
				+ "?channels=departments" // channels
				+ "&command=SUBSCRIBE" // command
				+ "&serializer=#redisSerializer" // serializer
		;

		from("spring-redis://" + redisParameter) // from
				.log(String.format("Consuming with redis in channel: %s, massage body: ${body}", "departments")) // log
				.to("file://filein") // to
				.bean("redisSubscriber", "onMessageCamel") // bean
		;
	}

}