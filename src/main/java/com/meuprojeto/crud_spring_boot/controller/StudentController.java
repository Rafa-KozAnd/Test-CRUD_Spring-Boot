package com.meuprojeto.crud_spring_boot.controller;

import com.meuprojeto.crud_spring_boot.model.Student;
import com.meuprojeto.crud_spring_boot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students); // Certifique-se de que este retorno está correto
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        student.setStudentNumber(studentDetails.getStudentNumber());
        student.setPhoto(studentDetails.getPhoto());

        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        studentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
