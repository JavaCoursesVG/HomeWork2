package com.example.HomeWork2;

public class PersonNotFoundException extends RuntimeException{
    PersonNotFoundException(Long id) {
        super("Could not find person by ID " + id);
    }
    PersonNotFoundException(String parameter) {
        super("Could not find person by " + parameter);
    }

}
