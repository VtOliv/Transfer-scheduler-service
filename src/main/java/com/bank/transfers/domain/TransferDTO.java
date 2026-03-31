package com.bank.transfers.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
	
    @NotBlank(message = "A conta de origem é obrigatória")
    @Pattern(regexp = "\\d{10}", message = "A conta de origem deve conter exatamente 10 dígitos")
    private String sourceAccount;

    @NotBlank(message = "A conta de destino é obrigatória")
    @Pattern(regexp = "\\d{10}", message = "A conta de destino deve conter exatamente 10 dígitos")
    private String destinationAccount;

    @NotNull(message = "O valor da transferência é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "O valor da transferência deve ser maior que zero")
    private BigDecimal amount;

    @NotNull(message = "A data da transferência é obrigatória")
    @FutureOrPresent(message = "A data da transferência não pode ser no passado")
    private LocalDate transferDate;
}