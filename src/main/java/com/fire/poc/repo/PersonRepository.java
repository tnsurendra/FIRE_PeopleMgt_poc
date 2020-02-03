package com.fire.poc.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fire.poc.entity.Person;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long>{

}
