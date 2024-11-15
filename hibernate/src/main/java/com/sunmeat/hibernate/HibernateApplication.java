package com.sunmeat.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска Spring Boot приложения.
 * <p>
 * Приложение запускается по адресу <a href="http://localhost:8080/">http://localhost:8080/</a>.
 * Для работы приложения требуется подключение к базе данных MySQL.
 * </p>
 * 
 * @author Олександр Загоруйко
 * @version 1.0.0.0
 * @since 12.11.2024
 */
@SpringBootApplication
public class HibernateApplication {

    /**
     * Точка входа в приложение.
     * 
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(HibernateApplication.class, args);
    }
}