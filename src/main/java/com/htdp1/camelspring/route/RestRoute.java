package com.htdp1.camelspring.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;

import com.htdp1.camelspring.dto.Department;

//@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

		rest("/departments") //
				.get("/{deptNo}") //
				.consumes(MediaType.APPLICATION_JSON_VALUE) //
				.type(Department.class) //
				.outType(Department.class) //
				.to("rest:get:?host=localhost:8090/dept-spring/api&bridgeEndpoint=true") //
		;

		rest("/departments") //
				.post("/") //
				.consumes(MediaType.APPLICATION_JSON_VALUE) //
				.type(Department.class) //
				.outType(Department.class) //
				.to("seda:newMessage") //
		;

		from("seda:newMessage?concurrentConsumers=20") //
				.log("seda:newMessage ${body}") //
//				.routeId("smpp-sender") //
//				.process(messageProcessor) //
//				.setHeader("CamelSmppDestAddr", simple("94${in.body.sender}")) //
//				.setBody(simple("${in.body.messageBody}")) //
//				.to("smpp://{{smpp.tr.systemid}}@{{smpp.tr.host}}:{{smpp.tr.port}}?password={{smpp.tr.password}}&enquireLinkTimer=3000&transactionTimer=5000&sourceAddrTon={{smpp.source.addr.ton}}&sourceAddrNpi={{smpp.source.addr.npi}}&destAddrTon={{smpp.dest.addr.ton}}&destAddrNpi={{smpp.dest.addr.npi}}&sourceAddr={{smpp.source.address}}") //
		;

		rest("/say")//
				.get("/hello").to("direct:hello")//
				.get("/bye").consumes("application/json").to("direct:bye")//
				.post("/bye").to("mock:update")//
		;

		from("direct:hello").transform().constant("Hello World");
		from("direct:bye").transform().constant("Bye World");

	}
}