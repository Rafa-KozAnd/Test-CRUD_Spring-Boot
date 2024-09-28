package com.meuprojeto.crud_spring_boot.repository;

import com.meuprojeto.crud_spring_boot.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
