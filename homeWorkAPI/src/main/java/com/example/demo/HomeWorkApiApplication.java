package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class HomeWorkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeWorkApiApplication.class, args);
	}

}

class Contact {
    private Long id;
    private String name;
    private String email;
    private String phone;

    public Contact() {}

    public Contact(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


@RestController
@RequestMapping("/api/contacts")
class ContactController {

    private final List<Contact> contacts = new ArrayList<>();

    public ContactController() {
        contacts.add(new Contact(1L, "John Doe", "john.doe@example.com", "123456789"));
        contacts.add(new Contact(2L, "Jane Smith", "jane.smith@example.com", "987654321"));
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}") // Отримання контакту за ідентифікатором
    public ResponseEntity<Contact> getContactById(@PathVariable("id") Long id) {
        Contact contact = contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Контакт з ID " + id + " не знайдений"));
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") Long id) {
        Contact contact = contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Контакт с ID " + id + " не найден"));

        contacts.remove(contact);

        String message = "Контакт с именем " + contact.getName() + " и ID " + contact.getId() + " был успешно удалён.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}


