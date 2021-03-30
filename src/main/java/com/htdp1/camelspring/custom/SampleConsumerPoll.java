package com.htdp1.camelspring.custom;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.ScheduledPollConsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleConsumerPoll extends ScheduledPollConsumer {
	private final SampleEndpoint endpoint;

	public SampleConsumerPoll(SampleEndpoint endpoint, Processor processor) {
		super(endpoint, processor);

		this.endpoint = endpoint;
		this.setDelay(10000);
	}

	@Override
	protected int poll() throws Exception {
		log.debug("***** SampleConsumerPoll sample consummer polling");
		log.debug("endpoint=>" + endpoint);

		Exchange exchange = endpoint.createExchange();

		try {
			exchange.getIn().setBody(new String("body"));

			getProcessor().process(exchange);

			return 1; // number of messages polled
		} finally {
			// log exception if an exception occurred and was not handled
			if (exchange.getException() != null) {
				getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
			}
		}

	}
}