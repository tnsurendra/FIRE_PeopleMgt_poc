package com.fire.poc.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fire.poc.entity.Person;
import com.fire.poc.entity.PersonHolder;
import com.fire.poc.exception.PersonNotFoundException;
import com.fire.poc.service.IPersonService;

/**
 * Person Controller class
 * Provides CRUD API
 *
 */
@RestController
@RequestMapping(path = "/person/v1")
public class PersonRest {
	private static final Logger logger = LoggerFactory.getLogger(PersonRest.class);
	@Autowired
	private IPersonService personService;

	/**
	 * Return All Persons Data
	 * @return
	 */
	@GetMapping("/allPersons")
	public PersonHolder retrieveAllPersons() {
		logger.info("retrieveAllPersons ");
		List<Person> persons = personService.getAllPersons();
		Collections.sort(persons);

		 logger.debug("retrieveAllPersons Data {}",persons);
		return new PersonHolder(persons);
	}

	/**
	 * Retrieves Specific Person Details based on ID 
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<Person> retrievePerson(@PathVariable Long id) {
		logger.debug("retrievePerson Data{}", id);
		Optional<Person> person = personService.getPersonById(id);
		 logger.debug("retrievePerson return Data {}",person);
		if (!person.isPresent())
			throw new PersonNotFoundException("Person not found with ID:" + id);
		
		 
		return new ResponseEntity<Person>(person.get(), new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Delete a Person by ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePerson(@PathVariable Long id) {
		 logger.debug("deletePerson Data{}",id);
		retrievePerson(id);
		personService.removePerson(id);
		return new ResponseEntity<String>("Person has been deleted", HttpStatus.OK);
	}

	/**
	 * Creates a Person
	 * 
	 * @param person
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<Person> createPerson(@RequestBody Person person) {
		 logger.debug("createPerson Data{}",person);
		
		return new ResponseEntity<Person>(personService.savePerson(person), HttpStatus.OK);

	}

	/**
	 * Updates Existing Person 
	 * 
	 * @param person
	 * @param id
	 * @return
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable Long id) {
		 logger.debug("updatePerson Data{}{}",person,id);
		Optional<Person> existingPerson = personService.getPersonById(id);

		if (!existingPerson.isPresent()) {
			throw new PersonNotFoundException("Person not found with ID:" + id);
		}
		person.setId(id);

		personService.savePerson(person);
		return new ResponseEntity<Person>(personService.savePerson(person), HttpStatus.OK);
	}
}
