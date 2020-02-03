package com.fire.poc.test.controller;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fire.poc.FirePersonApp;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FirePersonApp.class)
@SpringBootTest
public class TestPersonRest {

	private MockMvc mockMvc;
	private static final String BASE_PATH="/person/v1/";
	
	@Autowired
    private WebApplicationContext wac;

	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void verifyAllPersons() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(TestPersonRest.BASE_PATH+"allPersons").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())	
		.andExpect(jsonPath("$.person", hasSize(2))).andDo(print());
	}
	
	@Test
	public void verifyPersonById() throws Exception {
		
		
		mockMvc.perform(MockMvcRequestBuilders.get(TestPersonRest.BASE_PATH+"get/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.first_name").exists())
		.andExpect(jsonPath("$.last_name").exists())
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.age").value(20))
		.andExpect(jsonPath("$.first_name").value("John"))
		.andDo(print());
		
	}
	
	@Test
	public void verifyInvalidPersonArgument() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(TestPersonRest.BASE_PATH+"/get/f").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError())			
			.andDo(print());
	}
	
	@Test
	public void verifyInvalidPersonId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(TestPersonRest.BASE_PATH+"/get/11").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message").value("Record Not Found"))
		.andExpect(jsonPath("$.details").value("Person not found with ID:11"))
		.andDo(print());
		
	}
	

	@Test
	public void verifyDeletePerson() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(TestPersonRest.BASE_PATH+"/delete/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string("Person has been deleted"))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidPersonIdToDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(TestPersonRest.BASE_PATH+"/delete/9").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message").value("Record Not Found"))
		.andExpect(jsonPath("$.details").value("Person not found with ID:9"))
		.andDo(print());
	}
	
	
	//@Test
	@AfterAll
	public void verifyCreatePerson() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(TestPersonRest.BASE_PATH+"/create/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" + 
        		     		"            \"first_name\": \"Robert\",\n" + 
        		"            \"last_name\": \"Joe\",\n" + 
        		"            \"age\": 30,\n" + 
        		"            \"favourite_colour\": \"Orange\",\n" + 
        		"            \"hobby\": [\n" + 
        		"                \"Chess\",\n" + 
        		"                \"Reading Books\"\n" + 
        		"            ]\n" + 
        		"        }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.first_name").exists())
		.andExpect(jsonPath("$.last_name").exists())
		.andExpect(jsonPath("$.age").value(30))
		.andExpect(jsonPath("$.favourite_colour").value("Orange"))
		.andDo(print());
	}
	
	@Test
	public void verifyMalformedCreatePerson() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(TestPersonRest.BASE_PATH+"/create/1123")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" + 
        		"\"first_name\":\"Liam\",\n" + 
        		"\"last_name\":\"Clark\",\n" + 
        		"\"age\":28,\n" + 
        		"\"favourite_colour\":\"Yellow\",\n" + 
        		"\"hobby\":[\"cricket\"]\n" + 
        		"}")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is4xxClientError())		
		.andDo(print());
	}
	
	@Test
	public void verifyUpdatePerson() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(TestPersonRest.BASE_PATH+"/update/2")
        .contentType(MediaType.APPLICATION_JSON)
        .content(		"{\n" + 
        		"            \"id\": 2,\n" + 
        		"            \"first_name\": \"Sarah\",\n" + 
        		"            \"last_name\": \"Williams\",\n" + 
        		"            \"age\": 27,\n" + 
        		"            \"favourite_colour\": \"Pink\",\n" + 
        		"            \"hobby\": [\n" + 
        		"                \"Playing Music\",\n" + 
        		"                \"Cooking\"\n" + 
        		"            ]\n" + 
        		"        }")
        .accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.first_name").exists())
		.andExpect(jsonPath("$.age").exists())
		.andExpect(jsonPath("$.favourite_colour").value("Pink"))
		.andExpect(jsonPath("$.first_name").value("Sarah"))
		.andExpect(jsonPath("$.age").value(27))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidPersonUpdate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(TestPersonRest.BASE_PATH+"/update/1122")
        .contentType(MediaType.APPLICATION_JSON)
        .content(		"{\n" + 
        		"            \"id\": 1122,\n" + 
        		"            \"first_name\": \"Sarah\",\n" + 
        		"            \"last_name\": \"Williams\",\n" + 
        		"            \"age\": 27,\n" + 
        		"            \"favourite_colour\": \"Pink\",\n" + 
        		"            \"hobby\": [\n" + 
        		"                \"Playing Music\",\n" + 
        		"                \"Cooking\"\n" + 
        		"            ]\n" + 
        		"        }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message").value("Record Not Found"))
		.andExpect(jsonPath("$.details").value("Person not found with ID:1122"))
		
		.andDo(print());
	}

}