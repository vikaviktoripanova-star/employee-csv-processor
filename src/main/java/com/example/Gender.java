package com.example;

/**
 * Перечисление для представления пола сотрудника.
 * Поддерживает преобразование из строковых значений.
 * 
 * @author Panova Viktoria
 * @version 1.0
 * @see Person
 */
public enum Gender {
    MALE("Male", "M"),
    FEMALE("Female", "F");
    
    private final String fullName;
    private final String shortCode;
    
    /**
     * Конструктор перечисления Gender.
     * 
     * @param fullName полное название пола
     * @param shortCode короткий код пола
     */
    Gender(String fullName, String shortCode) {
        this.fullName = fullName;
        this.shortCode = shortCode;
    }
    
    /**
     * Преобразует строковое значение в enum Gender.
     * 
     * @param value строковое значение пола
     * @return соответствующий enum Gender
     * @throws IllegalArgumentException если значение не распознано
     */
    public static Gender fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender value cannot be null or empty");
        }
        
        String normalized = value.trim().toUpperCase();
        
        switch (normalized) {
            case "MALE":
            case "M":
                return MALE;
            case "FEMALE":
            case "F":
                return FEMALE;
            default:
                throw new IllegalArgumentException("Unknown gender value: " + value);
        }
    }
    
    /**
     * Возвращает полное название пола.
     * 
     * @return полное название
     */
    public String getFullName() {
        return fullName;
    }
    
    /**
     * Возвращает короткий код пола.
     * 
     * @return короткий код
     */
    public String getShortCode() {
        return shortCode;
    }
    
    /**
     * Проверяет, является ли пол мужским.
     * 
     * @return true если мужской
     */
    public boolean isMale() {
        return this == MALE;
    }
    
    /**
     * Проверяет, является ли пол женским.
     * 
     * @return true если женский
     */
    public boolean isFemale() {
        return this == FEMALE;
    }
    
    @Override
    public String toString() {
        return fullName;
    }
}