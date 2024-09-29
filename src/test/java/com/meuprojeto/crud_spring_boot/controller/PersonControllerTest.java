package com.meuprojeto.crud_spring_boot.controller;

import com.meuprojeto.crud_spring_boot.model.Person;
import com.meuprojeto.crud_spring_boot.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setPhoneNumber("123456789");
        person.setEmailAddress("johndoe@example.com");
    }

    @Test
    void testGetAllPersons() {
        List<Person> persons = Arrays.asList(person);
        when(personRepository.findAll()).thenReturn(persons);

        List<Person> result = personController.getAllPersons();
        assertEquals(1, result.size());
        assertEquals(person.getName(), result.get(0).getName());
    }

    @Test
    void testGetPersonById_PersonFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.getPersonById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person.getName(), response.getBody().getName());
    }

    @Test
    void testGetPersonById_PersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Person> response = personController.getPersonById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreatePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personController.createPerson(person);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(person.getName(), response.getBody().getName());
    }

    @Test
    void testUpdatePerson_PersonFound() {
        Person updatedPersonDetails = new Person();
        updatedPersonDetails.setName("Jane Doe");
        updatedPersonDetails.setPhoneNumber("987654321");
        updatedPersonDetails.setEmailAddress("janedoe@example.com");

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPersonDetails);

        ResponseEntity<Person> response = personController.updatePerson(1L, updatedPersonDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane Doe", response.getBody().getName());
    }

    @Test
    void testUpdatePerson_PersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Person> response = personController.updatePerson(1L, person);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeletePerson_PersonFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        ResponseEntity<Void> response = personController.deletePerson(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePerson_PersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = personController.deletePerson(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(personRepository, times(0)).deleteById(1L);
    }
}
