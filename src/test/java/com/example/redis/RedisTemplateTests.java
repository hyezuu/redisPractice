package com.example.redis;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.io.InvalidClassException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

@SpringBootTest
public class RedisTemplateTests {

	@Autowired
	//redis의 자료형 문자열 의미 X, Java의 자료형 문자열을 의미함
	private StringRedisTemplate stringRedisTemplate;

	@Nested
	class StringOperationsTest {

		@Test
		void 문자열_삽입_조회_테스트() {
			// 지금 redisTemplate에 설정된 타입을 바탕으로
			// redis 문자열 조작을 할거다.
			// ValueOperations는 Generic에 정의되어 있는 타입을 바탕으로 한 객체를 반환받음
			ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

			ops.set("hello", "world");
			assertThat(ops.get("hello")).isEqualTo("world");
		}

		@Test
		void SET_자료형_삽입_테스트() {
			// 집합을 조작하기 위한 클래스
			// 키를 문자열을 사용하고, value로는 set을 사용할 것이며,
			// set의 인자들로 문자열을 사용하겠다.
			SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();

			//SADD hobies games
			setOps.add("hobbies", "games");
			//SADD hobies coding, alcohol, games
			setOps.add("hobbies", "coding", "alcohol", "games");

			assertThat(setOps.size("hobbies")).isEqualTo(3);
		}

		@Test
		void Hash_자료형_삽입_테스트() {
			HashOperations<String, String, String> ops = stringRedisTemplate.opsForHash();
			String key = "user:hyezuu";
			ops.put(key, "email", "hyezuu@gmail.com");
			ops.put(key, "name", "혜주");

			String email = ops.get(key, "email");
			String name = ops.get(key, "name");

			assertThat(email).isEqualTo("hyezuu@gmail.com");
			assertThat(name).isEqualTo("혜주");

			boolean keyExists = stringRedisTemplate.hasKey(key);
			assertThat(keyExists).isTrue();
		}

		@Test
		void 만료_테스트() {
			ValueOperations<String, String> ops
				= stringRedisTemplate.opsForValue();

			ops.set("expire", "test");
			stringRedisTemplate.expire("expire", 0, TimeUnit.SECONDS);
			assertThat(ops.get("expire")).isNull();
		}

		@Test
		void 삭제_테스트() {
			ValueOperations<String, String> ops
				= stringRedisTemplate.opsForValue();

			ops.set("delete", "test");
			stringRedisTemplate.delete("delete");
			assertThat(ops.get("delete")).isNull();
		}
	}

	@Autowired
	private RedisTemplate<String, ItemDto> redisTemplate;

	@Test
	public void valueOperationsTest() {
		ValueOperations<String, ItemDto> ops = redisTemplate.opsForValue();

		ops.set("my:keyboard", ItemDto.builder()
			.name("Mechanical Keyboard")
			.price(300000)
			.description("Expensive 😢")
			.build());
		System.out.println(ops.get("my:keyboard"));

		ops.set("my:mouse", ItemDto.builder()
			.name("mouse mice")
			.price(100000)
			.description("Expensive 😢")
			.build());
		System.out.println(ops.get("my:mouse"));
	}
	//위 테스트 수행 후 serialVersionUID 변경
	@Test
	public void serialVersionUID_값이_바뀌면_InvalidClassException_을_던진다(){
		ValueOperations<String, ItemDto> ops = redisTemplate.opsForValue();

		assertThatThrownBy(()-> ops.get("my:keyboard")
		).hasRootCauseInstanceOf(InvalidClassException.class);
	}

}