package com.meuprojeto.crud_spring_boot.repository;

import com.meuprojeto.crud_spring_boot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
