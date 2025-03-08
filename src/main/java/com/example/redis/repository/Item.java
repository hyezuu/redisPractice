package com.example.redis.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 직렬화 가능한 객체
@RedisHash("item")
public class Item {

	@Id
	private Long id;
	private String name;
	private String description;
	private Integer price;
}
