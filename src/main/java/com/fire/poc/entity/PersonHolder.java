package com.fire.poc.entity;

import java.util.List;

public class PersonHolder {
	private List<Person> person;
	public PersonHolder(List<Person> person) {
		this.person=person;
	}
	public List<Person> getPerson() {
		return person;
	}
	public void setPerson(List<Person> person) {
		this.person = person;
	}

	
}
