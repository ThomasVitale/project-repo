package com.polarbookshop.orderservice.order.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.polarbookshop.orderservice.order.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "entity_sequence")
	private Long id;

	@NotBlank(message = "The book ISBN must be defined.")
	private String bookIsbn;

	private String bookName;

	@NotNull(message = "The book quantity must be defined.")
	@Min(value = 1, message = "You must order at least 1 item.")
	@Max(value = 5, message = "You cannot order more than 5 items.")
	private Integer quantity;

	@Positive(message = "The price must be a positive number.")
	private Double price;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "The order status must be defined.")
	private OrderStatus status;

	Order(String bookIsbn, int quantity, OrderStatus status) {
		this.bookIsbn = bookIsbn;
		this.quantity = quantity;
		this.status = status;
	}
}
