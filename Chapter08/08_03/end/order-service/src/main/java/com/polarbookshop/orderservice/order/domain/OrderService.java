package com.polarbookshop.orderservice.order.domain;

import java.util.Collection;
import java.util.Optional;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

	private final BookClient bookClient;
	private final OrderRepository orderRepository;

	public OrderService(BookClient bookClient, OrderRepository orderRepository) {
		this.bookClient = bookClient;
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
		Optional<Book> bookToOrder = bookClient.getBookByIsbn(isbn);

		if (bookToOrder.isPresent()) {
			Book book = bookToOrder.get();
			return new Order(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPrice(), OrderStatus.IN_PROGRESS);
		} else {
			return new Order(isbn, OrderStatus.REJECTED);
		}
	}
}
