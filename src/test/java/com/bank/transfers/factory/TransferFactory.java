package com.bank.transfers.factory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.bank.transfers.domain.Transfer;
import com.bank.transfers.domain.TransferDTO;

public class TransferFactory {

	public static Transfer transferMock() {
		return Transfer.builder().id(1L).sourceAccount("1234567890").destinationAccount("0987654321")
				.amount(new BigDecimal("100.00")).fee(new BigDecimal("5.50000")).schedulingDate(LocalDate.now())
				.transferDate(LocalDate.now()).build();
	}

	public static Transfer transferMockBuilder(Long id, String sourceAccount, String destinationAccount,
			BigDecimal amount, BigDecimal fee, LocalDate schedulingDate, LocalDate transferDate) {
		return Transfer.builder().id(id).sourceAccount(sourceAccount).destinationAccount(destinationAccount)
				.amount(amount).fee(fee).schedulingDate(schedulingDate).transferDate(transferDate).build();
	}

	public static List<Transfer> transferListMock() {
		return Arrays.asList(
				Transfer.builder().id(1L).sourceAccount("1234567890").destinationAccount("0987654321")
						.amount(new BigDecimal("100.00")).fee(new BigDecimal("5.50000")).schedulingDate(LocalDate.now())
						.transferDate(LocalDate.now()).build(),
				Transfer.builder().id(2L).sourceAccount("1111111111").destinationAccount("2222222222")
						.amount(new BigDecimal("200.00")).fee(new BigDecimal("16.40000"))
						.schedulingDate(LocalDate.now()).transferDate(LocalDate.now().plusDays(12)).build());
	}

	public static TransferDTO dtoMock() {
		return TransferDTO.builder().sourceAccount("1234567890").destinationAccount("0987654321")
				.amount(new BigDecimal("100.00")).transferDate(LocalDate.now()).build();
	}

	public static TransferDTO dtoMockBuilder(String sourceAccount, String destinationAccount, BigDecimal amount,
			LocalDate transferDate) {
		return TransferDTO.builder().sourceAccount(sourceAccount).destinationAccount(destinationAccount).amount(amount)
				.transferDate(transferDate).build();
	}

}
