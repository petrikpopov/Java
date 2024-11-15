package com.sunmeat.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

	@Autowired
	private GroupRepo groupRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public Group findById(Long id) {
		return groupRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Группа с ID " + id + " не найдена"));
	}

	@Transactional(readOnly = true)
	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	@Transactional
	public void saveGroup(String name) {
		Group newGroup = new Group();
		newGroup.setName(name);
		groupRepository.save(newGroup);
	}

	@Transactional
	public void updateGroup(Long id, String name) {
		Group group = findById(id);
		group.setName(name);
		groupRepository.save(group);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteGroup(Long id) {
		if (!groupRepository.existsById(id)) {
			throw new EntityNotFoundException("Группа с ID " + id + " не найдена");
		}
		groupRepository.deleteById(id);
	}
}
