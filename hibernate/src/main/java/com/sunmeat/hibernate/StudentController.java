package com.sunmeat.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;

/**
 * REST-контроллер для управления объектами {@link Student}.
 * Обрабатывает HTTP-запросы, связанные с CRUD-операциями над студентами.
 * 
 * @author Олександр Загоруйко
 * @version 1.0.0.0
 * @since 12.11.2024
 */
@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private CourseService courseService;
	
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private GroupService groupService;

    @Autowired
    private StudentRepo studentRepository;

    /**
     * Получает информацию о студентах. Если указан параметр {@code id}, возвращает 
     * информацию о студенте с данным ID, иначе возвращает список всех студентов.
     *
     * @param id необязательный параметр ID студента
     * @return {@link ResponseEntity} с объектом {@link Student} или списком студентов
     */
    @GetMapping
    public ResponseEntity<Object> getStudentOrAll(@RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            Student student = studentRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент с ID " + id + " не найден"));
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.ok(studentRepository.findAll());
    }

    /**
     * Удаляет студента с указанным ID.
     *
     * @param id ID студента для удаления
     * @return {@link ResponseEntity} с сообщением об успешном удалении
     *         или информацией об ошибке
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam(name = "id") Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Студент успешно удален.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok("Студент с ID " + id + " не найден.");
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Конфликт версии при удалении студента с ID " + id);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении студента: " + e.getMessage());
        }
    }

    /**
     * Создает или обновляет объект {@link Student}.
     * Если параметр {@code _method} равен "PUT" и указан {@code id},
     * выполняется обновление, иначе создается новый объект.
     *
     * @param _method метод операции ("PUT" для обновления, любой другой — для создания)
     * @param id      ID студента (необязательный, используется для обновления)
     * @param name    имя студента
     * @param email   email студента
     * @return {@link ResponseEntity} с сообщением о создании или обновлении студента
     */
    @PostMapping
    public ResponseEntity<String> saveStudent(@RequestParam(required = false, name = "_method") String _method,
                                              @RequestParam(required = false, name = "id") Long id,
                                              @RequestParam(name = "name") String name,
                                              @RequestParam(name = "email") String email,
                                              @RequestParam(required = false, name = "groupId") Long groupId,
                                              @RequestParam(required = false, name = "courseId") Long courseId) { // Добавляем параметр для курса
        if ("PUT".equalsIgnoreCase(_method) && id != null) {
            return studentRepository.findById(id).map(student -> {
                student.setName(name);
                student.setEmail(email);
                if (groupId != null) {
                    Group group = groupService.findById(groupId);
                    student.setGroup(group);
                }
                if (courseId != null) {
                    Course course = courseService.findById(courseId); // Получаем курс по ID
                    student.setCourse(course); // Устанавливаем курс
                }
                studentRepository.save(student);
                return ResponseEntity.ok("Студент обновлен: " + student.getName());
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден"));
        } else {
            var newStudent = new Student();
            newStudent.setName(name);
            newStudent.setEmail(email);
            if (groupId != null) {
                Group group = groupService.findById(groupId);
                newStudent.setGroup(group);
            }
            if (courseId != null) {
                Course course = courseService.findById(courseId); // Получаем курс по ID
                newStudent.setCourse(course); // Устанавливаем курс
            }
            studentRepository.save(newStudent);
            return ResponseEntity.status(HttpStatus.CREATED).body("Студент создан: " + newStudent.getName());
        }
    }
}