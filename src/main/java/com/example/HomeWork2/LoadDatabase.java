package com.example.HomeWork2;

import com.example.HomeWork2.entity.Person;
import com.example.HomeWork2.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

import static com.example.HomeWork2.entity.enums.Gender.*;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {

        return args -> {
            log.info("Preloading" + repository.save(new Person(
                    "Anna 1",
                    "Gosteva 1",
                    FEMALE,
                    LocalDate.of(1984, Calendar.FEBRUARY + 1, 11),
                    "+371 88 888 888",
                    "anna.gosteva@gmail.com"
                    )));
            log.info("Preloading" + repository.save(new Person(
                    "Vadims 1",
                    "Gostevs 1",
                    MALE,
                    LocalDate.of(1983, Calendar.JUNE + 1,29),
                    "+371 99 999 999",
                    "vadims.gostevs@gmail.com"
            )));
        };
    }
}
