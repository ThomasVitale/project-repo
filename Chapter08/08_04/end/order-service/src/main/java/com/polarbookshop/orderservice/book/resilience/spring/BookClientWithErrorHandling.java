package com.polarbookshop.orderservice.book.resilience.spring;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClientProperties;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class BookClientWithErrorHandling {

	private final WebClient webClient;

	public BookClientWithErrorHandling(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.defaultHeader("Accept", "application/json; charset=utf-8")
				.baseUrl(bookClientProperties.getBookServiceUrl().toString())
				.build();
	}

	public Mono<Book> getBookByIsbnWithNotFoundHandling(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty());
	}
}
