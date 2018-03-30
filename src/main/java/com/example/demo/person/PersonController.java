package com.example.demo.person;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonDao personDao;

    @Autowired
    public PersonController(Jdbi jdbi) {
        this.personDao = jdbi.onDemand(PersonDao.class);
    }

    @GetMapping
    public List<Person> getPersons() {
        return personDao.getPersons().toJavaList();
    }

}
