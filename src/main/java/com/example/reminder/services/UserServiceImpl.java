package com.example.reminder.services;

import com.example.reminder.domain.User;
import com.example.reminder.repositories.UserRepository;
import com.example.reminder.services.security.EncryptionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private EncryptionService encryptionService;

	@Autowired
	public void setEncryptionService(EncryptionService encryptionService, UserRepository userRepository) {
		this.encryptionService = encryptionService;
		this.userRepository = userRepository;
	}

	@Override
	public List<User> listAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public User getById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User save(User user) {
		if (user.getPassword() != null) {
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
		}
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}