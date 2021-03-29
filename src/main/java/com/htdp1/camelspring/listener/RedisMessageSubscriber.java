package com.htdp1.camelspring.listener;

import org.apache.camel.Exchange;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisMessageSubscriber implements MessageListener {

	public void onMessage(Message message, byte[] bytes) {
		log.debug("message.toString() => " + message.toString());
		log.debug("message.getBody().toString() => " + message.getBody().toString());

		byte[] body = message.getBody();
		String str = (String) (new StringRedisSerializer()).deserialize(body);
		log.debug("key : {}, message : {}", new String(bytes), str);
	}

	public void onMessageCamel(Exchange exchange) {
		log.debug("exchange.toString() => " + exchange.toString());
		log.debug("exchange.getMessage() => " + exchange.getMessage());

		org.apache.camel.Message message = exchange.getMessage();

		log.debug("message.toString() => " + message.toString());
		log.debug("message.getMessageId() => " + message.getMessageId());
		log.debug("message.getHeaders() => " + message.getHeaders());
		log.debug("message.getBody() => " + message.getBody());

	}
}