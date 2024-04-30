package com.repository;


import com.entity.Person;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Basic repository for person {@link Person}.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  List<Person> findByDateOfBirthBetween(LocalDate from, LocalDate to);
  

}
