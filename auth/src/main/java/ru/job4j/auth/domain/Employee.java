package ru.job4j.auth.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "employee")
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;

    private String lastname;

    private String ssn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date hiredate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "employee_accounts",
            joinColumns = @JoinColumn(name = "employee_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "accounts_id", nullable = false))

    private List<Person> accounts;

    public static Employee of(int id, String firstName, String lastName,
                              String ssn, Date hireDate, List<Person> accounts) {
        Employee employee = new Employee();
        employee.id = id;
        employee.firstname = firstName;
        employee.lastname = lastName;
        employee.ssn = ssn;
        employee.hiredate = hireDate;
        employee.accounts = accounts;
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hireDate) {
        this.hiredate = hireDate;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", firstName='" + firstname + '\''
                + ", lastName='" + lastname + '\''
                + ", ssn='" + ssn + '\''
                + ", hireDate=" + hiredate
                + ", accounts=" + accounts
                + '}';
    }
}
