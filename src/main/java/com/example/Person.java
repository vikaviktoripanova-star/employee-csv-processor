package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс, представляющий сотрудника организации.
 */
public class Person {
    private final int id;
    private final String name;
    private final Gender gender;
    private final LocalDate birthDate;
    private final Department department;
    private final double salary;
    
    /**
     * Конструктор сотрудника.
     * 
     * @param id идентификатор сотрудника
     * @param name имя сотрудника
     * @param gender пол сотрудника
     * @param birthDate дата рождения
     * @param department подразделение
     * @param salary зарплата
     */
    public Person(int id, String name, Gender gender, LocalDate birthDate, 
                  Department department, double salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.department = department;
        this.salary = salary;
    }
    
    // Геттеры
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public double getSalary() {
        return salary;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "Person{id=" + id + ", name='" + name + "', gender=" + gender + 
               ", birthDate=" + birthDate.format(formatter) + ", department=" + department + 
               ", salary=" + salary + '}';
    }
}