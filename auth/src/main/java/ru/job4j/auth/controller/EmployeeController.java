package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final String API = "http://localhost:8080/person/";

    @Autowired
    private RestTemplate rest;

    private final EmployeeRepository employees;

    public EmployeeController(final EmployeeRepository employees) {
        this.employees = employees;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(employees.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Person> create(@PathVariable int id, @RequestBody Person person) {
        Employee employee = employees.findById(id).get();
        List<Person> accounts = employee.getAccounts();
        accounts.add(person);
        employees.save(employee);
        return new ResponseEntity<>(
                person,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Employee employee = employees.findById(id).get();
        employees.delete(employee);
        return ResponseEntity.ok().build();
    }
}
