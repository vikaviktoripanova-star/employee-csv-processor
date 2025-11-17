package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit тесты для CsvPersonReader.
 * 
 * @author Panova Viktoria
 * @version 1.0
 * @see CsvPersonReader
 */
class CsvPersonReaderTest {
    private CsvPersonReader reader;
    
    /**
     * Инициализация перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        reader = new CsvPersonReader();
    }
    
    /**
     * Тест успешного чтения CSV файла.
     */
    @Test
    void testReadPeopleFromCsv_Success() throws IOException {
        // When
        List<Person> people = reader.readPeopleFromCsv("foreign_names.csv");
        
        // Then
        assertNotNull(people);
        assertFalse(people.isEmpty());
        
        // Проверяем первую запись
        Person firstPerson = people.get(0);
        assertEquals(28281, firstPerson.getId());
        assertEquals("Aahan", firstPerson.getName());
        assertEquals(Gender.MALE, firstPerson.getGender());
        assertEquals(4800.0, firstPerson.getSalary(), 0.001);
        assertNotNull(firstPerson.getDepartment());
    }
    
    /**
     * Тест обработки отсутствующего файла.
     */
    @Test
    void testReadPeopleFromCsv_FileNotFound() {
        // When & Then
        assertThrows(FileNotFoundException.class, () -> {
            reader.readPeopleFromCsv("nonexistent.csv");
        });
    }
    
    /**
     * Тест преобразования строк в Gender.
     */
    @Test
    void testGenderFromString_ValidValues() {
        assertEquals(Gender.MALE, Gender.fromString("Male"));
        assertEquals(Gender.MALE, Gender.fromString("MALE"));
        assertEquals(Gender.MALE, Gender.fromString("M"));
        assertEquals(Gender.FEMALE, Gender.fromString("Female"));
        assertEquals(Gender.FEMALE, Gender.fromString("FEMALE"));
        assertEquals(Gender.FEMALE, Gender.fromString("F"));
    }
    
    /**
     * Тест неверного значения Gender.
     */
    @Test
    void testGenderFromString_InvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            Gender.fromString("Unknown");
        });
    }
    
    /**
     * Тест null значения для Gender.
     */
    @Test
    void testGenderFromString_NullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            Gender.fromString(null);
        });
    }
    
    /**
     * Тест методов Gender.
     */
    @Test
    void testGenderMethods() {
        assertEquals("Male", Gender.MALE.getFullName());
        assertEquals("M", Gender.MALE.getShortCode());
        assertEquals("Female", Gender.FEMALE.getFullName());
        assertEquals("F", Gender.FEMALE.getShortCode());
        
        assertTrue(Gender.MALE.isMale());
        assertFalse(Gender.MALE.isFemale());
        assertTrue(Gender.FEMALE.isFemale());
        assertFalse(Gender.FEMALE.isMale());
        
        assertEquals("Male", Gender.MALE.toString());
    }
    
    /**
     * Тест уникальности ID подразделений.
     */
    @Test
    void testDepartmentCreation_UniqueIds() {
        Department dept1 = new Department("IT");
        Department dept2 = new Department("HR");
        Department dept3 = new Department("Finance");
        
        assertNotEquals(dept1.getId(), dept2.getId());
        assertNotEquals(dept2.getId(), dept3.getId());
        assertNotEquals(dept1.getId(), dept3.getId());
    }
    
    /**
     * Тест equals и hashCode Department.
     */
    @Test
    void testDepartmentEqualsAndHashCode() {
        Department dept1 = new Department("IT");
        Department dept2 = new Department("IT");
        Department dept3 = new Department("HR");
        
        assertEquals(dept1, dept2);
        assertNotEquals(dept1, dept3);
        assertEquals(dept1.hashCode(), dept2.hashCode());
    }
    
    /**
     * Тест equals и hashCode Person.
     */
    @Test
    void testPersonEqualsAndHashCode() {
        Department dept = new Department("IT");
        Person person1 = new Person(1, "John", Gender.MALE, 
            java.time.LocalDate.of(1990, 1, 1), dept, 5000.0);
        Person person2 = new Person(1, "Jane", Gender.FEMALE, 
            java.time.LocalDate.of(1991, 2, 2), dept, 6000.0);
        Person person3 = new Person(2, "John", Gender.MALE, 
            java.time.LocalDate.of(1990, 1, 1), dept, 5000.0);
        
        assertEquals(person1, person2);
        assertNotEquals(person1, person3);
        assertEquals(person1.hashCode(), person2.hashCode());
    }
}