package com.polarbookshop.orderservice.book;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class BookClient {

	private final WebClient webClient;

	public BookClient(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.defaultHeader("Accept", "application/json; charset=utf-8")
				.baseUrl(bookClientProperties.getBookServiceUrl().toString())
				.build();
	}

	public Optional<Book> getBookByIsbn(String isbn) {
		try {
			Book book = webClient.get().uri(isbn)
					.retrieve()
					.bodyToMono(Book.class)
					.block();
			return Optional.ofNullable(book);
		} catch (WebClientResponseException ex) {
			return Optional.empty();
		}
	}
}
