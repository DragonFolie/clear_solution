package com.service.impl;

import com.entity.Person;
import com.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {


    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    @Test
    public void personServiceReturnCreateNotNullPerson(){

        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);
        Person savedPerson = personService.createPerson(person);

        assertNotNull(savedPerson);

    }


    @Test
    @Order(value = 1)
    public void personServiceReturnAllCreatedPerson(){

        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        List<Person>personList = new ArrayList<>();
        personList.add(person);

        when(personRepository.findAll()).thenReturn(personList);
        List<Person> allPerson =  personService.findAllPerson();

        assertEquals(1,allPerson.size());

    }


    @Test
    public void personServiceUpdatePerson(){

        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAdress")
                .phoneNumber("+380653883388")
                .build();

        Person updatedPerson = Person.builder()
                .name("test")
                .surname("test")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        when(personRepository.save(person)).thenReturn(updatedPerson);
        Person result = personService.updatePerson(person);

        assertEquals(updatedPerson, result);
        verify(personRepository, times(1)).save(person);
    }


    @Test
    void testDeleteExistingPersonById() {
        Long idToDelete = 1L;
        when(personRepository.existsById(idToDelete)).thenReturn(true);

        personService.deletePersonById(idToDelete);

        verify(personRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void testDeleteNonExistingPersonById() {
        Long idToDelete = 1L;
        when(personRepository.existsById(idToDelete)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> personService.deletePersonById(idToDelete));
        verify(personRepository, never()).deleteById(idToDelete);
    }


    @Test
    void findPersonByBirthDateRange() {

        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(1990, 12, 31);
        List<Person> expectedPersons = new ArrayList<>();

        LocalDate testDateOfBirth = LocalDate.of(1990, 5, 15);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();
        expectedPersons.add(person);

        LocalDate testDateOfBirth2 = LocalDate.of(1991, 5, 15);
        Person person2 = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth2)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();
        expectedPersons.add(person2);

        when(personRepository.findByDateOfBirthBetween(from, to)).thenReturn(expectedPersons);
        List<Person> actualPersons = personService.findPersonByBirthDateRange(from, to);

        Assertions.assertEquals(expectedPersons, actualPersons);
        verify(personRepository, times(1)).findByDateOfBirthBetween(from, to);
    }

}