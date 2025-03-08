package com.example.redis.config;

import com.example.redis.ItemDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	//connectionFactory 의 구현체가 jedis, lettuce..
	public RedisTemplate<String, ItemDto> itemRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, ItemDto> template = new RedisTemplate<>();

		//기본적으로 Lettuce가 사용된다. 단일 인스턴스로 사용되어도 threadSafe 하다
		template.setConnectionFactory(connectionFactory);
		//키에 대한 직렬화는 문자열로 하겠다
//		template.setKeySerializer(RedisSerializer.string());
//		//값에 대한 직렬화는 json으로 하겠다
//		template.setValueSerializer(RedisSerializer.json());

		return template;
	}
}
