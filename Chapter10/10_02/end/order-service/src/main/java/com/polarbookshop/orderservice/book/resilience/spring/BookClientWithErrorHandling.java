package com.polarbookshop.orderservice.book.resilience.spring;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClientProperties;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class BookClientWithErrorHandling {

	private final WebClient webClient;

	public BookClientWithErrorHandling(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.baseUrl(bookClientProperties.getCatalogServiceUrl().toString())
				.build();
	}

	public Mono<Book> getBookByIsbnWithNotFoundHandling(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty());
	}
}
