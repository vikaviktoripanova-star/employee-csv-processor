package com.example;

import java.util.Objects;

/**
 * Класс, представляющий подразделение организации.
 * ID генерируется автоматически при создании объекта.
 * 
 * @author Panova Viktoria
 * @version 1.0
 * @see Person
 */
public class Department {
    private static int nextId = 1;
    
    private final int id;
    private final String name;
    
    /**
     * Конструктор подразделения.
     * 
     * @param name название подразделения
     */
    public Department(String name) {
        this.id = nextId++;
        this.name = name;
    }
    
    /**
     * Возвращает идентификатор подразделения.
     * 
     * @return идентификатор подразделения
     */
    public int getId() {
        return id;
    }
    
    /**
     * Возвращает название подразделения.
     * 
     * @return название подразделения
     */
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }
}