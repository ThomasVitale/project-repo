package com.polarbookshop.orderservice.order.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderValidationTests {

	private static Validator validator;

	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void whenAllFieldsCorrectThenValidationSucceeds() {
		Order order = new Order(1L, "1234567890", "Book Name", 9.90, 1, OrderStatus.ACCEPTED);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).isEmpty();
	}

	@Test
	void whenIsbnNotDefinedThenValidationFails() {
		Order order = new Order(1L, "", "Book Name", 9.90, 1, OrderStatus.ACCEPTED);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("The book ISBN must be defined.");
	}

	@Test
	void whenQuantityIsNotDefinedThenValidationFails() {
		Order order = new Order(1L, "1234567890", "Book Name", 9.90, null, OrderStatus.ACCEPTED);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("The book quantity must be defined.");
	}

	@Test
	void whenQuantityIsLowerThanMinThenValidationFails() {
		Order order = new Order(1L, "1234567890", "Book Name", 9.90, 0, OrderStatus.ACCEPTED);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("You must order at least 1 item.");
	}

	@Test
	void whenQuantityIsGreaterThanMaxThenValidationFails() {
		Order order = new Order(1L, "1234567890", "Book Name", 9.90, 6, OrderStatus.ACCEPTED);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("You cannot order more than 5 items.");
	}

	@Test
	void whenStatusIsNotDefinedThenValidationFails() {
		Order order = new Order(1L, "1234567890", "Book Name", 9.90, 1, null);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("The order status must be defined.");
	}

	@Test
	void whenOptionalFieldsNotDefinedThenValidationSucceeds() {
		Order order = new Order(null, "1234567890", "", null, 1, OrderStatus.ACCEPTED);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).isEmpty();
	}
}