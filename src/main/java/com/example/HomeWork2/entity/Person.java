package com.example.HomeWork2.entity;

import com.example.HomeWork2.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.PeriodFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="FIRST_NAME", length = 50, nullable = true)
    private String firstName;
    @Column(name="LAST_NAME", length = 50, nullable = true)
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTH_DATE", length = 10, updatable = false)
    private LocalDate dateOfBirth; //need validation...
    @Column (name="PHONE_NUMBER", length = 50, nullable = true)
    private String phoneNumber; //need validation...

    private @Column (nullable = false) String email;
    public Person() {}

    public Person(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, String phoneNumber, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
