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

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private EncryptionService encryptionService;

	@Autowired
	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	@Override
	public List<User> listAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add); // fun with Java 8
		return users;
	}

	@Override
	public User getById(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public User save(User user) {
		if (user.getPassword() != null) {
		//	user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
			user.setEncryptedPassword(user.getPassword());
		}
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		userRepository.delete(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}