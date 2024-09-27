package com.meuprojeto.controller;

import com.meuprojeto.model.Professor;
import com.meuprojeto.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    @PostMapping
    public Professor createProfessor(@RequestBody Professor professor) {
        return professorRepository.save(professor);
    }

    @PutMapping("/{id}")
    public Professor updateProfessor(@PathVariable Long id, @RequestBody Professor professorDetails) {
        Professor professor = professorRepository.findById(id).orElseThrow();
        
        // Atualiza os campos herdados de Person
        professor.setName(professorDetails.getName());
        professor.setPhoneNumber(professorDetails.getPhoneNumber());
        professor.setEmailAddress(professorDetails.getEmailAddress());

        // Atualiza o campo específico de Professor
        professor.setSalary(professorDetails.getSalary());

        return professorRepository.save(professor);
    }

    @DeleteMapping("/{id}")
    public void deleteProfessor(@PathVariable Long id) {
        professorRepository.deleteById(id);
    }
}
