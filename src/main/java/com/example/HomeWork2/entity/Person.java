package com.example.HomeWork2.entity;

import com.example.HomeWork2.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="FIRST_NAME", length = 50, nullable = true)
    private String firstname;
    @Column(name="LAST_NAME", length = 50, nullable = true)
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTH_DATE", length = 10, updatable = false)
    private LocalDate dateOfBirth; //need validation...
    @Column (name="PHONE_NUMBER", length = 50, nullable = true)
    private String phoneNumber; //need validation...

    private @Column (nullable = false) String email;
    public Person() {}

    public Person(String firstname, String lastname, Gender gender, LocalDate dateOfBirth, String phoneNumber, String email) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
