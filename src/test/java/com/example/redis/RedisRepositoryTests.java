package com.example.redis;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.redis.repository.Item;
import com.example.redis.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisRepositoryTests {

	@Autowired
	private ItemRepository itemRepository;

	@Test// id 없이 생성하니까 이상하게 만들어짐
	public void createTest() {
		Item item = Item.builder()
			.id(1L)
			.name("keyboard")
			.description("expensive")
			.price(1999999)
			.build();

		itemRepository.save(item);
		assertThat(itemRepository.findById(1L).isPresent()).isTrue();
	}

	@Test
	public void readOneTest() {
		Item item = itemRepository.findById(1L)
			.orElseThrow();

		System.out.println(item.getDescription());
		assertThat(item.getDescription()).isEqualTo("expensive");
	}

	@Test
	public void updateTest() {
		Item item = itemRepository.findById(1L).orElseThrow();

		item.setDescription("new description");
		item = itemRepository.save(item);

		System.out.println(item.getDescription());
		assertThat(item.getDescription()).isEqualTo("new description");
	}

	@Test
	public void deleteTest() {
		itemRepository.deleteById(1L);

		assertThat(itemRepository.findById(1L).isEmpty()).isTrue();
	}


}