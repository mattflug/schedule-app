package com.schedule.demo.controller;
import com.schedule.demo.model.Person;
import com.schedule.demo.model.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    PersonRepo repo;
    @PostMapping("/addPerson")
    public void addPerson(@RequestBody Person person) {
repo.save(person);
    }
}
