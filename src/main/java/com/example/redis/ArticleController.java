package com.example.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final StringRedisTemplate stringRedisTemplate;

	@GetMapping("/articles/{id}")
	public Long getArticle(@PathVariable String id) {
		return stringRedisTemplate.opsForValue().increment("articles:"+id);
	}

}
