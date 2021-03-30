package com.htdp1.camelspring.route;

import java.util.HashMap;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.htdp1.camelspring.dto.Department;

@Component
public class SampleRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("rest:get:/sample") //
//				.log("body1=>${body}") //
				.convertBodyTo(String.class) //
//				.unmarshal().json(JsonLibrary.Jackson) // java.util.LinkedHashMap
//				.unmarshal().json(JsonLibrary.Jackson, HashMap.class) //
//				.unmarshal().json(JsonLibrary.Jackson, Department.class) //
				.to("sample:foo?service=service1&method=method1") //
				.log("body1=>${body}") //
				.end() //
		;

//		from("sample:foo?service=service1&method=method1") //
//				.to("sample:foo?service=service1&method=method1") //
//				.end() //
//		;
	}
}