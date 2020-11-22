package com.polarbookshop.orderservice.order.web;

import java.util.Optional;

import com.polarbookshop.orderservice.order.domain.OrderService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerMvcTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Test
	void whenReadingNotExistingBookThenShouldReturn404() throws Exception {
		long orderId = 99;
		given(orderService.getOrder(orderId)).willReturn(Optional.empty());
		mockMvc
				.perform(get("/orders/" + orderId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().string("The order with id " + orderId + " was not found."));
	}

}