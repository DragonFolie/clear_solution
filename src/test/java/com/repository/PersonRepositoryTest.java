package com.repository;

import com.entity.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

//    @AfterEach
//    public void


    @Test
    public void personRepositorySaveAllReturnSaved(){

        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        Person savedPerson = personRepository.save(person);

        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }


    @Test
    public void personRepositorySaveZeroAndReturnZeroPersonInList() {
        assertEquals(0, personRepository.findAll().size());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void personRepositoryReturnSavedListOfPerson(){

        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        Person person2 = Person.builder()
                .name("testName2")
                .surname("testSurname2")
                .email("testEmail2@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAdress2")
                .phoneNumber("+380653883388")
                .build();

        personRepository.deleteAll();
        personRepository.save(person);
        personRepository.save(person2);
        List<Person> personList = personRepository.findAll();

        assertNotNull(personList);
        assertEquals( 2,personList.size());
    }




    @Description("We save two person, first one with incorrect date, second one with correct date")
    @Test
    public void personReturnPersonFindById(){

        LocalDate from = LocalDate.of(1991, 1, 1);
        LocalDate to = LocalDate.of(1992, 12, 31);

        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        LocalDate testDateOfBirth2 = LocalDate.of(1991, 2, 2);
        Person person2 = Person.builder()
                .name("testName2")
                .surname("testSurname2")
                .email("testEmail2@gmail.com")
                .dateOfBirth(testDateOfBirth2)
                .address("testAddress2")
                .phoneNumber("+380653883388")
                .build();

        personRepository.save(person);
        personRepository.save(person2);

        assertEquals(1,personRepository.findByDateOfBirthBetween(from, to).size());

    }


    @Test
    public void deletePersonById(){


        LocalDate testDateOfBirth = LocalDate.of(1990, 1, 1);
        Person person = Person.builder()
                .name("testName")
                .surname("testSurname")
                .email("testEmail@gmail.com")
                .dateOfBirth(testDateOfBirth)
                .address("testAddress")
                .phoneNumber("+380653883388")
                .build();

        personRepository.save(person);
        personRepository.deleteById(person.getId());
        assertTrue(personRepository.findById(person.getId()).isEmpty());

    }

}