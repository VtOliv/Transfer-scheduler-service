package com.bank.transfers.service;

import static com.bank.transfers.factory.TransferFactory.dtoMockBuilder;
import static com.bank.transfers.factory.TransferFactory.transferListMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.transfers.domain.Transfer;
import com.bank.transfers.exception.FeeNotFoundException;
import com.bank.transfers.exception.InvallidAccountException;
import com.bank.transfers.repository.TransferRepository;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	@Mock
	private TransferRepository repository;

	@InjectMocks
	private TransferService service;

	@Test
	void shouldScheduleTransferWithFeeForSameDay() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("100.00"), LocalDate.now());

		when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var result = service.scheduleTransfer(dto);

		assertNotNull(result);
		assertEquals("1234567890", result.getSourceAccount());
		assertEquals("0987654321", result.getDestinationAccount());
		assertEquals(LocalDate.now(), result.getSchedulingDate());
		assertEquals(LocalDate.now(), result.getTransferDate());
		assertEquals(0, result.getFee().compareTo(new BigDecimal("5.50")));

		var captor = ArgumentCaptor.forClass(Transfer.class);
		verify(repository, times(1)).save(captor.capture());

		var savedTransfer = captor.getValue();
		assertEquals(0, savedTransfer.getFee().compareTo(new BigDecimal("5.50")));
	}

	@Test
	void shouldScheduleTransferWithFixedFeeBetweenOneAndTenDays() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("250.00"), LocalDate.now().plusDays(5));

		when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var result = service.scheduleTransfer(dto);

		assertNotNull(result);
		assertEquals(0, result.getFee().compareTo(new BigDecimal("12.00")));
		verify(repository, times(1)).save(any(Transfer.class));
	}

	@Test
	void shouldScheduleTransferWithFeeBetweenElevenAndTwentyDays() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("1000.00"),
				LocalDate.now().plusDays(15));

		when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var result = service.scheduleTransfer(dto);

		assertNotNull(result);
		assertEquals(0, result.getFee().compareTo(new BigDecimal("82.00")));
		verify(repository, times(1)).save(any(Transfer.class));
	}

	@Test
	void shouldScheduleTransferWithFeeBetweenTwentyOneAndThirtyDays() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("1000.00"),
				LocalDate.now().plusDays(25));

		when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var result = service.scheduleTransfer(dto);

		assertNotNull(result);
		assertEquals(0, result.getFee().compareTo(new BigDecimal("69.00")));
		verify(repository, times(1)).save(any(Transfer.class));
	}

	@Test
	void shouldScheduleTransferWithFeeBetweenThirtyOneAndFortyDays() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("1000.00"),
				LocalDate.now().plusDays(35));

		when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var result = service.scheduleTransfer(dto);

		assertNotNull(result);
		assertEquals(0, result.getFee().compareTo(new BigDecimal("47.00")));
		verify(repository, times(1)).save(any(Transfer.class));
	}

	@Test
	void shouldScheduleTransferWithFeeBetweenFortyOneAndFiftyDays() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("1000.00"),
				LocalDate.now().plusDays(45));

		when(repository.save(any(Transfer.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var result = service.scheduleTransfer(dto);

		assertNotNull(result);
		assertEquals(0, result.getFee().compareTo(new BigDecimal("17.00")));
		verify(repository, times(1)).save(any(Transfer.class));
	}

	@Test
	void shouldThrowExceptionWhenTransferDateIsOutOfAllowedRange() {
		var dto = dtoMockBuilder("1234567890", "0987654321", new BigDecimal("100.00"),
				LocalDate.now().plusDays(60));

		assertThrows(FeeNotFoundException.class, () -> service.scheduleTransfer(dto));
		verify(repository, never()).save(any(Transfer.class));
	}

	@Test
	void shouldThrowExceptionWhenSourceAndDestinationAccountsAreEqual() {
		var dto = dtoMockBuilder("1234567890", "1234567890", new BigDecimal("100.00"),
				LocalDate.now().plusDays(5));

		var exception = assertThrows(InvallidAccountException.class,
				() -> service.scheduleTransfer(dto));

		assertEquals("A conta de origem deve ser diferente da conta de destino.", exception.getMessage());
		verify(repository, never()).save(any(Transfer.class));
	}

	@Test
	void shouldListAllTransfers() {
		var transfers = transferListMock();

		when(repository.findAll()).thenReturn(transfers);

		var result = service.listTransfers();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("1234567890", result.get(0).getSourceAccount());
		assertEquals("2222222222", result.get(1).getDestinationAccount());
		verify(repository, times(1)).findAll();
	}
}