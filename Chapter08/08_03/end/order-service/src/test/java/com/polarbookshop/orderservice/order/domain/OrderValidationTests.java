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
		Order order = new Order("1234567890", "Book Title", "Book Author", 9.90, OrderStatus.IN_PROGRESS);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).isEmpty();
	}

	@Test
	void whenIsbnNotDefinedThenValidationFails() {
		Order order = new Order("", "Book Title", "Book Author", 9.90, OrderStatus.IN_PROGRESS);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("The book ISBN must be defined.");
	}

	@Test
	void whenStatusIsNotDefinedThenValidationFails() {
		Order order = new Order("1234567890", "Book Title", "Book Author", 9.90, null);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
				.isEqualTo("The order status must be defined.");
	}

	@Test
	void whenOptionalFieldsNotDefinedThenValidationSucceeds() {
		Order order = new Order("1234567890", "", "", null, OrderStatus.IN_PROGRESS);
		Set<ConstraintViolation<Order>> violations = validator.validate(order);
		assertThat(violations).isEmpty();
	}
}