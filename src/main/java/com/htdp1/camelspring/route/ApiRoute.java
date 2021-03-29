package com.htdp1.camelspring.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import com.htdp1.camelspring.dto.Department;

//@Component
public class ApiRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		intercept().to("log:hello");

		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

		from("rest:get:/departments/{deptNo}") //
				.setHeader("CamelHttpMethod", constant("GET")) //
				.setHeader("CamelHttpUri", simple("http://localhost:8090/dept-spring/api")) //
				.to("http://localhost:8090/dept-spring/api") //
				.convertBodyTo(String.class) //
				.unmarshal().json(JsonLibrary.Jackson, Department.class) //
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						if (exchange.getIn().getBody() instanceof Department) {
							Department department = (Department) exchange.getIn().getBody();
							String deptNo = (String) exchange.getIn().getHeader("deptNo");
							department.setDeptNo(deptNo);
							log.debug("" + department);
						}
					}
				}) //
				.marshal().json(JsonLibrary.Jackson, Department.class) //
				.end() //
		;

		from("rest:post:/departments") //
				.setHeader("CamelHttpMethod", constant("POST")) //
				.setHeader("CamelHttpUri", simple("http://localhost:8090/dept-spring/api/departments")) //
				.to("http://localhost:8090/dept-spring/api/departments") //
				.convertBodyTo(String.class) //
				.unmarshal().json(JsonLibrary.Jackson, Department.class) //
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
					}
				})//
				.end() //
		;
	}
}