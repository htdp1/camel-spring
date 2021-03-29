package com.htdp1.camelspring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.htdp1.camelspring.listener.RedisMessageSubscriber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RedisConfig {

	public @Value("${spring.redis.host}") String host;
	public @Value("${spring.redis.port}") int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		log.debug("@Bean:redisConnectionFactory");

		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);

		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);

		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		log.debug("@Bean:redisTemplate");
		
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setKeySerializer(redisSerializer());
		redisTemplate.setValueSerializer(redisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
	
	@Bean
	public RedisSerializer<?> redisSerializer() {
		log.debug("@Bean:redisSerializer");
		
		return new StringRedisSerializer();
	}
	

	@Bean
	public RedisMessageSubscriber redisSubscriber() {
		log.debug("@Bean:redisSubscriber");
		
		return new RedisMessageSubscriber();
	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		log.debug("@Bean:messageListenerAdapter");
		
		return new MessageListenerAdapter(redisSubscriber());
	}

	@Bean
	public RedisMessageListenerContainer redisContainer() {
		log.debug("@Bean:redisContainer");
		
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());
		container.addMessageListener(messageListenerAdapter(), topic());
		return container;
	}

	@Bean
	public ChannelTopic topic() {
		log.debug("@Bean:topic");
		
		return new ChannelTopic("departments");
	}
}