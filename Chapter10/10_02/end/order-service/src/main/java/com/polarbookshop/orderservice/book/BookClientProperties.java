package com.polarbookshop.orderservice.book;

import java.net.URI;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
@Data
public class BookClientProperties {

	@NotNull
	private URI catalogServiceUrl;
}
