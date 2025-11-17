package com.example;

import java.io.IOException;
import java.util.List;

/**
 * Главный класс приложения для демонстрации работы CSV парсера.
 * Считывает данные о сотрудниках из CSV файла и выводит статистику.
 * 
 * @author Panova Viktoria
 * @version 1.0
 * @see CsvPersonReader
 * @see Person
 * @see Department
 * @see Gender
 */
public class Main {
    
    /**
     * Точка входа в приложение.
     * Загружает данные из CSV файла, отображает первые 10 записей
     * и выводит статистическую информацию.
     * 
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        CsvPersonReader reader = new CsvPersonReader();
        
        try {
            List<Person> people = reader.readPeopleFromCsv("foreign_names.csv");
            
            // Выводим результат
            System.out.println("Successfully read " + people.size() + " people:");
            System.out.println("=========================================");
            
            // Выводим первые 10 записей для демонстрации
            people.stream().limit(10).forEach(System.out::println);
            
            // Статистика
            printStatistics(people);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data parsing error: " + e.getMessage());
        }
    }
    
    /**
     * Выводит статистическую информацию о сотрудниках.
     * Включает количество мужчин и женщин, уникальных подразделений,
     * а также данные о зарплатах (средняя, максимальная, минимальная).
     * 
     * @param people список сотрудников для анализа
     */
    private static void printStatistics(List<Person> people) {
        long maleCount = people.stream().filter(p -> p.getGender() == Gender.MALE).count();
        long femaleCount = people.stream().filter(p -> p.getGender() == Gender.FEMALE).count();
        
        long uniqueDepartments = people.stream()
            .map(Person::getDepartment)
            .distinct()
            .count();
        
        double avgSalary = people.stream()
            .mapToDouble(Person::getSalary)
            .average()
            .orElse(0.0);
        
        double maxSalary = people.stream()
            .mapToDouble(Person::getSalary)
            .max()
            .orElse(0.0);
        
        double minSalary = people.stream()
            .mapToDouble(Person::getSalary)
            .min()
            .orElse(0.0);
        
        System.out.println("\nStatistics:");
        System.out.printf("Total people: %d%n", people.size());
        System.out.printf("Male: %d, Female: %d%n", maleCount, femaleCount);
        System.out.printf("Unique departments: %d%n", uniqueDepartments);
        System.out.printf("Average salary: %.2f%n", avgSalary);
        System.out.printf("Max salary: %.2f%n", maxSalary);
        System.out.printf("Min salary: %.2f%n", minSalary);
    }
}