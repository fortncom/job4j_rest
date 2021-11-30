package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Employee;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Override
    @Query("select e from employee e left join fetch e.accounts where e.id=?1")
    Optional<Employee> findById(Integer integer);

    @Override
    @Query("select distinct e from employee e left join fetch e.accounts")
    Iterable<Employee> findAll();
}
