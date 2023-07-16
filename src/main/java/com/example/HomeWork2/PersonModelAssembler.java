package com.example.HomeWork2;

import com.example.HomeWork2.controllers.PersonController;
import com.example.HomeWork2.entity.Person;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {
    @Override
    @NonNull
    public EntityModel<Person> toModel(@NonNull Person person) {
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).getAll()).withRel("persons"));
    }

}
