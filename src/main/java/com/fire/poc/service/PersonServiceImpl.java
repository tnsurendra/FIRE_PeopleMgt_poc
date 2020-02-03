package com.fire.poc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fire.poc.entity.Person;
import com.fire.poc.repo.PersonRepository;

@Service("personService")
public class PersonServiceImpl implements IPersonService{

	@Autowired
	private PersonRepository personRepository;
	
	
	/**
	 *
	 *Retrieves All Persons
	 */
	@Override
	public List<Person> getAllPersons() {
	
		return personRepository.findAll();
	}

	/**
	 *Retrieves Person by ID
	 *
	 */
	@Override
	public Optional<Person> getPersonById(Long id) {
		
		return personRepository.findById(id);
	}

	/**
	 *Stores a Person
	 *
	 */
	@Override
	public Person savePerson(Person person) {
	
		return personRepository.save(person);
	}

	/**
	 *Deletes a Person
	 *
	 */
	@Override
	public void removePerson(Long id) {
		
		personRepository.deleteById(id);
	}

}
