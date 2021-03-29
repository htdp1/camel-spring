package com.htdp1.camelspring.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileInOutRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file://filein?noop=true&delete=true&recursive=true") // from
				.log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName} at ${header.CamelFileLastModified}")
				.to("file://fileout") // to
		;

		from("file://fileout?noop=true&delete=true&recursive=true") // from
				.log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName} at ${header.CamelFileLastModified}")
//				.to("file://fileout") // to
		;
	}

}