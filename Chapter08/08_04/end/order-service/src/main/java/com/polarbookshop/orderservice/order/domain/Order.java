package com.polarbookshop.orderservice.order.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.polarbookshop.orderservice.order.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

	@NotBlank(message = "The book ISBN must be defined.")
	private String bookIsbn;

	private String bookTitle;

	private String bookAuthor;

	private Double bookPrice;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "The order status must be defined.")
	private OrderStatus status;

	Order(String bookIsbn, OrderStatus status) {
		this.bookIsbn = bookIsbn;
		this.status = status;
	}
}
