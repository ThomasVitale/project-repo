package com.polarbookshop.orderservice.order.domain;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public Collection<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Optional<Order> getOrder(Long id) {
		return orderRepository.findById(id);
	}

	public Order submitOrder(String isbn, int quantity) {
		Order orderToSubmit = generateOrder(isbn, quantity);
		return orderRepository.save(orderToSubmit);
	}

	private Order generateOrder(String isbn, int quantity) {
		return new Order(isbn, quantity, OrderStatus.REJECTED);
	}
}
