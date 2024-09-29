package com.meuprojeto.crud_spring_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuprojeto.crud_spring_boot.model.Person;
import com.meuprojeto.crud_spring_boot.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonRepository personRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person();
        person.setName("John Smith");
        person.setEmailAddress("john.smith@example.com");

        when(personRepository.save(any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personController.createPerson(person);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Smith", response.getBody().getName());
    }

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/persons/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdatePerson() {
        Long personId = 1L;
        Person existingPerson = new Person();
        existingPerson.setName("Old Name");
        existingPerson.setEmailAddress("old@example.com");

        Person updatedPersonDetails = new Person();
        updatedPersonDetails.setName("Updated Name");
        updatedPersonDetails.setEmailAddress("updated@example.com");

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPersonDetails); // Certifique-se de que está usando any()

        ResponseEntity<Person> response = personController.updatePerson(personId, updatedPersonDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Name", response.getBody().getName());
    }

    @Test
    public void testDeletePerson() throws Exception {
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/persons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
