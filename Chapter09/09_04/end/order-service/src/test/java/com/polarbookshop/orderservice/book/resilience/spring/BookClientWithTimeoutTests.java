package com.polarbookshop.orderservice.book.resilience.spring;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClientProperties;
import com.polarbookshop.orderservice.book.resilience.spring.BookClientWithTimeout;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.web.reactive.function.client.WebClient;

class BookClientWithTimeoutTests {

	private MockWebServer mockWebServer;
	private BookClientWithTimeout bookClient;

	@BeforeEach
	void setup() throws IOException {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.start();

		BookClientProperties bookClientProperties = new BookClientProperties();
		bookClientProperties.setBookServiceUrl(mockWebServer.url("/").uri());
		this.bookClient = new BookClientWithTimeout(bookClientProperties, WebClient.builder());
	}

	@AfterEach
	void clean() throws IOException {
		this.mockWebServer.shutdown();
	}

	@Test
	void whenTimeoutNotExpiredAThenReturn() {
		String bookIsbn = "1234567890";

		MockResponse mockResponse = new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"isbn\":\"" + bookIsbn + "\",\"title\":\"Book Title\", \"author\":\"Book Author\", \"publishingYear\":\"1973\", \"price\":\"9.90\"}")
				.setHeadersDelay(0, TimeUnit.SECONDS);

		mockWebServer.enqueue(mockResponse);

		Mono<Book> book = bookClient.getBookByIsbnWithTimeout(bookIsbn);

		StepVerifier.create(book)
				.expectNextMatches(b -> b.getIsbn().equals(bookIsbn))
				.verifyComplete();
	}

	@Test
	void whenTimeoutExpiresAndNoFallbackThenThrows() {
		String bookIsbn = "1234567890";

		MockResponse mockResponse = new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"isbn\":\"" + bookIsbn + "\",\"title\":\"Book Title\", \"author\":\"Book Author\", \"publishingYear\":\"1973\", \"price\":\"9.90\"}")
				.setBodyDelay(2, TimeUnit.SECONDS);

		mockWebServer.enqueue(mockResponse);

		Mono<Book> book = bookClient.getBookByIsbnWithTimeout(bookIsbn);

		StepVerifier.create(book)
				.expectError(TimeoutException.class)
				.verify();
	}

	@Test
	void whenTimeoutExpiresAndFallbackExistsThenReturn() {
		String bookIsbn = "1234567890";

		MockResponse mockResponse = new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"isbn\":\"" + bookIsbn + "\",\"title\":\"Book Title\", \"author\":\"Book Author\", \"publishingYear\":\"1973\", \"price\":\"9.90\"}")
				.setHeadersDelay(2, TimeUnit.SECONDS);

		mockWebServer.enqueue(mockResponse);

		Mono<Book> book = bookClient.getBookByIsbnWithTimeoutAndFallback(bookIsbn);

		StepVerifier.create(book)
				.expectNextCount(0)
				.verifyComplete();
	}
}