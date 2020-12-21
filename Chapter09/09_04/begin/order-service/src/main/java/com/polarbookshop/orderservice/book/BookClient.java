package com.polarbookshop.orderservice.book;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class BookClient {

	private final WebClient webClient;

	public BookClient(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.baseUrl(bookClientProperties.getBookServiceUrl().toString() + "/books/")
				.build();
	}

	public Mono<Book> getBookByIsbn(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty());
	}
}
