package com.api.account.repository;

import com.api.persistence.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountIdIn(List<Integer> ids);

    List<Transaction> findByAccountId(Integer accountId);
}
