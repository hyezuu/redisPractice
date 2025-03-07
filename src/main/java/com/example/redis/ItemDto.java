package com.example.redis;

import java.io.Serializable;
import lombok.Builder;

@Builder
public record ItemDto(String name, String description, Integer price) {

}
