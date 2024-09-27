package com.meuprojeto.controller;

import com.meuprojeto.model.Professor;
import com.meuprojeto.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        List<Professor> professors = professorRepository.findAll();
        return ResponseEntity.ok(professors);
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        Professor savedProfessor = professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor professorDetails) {
        Professor professor = professorRepository.findById(id).orElse(null);

        if (professor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        professor.setSalary(professorDetails.getSalary());

        Professor updatedProfessor = professorRepository.save(professor);
        return ResponseEntity.ok(updatedProfessor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        Professor professor = professorRepository.findById(id).orElse(null);

        if (professor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        professorRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
