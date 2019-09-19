package com.budget.spendings.services;

import com.budget.spendings.domain.User;
import com.budget.spendings.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements CRUDService<User> {

	private UserRepository userRepository;
	private PasswordEncoder encoder;

	@Autowired
	public UserService(PasswordEncoder encoder, UserRepository userRepository) {
		this.encoder = encoder;
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
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User save(User user) {
		if (user.getPassword() != null) {
			String encodedPassword = encoder.encode(user.getPassword());
			user.setEncryptedPassword(encodedPassword);
		}
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}