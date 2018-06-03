package com.example.reminder.services;

import java.util.List;

public interface CRUDService<T> {
	List<?> listAll();
	 
    T getById(Integer id);
 
    T save(T domainObject);
 
    void delete(Integer id);
}
