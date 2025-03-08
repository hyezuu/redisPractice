package com.example.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final StringRedisTemplate stringRedisTemplate;

	@GetMapping("/articles/{id}")
	public Long getArticle(@PathVariable String id) {
		return stringRedisTemplate.opsForValue().increment("articles:"+id);
	}

	//오늘의 조회수를 따로 관리하고 싶다면 ?
	@GetMapping("/articles/{id}/today")
	public Long getTodayArticle(@PathVariable String id) {
		Long hits = stringRedisTemplate.opsForValue().increment("articles:"+id+":today");
		//이후 rename articles:1:today articles:20xx-xx-xx 로 키를 바꿔준다
		return hits;
	}



}
