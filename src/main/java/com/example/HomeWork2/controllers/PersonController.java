package com.example.HomeWork2.controllers;

import com.example.HomeWork2.validators.DateValidator;
import com.example.HomeWork2.DateValidatorUsingDateTimeFormatter;
import com.example.HomeWork2.PersonModelAssembler;
import com.example.HomeWork2.PersonNotFoundException;
import com.example.HomeWork2.entity.Person;
import com.example.HomeWork2.repository.PersonRepository;
import jakarta.annotation.Nullable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {
    private final PersonRepository repository;
    private final PersonModelAssembler assembler;


    public PersonController(PersonRepository repository, PersonModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    DateValidator validator = new DateValidatorUsingDateTimeFormatter(dateFormatter);

    @GetMapping("/persons/")
    public CollectionModel<EntityModel<Person>> getAll() {

        List<EntityModel<Person>> persons = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(persons, linkTo(methodOn(PersonController.class).getAll()).withSelfRel());
    }

    @PostMapping("/persons/")
    Person newPerson ( @RequestBody Person newPerson) {return repository.save(newPerson);}

    @GetMapping("/persons/{id}")
    public EntityModel<Person> one(@PathVariable Long id) {

        Person person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return assembler.toModel(person);
    }

    @PutMapping("/persons/{id}")
    Person replacePerson(@Validated @RequestBody Person newPerson, @PathVariable Long id) {

        return repository.findById(id)
                .map(person -> {
                    if (newPerson.getFirstname() != null) {
                        person.setFirstname(newPerson.getFirstname()); // if we receive object with "null" fields validation prevent to change them
                    }
                    if (newPerson.getLastname() != null) {
                        person.setLastname(newPerson.getLastname());
                    }
                    if (newPerson.getGender() != null) {
                        person.setGender(newPerson.getGender());
                    }
                    if (newPerson.getDateOfBirth() != null) {
                        person.setDateOfBirth(newPerson.getDateOfBirth());
                    }
                    if (newPerson.getPhoneNumber() != null) {
                        person.setPhoneNumber(newPerson.getPhoneNumber());
                    }
                    if (newPerson.getEmail() != null) {
                        person.setEmail(newPerson.getEmail());
                    }
                    return repository.save(person);
                })
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @DeleteMapping("/persons/{id}")
    String deletePerson(@PathVariable Long id) throws PersonNotFoundException{
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return "Person with id = " + id + " was deleted.";
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @GetMapping("/persons/findByName")
    CollectionModel<EntityModel<Person>> getPersonsByName(@Nullable @RequestParam String firstname, String lastname) throws PersonNotFoundException {

        List<EntityModel<Person>> response = repository.findByFirstnameOrLastname(firstname, lastname)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(response, linkTo(methodOn(PersonController.class).getAll()).withSelfRel());
    }

    @GetMapping("/persons/findByBirthDate")
    CollectionModel<EntityModel<Person>> getPersonsByBirthDate(@RequestParam String dateOfBirth) throws PersonNotFoundException {

        if (validator.isValid(dateOfBirth)) {
            LocalDate birthDate = LocalDate.parse(dateOfBirth);
            if (repository.findByDateOfBirth(birthDate).isEmpty()) throw new PersonNotFoundException("this date: " + dateOfBirth);
            else {
                List<EntityModel<Person>> response = repository.findByDateOfBirth(birthDate)
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

                return CollectionModel.of(response, linkTo(methodOn(PersonController.class).getAll()).withSelfRel());
            }
        } else throw new PersonNotFoundException("invalid date format");
    }
}
