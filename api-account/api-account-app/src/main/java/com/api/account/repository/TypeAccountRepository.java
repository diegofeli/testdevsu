package com.api.account.repository;

import com.api.persistence.domain.TypeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeAccountRepository extends JpaRepository<TypeAccount, Integer> {

    Optional<TypeAccount> findByName(String name);
}