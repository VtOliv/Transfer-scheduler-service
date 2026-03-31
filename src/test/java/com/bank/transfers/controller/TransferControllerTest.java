package com.bank.transfers.controller;

import static com.bank.transfers.factory.TransferFactory.dtoMockBuilder;
import static com.bank.transfers.factory.TransferFactory.transferMockBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.transfers.domain.TransferDTO;
import com.bank.transfers.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransferService transferService;

	@Test
	void shouldReturnCreatedWhenSchedulingTransfer() throws Exception {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("100.00"), LocalDate.now().plusDays(5));

		var response = transferMockBuilder(1L, "1234567890", "0987654321", new BigDecimal("100.00"),
				new BigDecimal("12.00"), LocalDate.now(), dto.getTransferDate());

		when(transferService.scheduleTransfer(any(TransferDTO.class))).thenReturn(response);

		mockMvc.perform(post("/transfers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.sourceAccount").value("1234567890"))
				.andExpect(jsonPath("$.destinationAccount").value("0987654321"))
				.andExpect(jsonPath("$.fee").value(12.00));
	}

	@Test
	void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
		var dto = dtoMockBuilder("123", "0987654321", new BigDecimal("100.00"), LocalDate.now().minusDays(1));

		mockMvc.perform(post("/transfers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isBadRequest());
	}

	@Test
	void shouldReturnListOfTransfers() throws Exception {
		var transfer = transferMockBuilder(1L, "1234567890", "0987654321", new BigDecimal("100.00"),
				new BigDecimal("12.00"), LocalDate.now(), LocalDate.now().plusDays(5));

		when(transferService.listTransfers()).thenReturn(Collections.singletonList(transfer));

		mockMvc.perform(get("/transfers")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].sourceAccount").value("1234567890"))
				.andExpect(jsonPath("$[0].destinationAccount").value("0987654321"));
	}
}