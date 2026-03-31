package com.bank.transfers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.transfers.domain.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}