package com.example;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Сервис для чтения данных о сотрудниках из CSV файлов.
 * 
 * @author Panova Viktoria 
 * @version 1.0
 * @see Person
 * @see Department
 * @see Gender
 */
public class CsvPersonReader {
    private static final char SEPARATOR = ';';
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    /**
     * Читает данные о сотрудниках из CSV файла.
     *
     * @param csvFilePath путь к CSV файлу в ресурсах
     * @return список сотрудников
     * @throws IOException если файл не найден или ошибка чтения
     * @throws IllegalArgumentException если данные некорректны
     */
    public List<Person> readPeopleFromCsv(String csvFilePath) throws IOException {
        List<Person> people = new ArrayList<>();
        Map<String, Department> departments = new HashMap<>();
        
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(csvFilePath);
             BufferedReader reader = in == null ? null : new BufferedReader(new InputStreamReader(in))) {
            
            if (reader == null) {
                throw new FileNotFoundException("File not found: " + csvFilePath);
            }
            
            // Пропускаем заголовок
            String line = reader.readLine();
            
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                try {
                    String[] fields = parseCsvLine(line);
                    Person person = parsePerson(fields, departments);
                    people.add(person);
                } catch (Exception e) {
                    System.err.printf("Error parsing line %d: %s. Line: %s%n", 
                        lineNumber, e.getMessage(), line);
                    throw new IllegalArgumentException(
                        "Invalid data at line " + lineNumber + ": " + e.getMessage(), e);
                }
            }
        }
        
        return people;
    }
    
    /**
     * Парсит строку CSV на отдельные поля.
     * 
     * @param line строка CSV файла
     * @return массив полей
     */
    private String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == SEPARATOR && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        
        fields.add(currentField.toString().trim());
        return fields.toArray(new String[0]);
    }
    
    /**
     * Создает объект Person из полей CSV.
     * 
     * @param fields массив полей CSV
     * @param departments карта подразделений
     * @return объект Person
     * @throws IllegalArgumentException если неверное количество полей
     */
    private Person parsePerson(String[] fields, Map<String, Department> departments) {
        if (fields.length < 6) {
            throw new IllegalArgumentException("Invalid number of fields: " + fields.length);
        }
        
        int id = Integer.parseInt(fields[0].trim());
        String name = fields[1].trim();
        Gender gender = Gender.fromString(fields[2].trim());
        LocalDate birthDate = LocalDate.parse(fields[3].trim(), DATE_FORMATTER);
        String divisionName = fields[4].trim();
        double salary = Double.parseDouble(fields[5].trim());
        
        Department department = departments.computeIfAbsent(
            divisionName, Department::new);
        
        return new Person(id, name, gender, birthDate, department, salary);
    }
}