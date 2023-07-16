package com.example.HomeWork2.repository;

import com.example.HomeWork2.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFirstNameOrLastName(String firstName, String lastName);
    List<Person> findByDateOfBirth(LocalDate dateOfBirth);

}
