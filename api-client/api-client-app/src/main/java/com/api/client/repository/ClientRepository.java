package com.api.client.repository;

import com.api.persistence.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	List<Client> findByIdIn(List<Integer> ids);
}