package com.sunmeat.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с курсами.
 * Содержит бизнес-логику для операций CRUD с курсами.
 * 
 * @version 1.0.0.0
 * @since 12.11.2024
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Находит курс по его ID.
     * 
     * @param id идентификатор курса
     * @return курс с указанным ID
     * @throws EntityNotFoundException если курс не найден
     */
    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курс с ID " + id + " не найден"));
    }

    /**
     * Возвращает список всех курсов.
     * 
     * @return список всех курсов
     */
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    /**
     * Сохраняет новый курс.
     * 
     * @param name название курса
     */
    @Transactional
    public void saveCourse(String name) {
        Course newCourse = new Course();
        newCourse.setName(name);
        courseRepository.save(newCourse);
    }

    /**
     * Обновляет курс по его ID.
     * 
     * @param id идентификатор курса
     * @param name новое название курса
     * @throws EntityNotFoundException если курс с таким ID не найден
     */
    @Transactional
    public void updateCourse(Long id, String name) {
        Course course = findById(id);
        course.setName(name);
        courseRepository.save(course);
    }

    /**
     * Удаляет курс по его ID.
     * 
     * @param id идентификатор курса
     * @throws EntityNotFoundException если курс с таким ID не найден
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Курс с ID " + id + " не найден");
        }
        courseRepository.deleteById(id);
    }
}
