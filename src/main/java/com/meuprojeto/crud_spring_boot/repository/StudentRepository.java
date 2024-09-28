package com.meuprojeto.crud_spring_boot.repository;

import com.meuprojeto.crud_spring_boot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
