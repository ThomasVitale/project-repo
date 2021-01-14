package com.polarbookshop.orderservice.order.event;

import java.util.function.Consumer;

import com.polarbookshop.orderservice.order.domain.OrderService;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OrderFunctions {

	@Bean
	public Consumer<OrderDispatchedMessage> dispatchOrder(OrderService orderService) {
		return orderDispatchedMessage -> {
			log.info("The order with id " + orderDispatchedMessage.getOrderId() + " has been updated.");
			orderService.updateOrderStatus(orderDispatchedMessage.getOrderId(), OrderStatus.DISPATCHED);
		};
	}
}
