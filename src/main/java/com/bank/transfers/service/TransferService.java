package com.bank.transfers.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.transfers.domain.Transfer;
import com.bank.transfers.domain.TransferDTO;
import com.bank.transfers.exception.FeeNotFoundException;
import com.bank.transfers.exception.InvallidAccountException;
import com.bank.transfers.repository.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository repository;

    public Transfer scheduleTransfer(TransferDTO dto) {
        validateBusinessRules(dto);

        var schedulingDate = LocalDate.now();
        var days = ChronoUnit.DAYS.between(schedulingDate, dto.getTransferDate());

        var fee = calculateFee(dto.getAmount(), days);

        var transfer = Transfer.builder()
                .sourceAccount(dto.getSourceAccount())
                .destinationAccount(dto.getDestinationAccount())
                .amount(dto.getAmount())
                .fee(fee)
                .schedulingDate(schedulingDate)
                .transferDate(dto.getTransferDate())
                .build();

        return repository.save(transfer);
    }

    public List<Transfer> listTransfers() {
        return repository.findAll();
    }

    private void validateBusinessRules(TransferDTO dto) {
        if (dto.getSourceAccount().equals(dto.getDestinationAccount())) {
            throw new InvallidAccountException();
        }
    }

    private BigDecimal calculateFee(BigDecimal amount, long days) {
        if (days == 0) {
            return new BigDecimal("3.00")
                    .add(amount.multiply(new BigDecimal("0.025")));
        } else if (days >= 1 && days <= 10) {
            return new BigDecimal("12.00");
        } else if (days >= 11 && days <= 20) {
            return amount.multiply(new BigDecimal("0.082"));
        } else if (days >= 21 && days <= 30) {
            return amount.multiply(new BigDecimal("0.069"));
        } else if (days >= 31 && days <= 40) {
            return amount.multiply(new BigDecimal("0.047"));
        } else if (days >= 41 && days <= 50) {
            return amount.multiply(new BigDecimal("0.017"));
        }

        throw new FeeNotFoundException();
    }
}