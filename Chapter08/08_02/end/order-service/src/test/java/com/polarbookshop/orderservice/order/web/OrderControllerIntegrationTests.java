package com.polarbookshop.orderservice.order.web;

import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class OrderControllerIntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void whenGetRequestWithIdThenOrderReturned() {
		// Given
		OrderRequest orderRequest = new OrderRequest("1234567891", 1);
		Order expectedOrder = restTemplate.postForEntity("/orders", orderRequest, Order.class).getBody();
		assertThat(expectedOrder).isNotNull();

		// When
		ResponseEntity<Order> response = restTemplate.getForEntity("/orders/" + expectedOrder.getId(), Order.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedOrder);
	}

	@Test
	void whenPostRequestThenBookCreated() {
		// Given
		OrderRequest orderRequest = new OrderRequest("1234567891", 1);

		// When
		ResponseEntity<Order> response = restTemplate.postForEntity("/orders", orderRequest, Order.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getBookIsbn()).isEqualTo(orderRequest.getIsbn());
		assertThat(response.getBody().getQuantity()).isEqualTo(orderRequest.getQuantity());
		assertThat(response.getBody().getStatus()).isEqualTo(OrderStatus.REJECTED);
	}
}
