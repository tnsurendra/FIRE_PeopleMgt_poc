package com.fire.poc;

import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.mapping.Collection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fire.poc.entity.Person;
import com.fire.poc.repo.PersonRepository;

@SpringBootApplication
public class FirePersonApp {

	public static void main(String[] args) {
		SpringApplication.run(FirePersonApp.class, args);
	}
	
	//Dummy Data to insert Inititally
	@Bean
	public CommandLineRunner setup(PersonRepository repo) {
		return (args) -> {
			Person p=new Person();
			p.setFirst_name("John");
			p.setLast_name("Smith");
			p.setAge(20);
			p.setFavourite_colour("Green");
			String hobbies[]={"Chess","Reading Books"};
			
			p.setHobby(Arrays.asList(hobbies) );
			repo.save(p);
			Person pTwo=new Person();
			pTwo.setFirst_name("Sarah");
			pTwo.setLast_name("Williams");
			pTwo.setAge(27);
			pTwo.setFavourite_colour("Yellow");
			String pTwoHobbies[]={"Playing Music","Cooking"};
			
			pTwo.setHobby(Arrays.asList(pTwoHobbies) );
			repo.save(pTwo);	
			
		};
	}

}
