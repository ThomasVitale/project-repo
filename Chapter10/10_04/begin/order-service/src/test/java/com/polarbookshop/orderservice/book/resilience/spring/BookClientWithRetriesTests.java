package com.polarbookshop.orderservice.book.resilience.spring;

import java.io.IOException;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClientProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

class BookClientWithRetriesTests {

	private MockWebServer mockWebServer;
	private BookClientWithRetries bookClient;

	@BeforeEach
	void setup() throws IOException {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.start();

		BookClientProperties bookClientProperties = new BookClientProperties();
		bookClientProperties.setCatalogServiceUrl(mockWebServer.url("/").uri());
		this.bookClient = new BookClientWithRetries(bookClientProperties, WebClient.builder());
	}

	@AfterEach
	void clean() throws IOException {
		this.mockWebServer.shutdown();
	}

	@Test
	void whenRetrySuccessfulThenReturn() {
		String bookIsbn = "1234567890";

		MockResponse mockResponseWithError = new MockResponse()
				.setResponseCode(503);

		MockResponse mockResponse = new MockResponse()
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setBody("{\"isbn\":\"" + bookIsbn + "\",\"title\":\"Book Title\", \"author\":\"Book Author\", \"publishingYear\":\"1973\", \"price\":\"9.90\"}");

		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponse);

		Mono<Book> book = bookClient.getBookByIsbnWithRetries(bookIsbn);

		StepVerifier.create(book)
				.expectNextMatches(b -> b.getIsbn().equals(bookIsbn))
				.verifyComplete();
	}

	@Test
	void whenRetryNotSuccessfulThenThrows() {
		String bookIsbn = "1234567890";

		MockResponse mockResponseWithError = new MockResponse()
				.setResponseCode(503);

		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);

		Mono<Book> book = bookClient.getBookByIsbnWithRetries(bookIsbn);

		StepVerifier.create(book)
				.expectErrorMatches(Exceptions::isRetryExhausted)
				.verify();
	}
}