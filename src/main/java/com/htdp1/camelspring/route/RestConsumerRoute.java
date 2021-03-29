package com.htdp1.camelspring.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().host("localhost").port(8090);

		from("timer:rest-client?period=10s") //
				.to("rest:get:/dept-spring/api/departmentRedises/t1") //
				.log("${body}") //
		;
	}
}