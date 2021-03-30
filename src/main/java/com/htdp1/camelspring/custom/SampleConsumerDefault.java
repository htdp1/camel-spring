package com.htdp1.camelspring.custom;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleConsumerDefault extends DefaultConsumer {

	private final SampleEndpoint endpoint;

	public SampleConsumerDefault(SampleEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
	}

	@Override
	protected void doStart() throws Exception {
		log.debug("***** SampleConsumerDefault do start");

		Exchange exchange = endpoint.createExchange();
		
		try {
			exchange.getIn().setBody(new String("body"));
			
			getProcessor().process(exchange);
		} finally {
			if (exchange.getException() != null) {
				getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
			}
		}
	}
}