package com.polarbookshop.orderservice.order.domain;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("The order with id " + id + " was not found.");
    }
}
