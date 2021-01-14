package com.polarbookshop.dispatcherservice;

import java.time.Duration;
import java.util.function.Function;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class DispatchingFunctions {

	@Bean
	public Function<OrderAcceptedMessage, Long> pack() {
		return orderAcceptedMessage -> {
			log.info("The order with id " + orderAcceptedMessage.getOrderId() + " is packed.");
			return orderAcceptedMessage.getOrderId();
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
