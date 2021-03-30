package com.htdp1.camelspring.custom;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleComponent extends DefaultComponent {

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		log.debug("***** SampleComponent create endpoint ");

		SampleEndpoint endpoint = new SampleEndpoint(uri, this);
		setProperties(endpoint, parameters);
		return endpoint;
	}

}