package com.polarbookshop.edgeservice;

import java.io.IOException;
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
class TimeoutTests {

	private static final MockWebServer mockWebServer = new MockWebServer();

	@Autowired
	private WebTestClient webTestClient;

	@BeforeAll
	static void init() throws IOException {
		mockWebServer.start(9999);
		System.setProperty("CATALOG_SERVICE_URL", "http://localhost:" + mockWebServer.getPort());
		System.setProperty("ORDER_SERVICE_URL", "http://localhost:" + mockWebServer.getPort());
	}

	@AfterAll
	static void destroy() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	void whenTimeoutNotExceededThenReturns() {
		MockResponse mockResponseWithDelay = new MockResponse().setResponseCode(201)
				.setHeadersDelay(500, TimeUnit.MILLISECONDS);

		mockWebServer.enqueue(mockResponseWithDelay);

		webTestClient.post().uri("/books")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.CREATED);
	}

	@Test
	void whenTimeoutExceededThenThrows() {
		MockResponse mockResponseWithDelay = new MockResponse().setResponseCode(201)
				.setHeadersDelay(2, TimeUnit.SECONDS);

		mockWebServer.enqueue(mockResponseWithDelay);

		webTestClient.post().uri("/orders")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.GATEWAY_TIMEOUT);
	}
}
