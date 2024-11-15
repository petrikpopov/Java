package com.sunmeat.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.List;

/**
 * Клас, що представляє студента.
 * <p>
 * Цей клас містить інформацію про студента, таку як ім'я, електронну пошту та ідентифікатор.
 * Містить конструктори для ініціалізації цих полів та відповідні методи доступу.
 * </p>
 * 
 * @author Олександр Загоруйко
 * @version 1.0.0.0
 * @since 11.11.2024
 */
@Entity
@Table(name = "students")
public class Student {

    /**
     * Версія сутності для керування її версіями при використанні JPA.
     */
    @Version
    private Long version;

    /**
     * Унікальний ідентифікатор студента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ім'я студента.
     */
    private String name;

    /**
     * Електронна пошта студента.
     */
    private String email;

    /**
     * Група, до якої належить студент.
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    /**
     * Список курсів, на які записаний студент.
     */
    @ManyToMany
    @JoinTable(
        name = "student_courses",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
    
    /**
     * Конструктор без параметрів. Ініціалізує поля значеннями за замовчуванням.
     */
    public Student() {
    }

    /**
     * Конструктор, що ініціалізує ім'я та електронну пошту студента.
     * @param name Ім'я студента
     * @param email Електронна пошта студента
     */
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    /**
     * Конструктор, що ініціалізує ім'я, електронну пошту студента та групу.
     * @param name Ім'я студента
     * @param email Електронна пошта студента
     * @param group Група студента
     */
    public Student(String name, String email, Group group) {
        this.name = name;
        this.email = email;
        this.group = group;
    }

    /**
     * Отримати ідентифікатор студента.
     * @return Ідентифікатор студента
     */
    public Long getId() {
        return id;
    }

    /**
     * Встановити ідентифікатор студента.
     * @param id Ідентифікатор студента
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Отримати ім'я студента.
     * @return Ім'я студента
     */
    public String getName() {
        return name;
    }

    /**
     * Встановити ім'я студента.
     * @param name Ім'я студента
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Отримати електронну пошту студента.
     * @return Електронна пошта студента
     */
    public String getEmail() {
        return email;
    }

    /**
     * Встановити електронну пошту студента.
     * @param email Електронна пошта студента
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    /**
     * Повертає строкове подання студента.
     * @return JSON-Строка з інформацією про студента
     */
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', group=" + (group != null ? group.getName() : "Не в группе") +
                ", course=" + (course != null ? course.getName() : "Не на курсе") + "}";
    }

    /**
     * Виводить всю інформацію про студента на екран.
     * <p>
     * Цей метод виводить ім'я та електронну пошту студента у вигляді форматованого рядка.
     * </p>
     */
    public void print() {
        System.out.println("Ідентифікатор: " + id);
        System.out.println("Ім'я: " + name);
        System.out.println("Електронна пошта: " + email);
    }
}