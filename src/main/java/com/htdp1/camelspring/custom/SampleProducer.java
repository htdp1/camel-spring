package com.htdp1.camelspring.custom;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.apache.camel.util.json.JsonObject;
import org.apache.camel.util.json.Jsoner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleProducer extends DefaultProducer {

	private SampleEndpoint endpoint;

	public SampleProducer(SampleEndpoint endpoint) throws IOException {
		super(endpoint);
		this.endpoint = endpoint;
	}

	public void process(Exchange exchange) throws Exception {
		log.debug("endpoint=>" + endpoint);
		log.debug("getBodyClass=>" + exchange.getIn().getBody().getClass());
		log.debug("getBody=>" + exchange.getIn().getBody());

		JsonObject jsonObj = new JsonObject();

		if (exchange.getIn().getBody() instanceof String) {
			String body = (String) exchange.getIn().getBody();
			
			jsonObj = Jsoner.deserialize(body, jsonObj);
			
			jsonObj.put("deptName", "new DeptName");
			
			log.debug("jsonObj=>" + jsonObj);
			
		}

		String toBody = Jsoner.serialize(jsonObj);

		exchange.getIn().setBody(toBody);
	}
}