package com.fire.poc.service;

import java.util.List;
import java.util.Optional;

import com.fire.poc.entity.Person;

public interface IPersonService {
	public List<Person> getAllPersons();
	public Optional<Person> getPersonById(Long id);
	public Person savePerson(Person person);
	public void removePerson(Long id);
}
