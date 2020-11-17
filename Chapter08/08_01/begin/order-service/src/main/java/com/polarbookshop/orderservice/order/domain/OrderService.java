package com.polarbookshop.orderservice.order.domain;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Collection<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Optional<Order> getOrder(Long id) {
		return orderRepository.findById(id);
	}

	public Order submitOrder(String isbn) {
		Order orderToSubmit = generateOrder(isbn);
		return orderRepository.save(orderToSubmit);
	}

	private Order generateOrder(String isbn) {
		return new Order(isbn, OrderStatus.REJECTED);
	}
}
