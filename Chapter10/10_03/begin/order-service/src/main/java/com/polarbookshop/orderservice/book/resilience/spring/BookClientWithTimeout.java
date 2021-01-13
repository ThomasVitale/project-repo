package com.polarbookshop.orderservice.book.resilience.spring;

import java.time.Duration;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClientProperties;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookClientWithTimeout {

	private final WebClient webClient;

	public BookClientWithTimeout(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.baseUrl(bookClientProperties.getCatalogServiceUrl().toString())
				.build();
	}

	public Mono<Book> getBookByIsbnWithTimeout(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.timeout(Duration.ofSeconds(1));
	}

	public Mono<Book> getBookByIsbnWithTimeoutAndFallback(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.timeout(Duration.ofSeconds(1), Mono.empty());
	}
}
