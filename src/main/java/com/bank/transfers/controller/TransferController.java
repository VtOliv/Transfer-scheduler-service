package com.bank.transfers.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.transfers.domain.Transfer;
import com.bank.transfers.domain.TransferDTO;
import com.bank.transfers.service.TransferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/transfers")
@Tag(name = "Transfers", description = "Operações de agendamento de transferências")
public class TransferController {

    private final TransferService transferService;

    @Operation(summary = "Agendar transferência")
    @PostMapping
    public ResponseEntity<Transfer> scheduleTransfer(@Valid @RequestBody TransferDTO transferDTO) {

        log.info("Recebida requisição de agendamento - origem: {}, destino: {}, valor: {}, dataTransferencia: {}",
                transferDTO.getSourceAccount(),
                transferDTO.getDestinationAccount(),
                transferDTO.getAmount(),
                transferDTO.getTransferDate()
        );

        var transfer = transferService.scheduleTransfer(transferDTO);

        log.info("Transferência agendada com sucesso - id: {}, taxa aplicada: {}, dataAgendamento: {}",
                transfer.getId(),
                transfer.getFee(),
                transfer.getSchedulingDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
    }

    @Operation(summary = "Listar transferências")
    @GetMapping
    public ResponseEntity<List<Transfer>> listTransfers() {

        log.info("Requisição para listar todas as transferências");

        var transfers = transferService.listTransfers();

        log.info("Quantidade de transferências encontradas: {}", transfers.size());

        return ResponseEntity.ok(transfers);
    }
}