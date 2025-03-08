package com.example.redis;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
//	private static final long serialVersionUID = 2L;

	private String name;
	private String description;
	private Integer price;

	@Builder
	public ItemDto(String name, String description, Integer price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
}
