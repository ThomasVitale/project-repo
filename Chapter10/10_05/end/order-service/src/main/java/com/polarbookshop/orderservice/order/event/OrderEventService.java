package com.polarbookshop.orderservice.order.event;

import com.polarbookshop.orderservice.order.domain.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventService {

	private final StreamBridge streamBridge;

	public OrderEventService(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	public void publishOrderAcceptedEvent(Order order) {
		OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage(order.getId());
		log.info("Sending order accepted event with id: " + order.getId());
		this.streamBridge.send("order-accepted", orderAcceptedMessage);
	}
}
