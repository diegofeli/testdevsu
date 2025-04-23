package com.api.account.repository;

import com.api.persistence.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccountRepository  extends JpaRepository<Account, Integer> {

    List<Account> findByNumberIn(List<String> ids);

    Optional<Account> findByNumber(String number);

    @Query("""
    SELECT DISTINCT a FROM Account a
    JOIN FETCH a.client c
    JOIN FETCH a.typeAccount t
    LEFT JOIN FETCH a.transactions t
    JOIN FETCH t.transactionType tp
    WHERE (c.identity = :clientId)
      AND (t.createdDate >= :startDate)
      AND (t.createdDate <= :endDate)
    """)
    List<Account> findAccountsWithFilters(
            @Param("clientId") String clientId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
