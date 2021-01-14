package com.polarbookshop.orderservice.order.web;

import java.util.Collection;

import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderNotFoundException;
import com.polarbookshop.orderservice.order.domain.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	public Collection<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("{id}")
	public Order getOrderById(@PathVariable Long id) {
		return orderService.getOrder(id)
				.orElseThrow(() -> new OrderNotFoundException(id));
	}

	@PostMapping
	public Order submitOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.submitOrder(orderRequest.getIsbn(), orderRequest.getQuantity());
	}
}
