package com.polarbookshop.orderservice.order.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderRequest {
	private String isbn;
	private Integer quantity;
}
