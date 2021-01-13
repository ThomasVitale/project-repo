package com.polarbookshop.edgeservice;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RetryTests {

	private static final MockWebServer mockWebServer = new MockWebServer();

	@Autowired
	private WebTestClient webTestClient;

	@BeforeAll
	static void init() throws IOException {
		mockWebServer.start();
		System.setProperty("CATALOG_SERVICE_URL", "http://localhost:" + mockWebServer.getPort());
		System.setProperty("ORDER_SERVICE_URL", "http://localhost:" + mockWebServer.getPort());
	}

	@AfterAll
	static void destroy() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	void whenRetryNotExceededThenReturns() {
		MockResponse mockResponseWithError = new MockResponse().setResponseCode(503);
		MockResponse mockResponseWithSuccess = new MockResponse().setBody("Books");

		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithSuccess);

		webTestClient.get().uri("/books")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(String.class).isEqualTo("Books");
	}

	@Test
	void whenRetryExceededThenThrows() {
		MockResponse mockResponseWithError = new MockResponse().setResponseCode(503);

		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);
		mockWebServer.enqueue(mockResponseWithError);

		webTestClient.get().uri("/books")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
