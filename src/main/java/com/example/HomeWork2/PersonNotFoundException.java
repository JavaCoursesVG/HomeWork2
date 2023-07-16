package com.example.HomeWork2;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(Long id) {
        super("Could not find person by ID " + id);
    }
    public PersonNotFoundException(String parameter) {
        super("Could not find person by " + parameter);
    }

}
