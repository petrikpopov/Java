package com.sunmeat.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepo groupRepository;

    @GetMapping
    public ResponseEntity<Object> getGroupOrAll(@RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            Group group = groupRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Группа с ID " + id + " не найдена"));
            return ResponseEntity.ok(group);
        }
        return ResponseEntity.ok(groupRepository.findAll());
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam(name = "id") Long id) {
        try {
        	groupService.deleteGroup(id);
            return ResponseEntity.ok("Группа успешно удалена.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok("Группа с ID " + id + " не найдена.");
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Конфликт версии при удалении группы с ID " + id);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении гурппы: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> saveGroup(@RequestParam(required = false, name = "_method") String _method,
                                              @RequestParam(required = false, name = "id") Long id,
                                              @RequestParam(name = "name") String name) {
        if ("PUT".equalsIgnoreCase(_method) && id != null) {
            return groupRepository.findById(id).map(group -> {
            	group.setName(name);
            	groupRepository.save(group);
                return ResponseEntity.ok("Группа обновлена: " + group.getName());
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Группа не найдена"));
        } else {
            var newGroup = new Group();
            newGroup.setName(name);
            groupRepository.save(newGroup);
            return ResponseEntity.status(HttpStatus.CREATED).body("Группа создана: " + newGroup.getName());
        }
    }
}