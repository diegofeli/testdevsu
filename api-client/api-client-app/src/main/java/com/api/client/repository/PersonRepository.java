package com.api.client.repository;

import com.api.persistence.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	List<Person> findByIdentityIn(List<String> ids);

	Optional<Person> findByIdentity(String identity);

}