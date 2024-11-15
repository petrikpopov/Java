package com.sunmeat.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import java.util.List;

/**
 * REST-контроллер для управления объектами {@link Course}.
 * Обрабатывает HTTP-запросы, связанные с CRUD-операциями над курсами.
 * 
 * @author Олександр Загоруйко
 * @version 1.0.0.0
 * @since 12.11.2024
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Получает информацию о курсах. Если указан параметр {@code id}, возвращает 
     * информацию о курсе с данным ID, иначе возвращает список всех курсов.
     *
     * @param id необязательный параметр ID курса
     * @return {@link ResponseEntity} с объектом {@link Course} или списком курсов
     */
    @GetMapping
    public ResponseEntity<Object> getCourseOrAll(@RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            Course course = courseRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Курс с ID " + id + " не найден"));
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.ok(courseRepository.findAll());
    }

    /**
     * Удаляет курс с указанным ID.
     *
     * @param id ID курса для удаления
     * @return {@link ResponseEntity} с сообщением об успешном удалении
     *         или информацией об ошибке
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestParam(name = "id") Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Курс успешно удален.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok("Курс с ID " + id + " не найден.");
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Конфликт версии при удалении курса с ID " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении курса: " + e.getMessage());
        }
    }

    /**
     * Создает или обновляет объект {@link Course}.
     * Если параметр {@code _method} равен "PUT" и указан {@code id},
     * выполняется обновление, иначе создается новый объект.
     *
     * @param _method метод операции ("PUT" для обновления, любой другой — для создания)
     * @param id      ID курса (необязательный, используется для обновления)
     * @param name    имя курса
     * @return {@link ResponseEntity} с сообщением о создании или обновлении курса
     */
    @PostMapping
    public ResponseEntity<String> saveCourse(@RequestParam(required = false, name = "_method") String _method,
                                             @RequestParam(required = false, name = "id") Long id,
                                             @RequestParam(name = "name") String name) {
        if ("PUT".equalsIgnoreCase(_method) && id != null) {
            return courseRepository.findById(id).map(course -> {
                course.setName(name);
                courseRepository.save(course);
                return ResponseEntity.ok("Курс обновлен: " + course.getName());
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Курс не найден"));
        } else {
            var newCourse = new Course(name);
            courseRepository.save(newCourse);
            return ResponseEntity.status(HttpStatus.CREATED).body("Курс создан: " + newCourse.getName());
        }
    }
}
