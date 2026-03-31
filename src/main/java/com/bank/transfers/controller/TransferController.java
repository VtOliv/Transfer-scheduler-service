package com.bank.transfers.controller;

import com.bank.transfers.domain.Transfer;
import com.bank.transfers.domain.TransferDTO;
import com.bank.transfers.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Transfer> scheduleTransfer(@Valid @RequestBody TransferDTO transferDTO) {

        log.info("Recebida requisição de agendamento - origem: {}, destino: {}, valor: {}, dataTransferencia: {}",
                transferDTO.getSourceAccount(),
                transferDTO.getDestinationAccount(),
                transferDTO.getAmount(),
                transferDTO.getTransferDate()
        );

        Transfer transfer = transferService.scheduleTransfer(transferDTO);

        log.info("Transferência agendada com sucesso - id: {}, taxa aplicada: {}, dataAgendamento: {}",
                transfer.getId(),
                transfer.getFee(),
                transfer.getSchedulingDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> listTransfers() {

        log.info("Requisição para listar todas as transferências");

        List<Transfer> transfers = transferService.listTransfers();

        log.info("Quantidade de transferências encontradas: {}", transfers.size());

        return ResponseEntity.ok(transfers);
    }
}