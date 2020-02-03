package com.fire.poc.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fire.poc.entity.Person;
import com.fire.poc.repo.PersonRepository;
import com.fire.poc.service.PersonServiceImpl;

public class TestPersonService {

	@InjectMocks
	PersonServiceImpl service;

	@Mock
	PersonRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllPersonsTest() {
		List<Person> list = new ArrayList<Person>();
		Person p = new Person();
		p.setFirst_name("John");
		p.setLast_name("Smith");
		p.setAge(20);
		p.setFavourite_colour("Green");
		String hobbies[] = { "Chess", "Reading Books" };

		p.setHobby(Arrays.asList(hobbies));
		list.add(p);
		Person pTwo = new Person();
		pTwo.setFirst_name("Sarah");
		pTwo.setLast_name("Williams");
		pTwo.setAge(27);
		pTwo.setFavourite_colour("Yellow");
		String pTwoHobbies[] = { "Playing Music", "Cooking" };

		pTwo.setHobby(Arrays.asList(pTwoHobbies));
		list.add(pTwo);

		when(repo.findAll()).thenReturn(list);

		// test
		List<Person> perList = service.getAllPersons();

		assertEquals(2, perList.size());
		verify(repo, times(1)).findAll();
	}

	@AfterAll
	public void getPersonByIdTest() {
		Person p = new Person();
		p.setFirst_name("John");
		p.setLast_name("Smith");
		p.setAge(20);
		p.setFavourite_colour("Green");
		String hobbies[] = { "Chess", "Reading Books" };
		when(repo.findById(1L).get()).thenReturn(p);

		Person per = service.getPersonById(1L).get();

		assertEquals("John", per.getFirst_name());
		assertEquals("Smith", per.getLast_name());
		assertEquals("20", per.getAge());
	}
	



	@Test
	public void updatePersonTest() {
		Person p = new Person();
		p.setId(1L);
		p.setFirst_name("John");
		p.setLast_name("Smith");
		p.setAge(20);
		p.setFavourite_colour("Purple");//Updating field
		String hobbies[] = { "Chess", "Reading Books" };
		when(repo.save(p)).thenReturn(p);

		Person per = service.savePerson(p);

		assertEquals("John", per.getFirst_name());
		assertEquals("Smith", per.getLast_name());
		assertEquals("Purple", per.getFavourite_colour());
	}
	
	@Test
	public void createPersonTest() {
		Person p = new Person();
		p.setFirst_name("Liam");
		p.setLast_name("Clark");
		p.setAge(40);
		p.setFavourite_colour("Blue");
		String hobbies[] = { "Stamp Collection", "Writing Books" };

		service.savePerson(p);

		verify(repo, times(1)).save(p);
	}

}