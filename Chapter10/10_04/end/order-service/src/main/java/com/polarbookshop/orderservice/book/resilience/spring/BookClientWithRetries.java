package com.polarbookshop.orderservice.book.resilience.spring;

import java.time.Duration;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClientProperties;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookClientWithRetries {

	private final WebClient webClient;

	public BookClientWithRetries(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.baseUrl(bookClientProperties.getCatalogServiceUrl().toString())
				.build();
	}

	public Mono<Book> getBookByIsbnWithRetries(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.retryWhen(Retry.backoff(3, Duration.ofMillis(100)));
	}
}
