package com.htdp1.camelspring.custom;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.DefaultEndpoint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
@UriEndpoint(firstVersion = "1.4.0", scheme = "sample", title = "sample", syntax = "sample")
public class SampleEndpoint extends DefaultEndpoint {

	@UriParam(label = "service", description = "service")
	private String service;

	@UriParam(label = "method", description = "method")
	private String method;

	public SampleEndpoint(String uri, SampleComponent component) {
		super(uri, component);

		log.debug("uri=>" + uri);
	}

	@Override
	public Producer createProducer() throws Exception {
		return new SampleProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
//		return new SampleConsumerPoll(this, processor);
		throw new UnsupportedOperationException("You cannot use consumer to this endpoint:" + getEndpointUri());
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}