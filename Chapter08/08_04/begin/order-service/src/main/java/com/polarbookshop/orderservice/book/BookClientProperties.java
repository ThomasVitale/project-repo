package com.polarbookshop.orderservice.book;

import java.net.URI;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
@Data
public class BookClientProperties {
	private URI bookServiceUrl;
}
