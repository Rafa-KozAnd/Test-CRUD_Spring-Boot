package com.meuprojeto.crud_spring_boot.controller;

import com.meuprojeto.crud_spring_boot.model.Person;
import com.meuprojeto.crud_spring_boot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        person.setName(personDetails.getName());
        person.setPhoneNumber(personDetails.getPhoneNumber());
        person.setEmailAddress(personDetails.getEmailAddress());

        Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        personRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
