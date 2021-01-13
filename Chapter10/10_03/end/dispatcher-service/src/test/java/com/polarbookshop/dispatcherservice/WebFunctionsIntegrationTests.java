package com.polarbookshop.dispatcherservice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class WebFunctionsIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenOrderCreatedThenDispatch() {
		long orderId = 121L;
		OrderCreatedMessage orderCreatedMessage = new OrderCreatedMessage(121L);
		OrderDispatchedMessage expectedOrderDispatchedMessage = new OrderDispatchedMessage(orderId);

		webTestClient
				.post()
				.uri("/")
				.body(Mono.just(orderCreatedMessage), OrderCreatedMessage.class)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(OrderDispatchedMessage.class)
				.hasSize(1)
				.contains(expectedOrderDispatchedMessage);
	}
}
