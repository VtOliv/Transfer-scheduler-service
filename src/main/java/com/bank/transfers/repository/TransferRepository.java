package com.bank.transfers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.transfers.domain.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}