package com.example.reminder.domain;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role extends AbstractDomainClass {

	public static final String USER = "USER";
	public static final String ADMIN = "ADMIN";

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	// ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name =
	// "role_id"),
	// inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		if (!this.users.contains(user)) {
			this.users.add(user);
		}

		if (!user.getRoles().contains(this)) {
			user.getRoles().add(this);
		}
	}

	public void removeUser(User user) {
		this.users.remove(user);
		user.getRoles().remove(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		if (o == this) {
			return true;
		}
		Role other = (Role) o;
		return Objects.equals(name, other.getName());
	}

}
