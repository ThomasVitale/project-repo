package com.polarbookshop.orderservice.order.domain;

import java.util.Collection;
import java.util.Optional;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final BookClient bookClient;
	private final OrderRepository orderRepository;

	public Collection<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Optional<Order> getOrder(Long id) {
		return orderRepository.findById(id);
	}

	public void updateOrderStatus(Long orderId, OrderStatus status) {
		getOrder(orderId).ifPresent(
				order -> {
					order.setStatus(status);
					orderRepository.save(order);
				}
		);
	}

	@Transactional
	public Order submitOrder(String isbn, int quantity) {
		Order orderToSubmit = generateOrder(isbn, quantity);
		Order savedOrder = orderRepository.save(orderToSubmit);
		orderEventService.publishOrderAcceptedEvent(savedOrder);
		return savedOrder;
	}

	private Order generateOrder(String isbn, int quantity) {
		Optional<Book> bookToOrder = bookClient.getBookByIsbn(isbn).blockOptional();

		if (bookToOrder.isPresent()) {
			Book book = bookToOrder.get();
			return new Order(null, book.getIsbn(), generateBookName(book), book.getPrice(), quantity, OrderStatus.ACCEPTED);
		} else {
			return new Order(isbn, quantity, OrderStatus.REJECTED);
		}
	}

	private String generateBookName(Book book) {
		return book.getTitle() + " - " + book.getAuthor();
	}
}
