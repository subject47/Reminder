package com.example.reminder.services;

import java.util.List;

public abstract class CRUDService<T> {
	public abstract List<T> listAll();

  public abstract T getById(Integer id);

  public abstract T save(T domainObject);

  public abstract void delete(Integer id);
}
