package com.polarbookshop.orderservice.order.web;

import java.util.Optional;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureWebTestClient
class OrderControllerIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookClient bookClient;

	@Test
	void whenGetRequestWithIdThenOrderReturned() {
		// Given
		String bookIsbn = "1234567890";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Optional.of(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 1);
		Order expectedOrder = webTestClient.post().uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(expectedOrder).isNotNull();

		// When
		Order fetchedOrder = webTestClient.get().uri("/orders/" + expectedOrder.getId())
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();

		// Then
		assertThat(fetchedOrder).isNotNull();
		assertThat(fetchedOrder).usingRecursiveComparison().isEqualTo(expectedOrder);
	}

	@Test
	void whenPostRequestAndBookExistsThenOrderAccepted() {
		// Given
		String bookIsbn = "1234567891";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Optional.of(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		// When
		Order createdOrder = webTestClient.post().uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();

		// Then
		assertThat(createdOrder).isNotNull();
		assertThat(createdOrder.getBookIsbn()).isEqualTo(orderRequest.getIsbn());
		assertThat(createdOrder.getQuantity()).isEqualTo(orderRequest.getQuantity());
		assertThat(createdOrder.getBookName()).isEqualTo(book.getTitle() + " - " + book.getAuthor());
		assertThat(createdOrder.getBookPrice()).isEqualTo(book.getPrice());
		assertThat(createdOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
	}

	@Test
	void whenPostRequestAndBookNotExistsThenOrderRejected() {
		// Given
		String bookIsbn = "1234567892";
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Optional.empty());
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		// When
		Order createdOrder = webTestClient.post().uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();

		// Then
		assertThat(createdOrder).isNotNull();
		assertThat(createdOrder.getBookIsbn()).isEqualTo(orderRequest.getIsbn());
		assertThat(createdOrder.getQuantity()).isEqualTo(orderRequest.getQuantity());
		assertThat(createdOrder.getStatus()).isEqualTo(OrderStatus.REJECTED);
	}
}
