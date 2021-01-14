package com.polarbookshop.dispatcherservice;

import java.util.function.Function;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class DispatchingFunctions {

	@Bean
	public Function<OrderCreatedMessage, Long> pack() {
		return orderCreatedMessage -> {
			log.info("The order with id " + orderCreatedMessage.getOrderId() + " is packed.");
			return orderCreatedMessage.getOrderId();
		};
	}

	@Bean
	public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
		return orderFlux -> orderFlux.map(orderId -> {
			log.info("The order with id " + orderId + " is labelled.");
			return new OrderDispatchedMessage(orderId);
		});
	}
}
