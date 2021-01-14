package com.polarbookshop.dispatcherservice;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTests {

	@Autowired
	private FunctionCatalog catalog;

	@Test
	void packOrder() {
		Function<OrderCreatedMessage, Long> pack = catalog.lookup(Function.class, "pack");
		long orderId = 121;
		assertThat(pack.apply(new OrderCreatedMessage(orderId))).isEqualTo(orderId);
	}

	@Test
	void labelOrder() {
		Function<Flux<Long>, Flux<OrderDispatchedMessage>> label = catalog.lookup(Function.class, "label");
		Flux<Long> orderId = Flux.just(121L);

		StepVerifier.create(label.apply(orderId))
				.expectNextMatches(dispatchedOrder ->
						dispatchedOrder.equals(new OrderDispatchedMessage(121L)))
				.verifyComplete();
	}

	@Test
	void packAndLabelOrder() {
		Function<OrderCreatedMessage, Flux<OrderDispatchedMessage>> packAndLabel =
				catalog.lookup(Function.class, "pack|label");
		long orderId = 121;

		StepVerifier.create(packAndLabel.apply(new OrderCreatedMessage(orderId)))
				.expectNextMatches(dispatchedOrder ->
						dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
				.verifyComplete();
	}
}