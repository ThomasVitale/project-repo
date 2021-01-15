package com.polarbookshop.orderservice.order.domain;

import java.util.Collection;
import java.util.Optional;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import com.polarbookshop.orderservice.order.event.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {

	private final BookClient bookClient;
	private final OrderRepository orderRepository;
	private final OrderEventService orderEventService;

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
		if (savedOrder.getStatus().equals(OrderStatus.ACCEPTED)) {
			log.info("The order with id {} is accepted.", savedOrder.getId());
			orderEventService.publishOrderAcceptedEvent(savedOrder);
		}
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
