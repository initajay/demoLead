package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Lead;
import com.example.demo.entities.LeadRepository;
import com.example.demo.entities.Location_type;
import com.example.demo.entities.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class DemoHelp {

	private static final Logger logger = LoggerFactory.getLogger(DemoHelp.class);

	private static final ObjectMapper mapper = new ObjectMapper();
	private static int inc = 0;
	
	@Autowired
	LeadRepository repository;

	public JsonNode readInput() {

		JsonNode root = null;
		try {
			// Read JSON Schema into config object
			File configFile = Paths.get("input.json").toFile();

			// first check for current directory
			if (configFile.exists()) {

				root = mapper.readTree(configFile);

			}
		} catch (Exception e) {
			logger.error("can't find the input file ", e);
		}

		return root;
	}

	public void writeFile(ObjectNode body) throws JsonProcessingException, IOException {
		String fileName = "output.json";
		File file = new File(fileName);
		FileUtils.write(file, mapper.writeValueAsString(body));
	}

	/*
	 * to get the unique id to insert into database
	 */
	public long getId() {

		long id = Long
				.parseLong(String.valueOf(System.currentTimeMillis()).substring(1, 10).concat(String.valueOf(inc)));
		inc = (inc + 1) % 10;
		return id;
	}

	
	public boolean isRequiredFieldAvalable(ObjectNode body) {
		if (body.has("first_name") && body.has("last_name") && body.has("mobile") && body.has("email")
				&& body.has("location_type") && body.has("location_string")) {
          return true;
		}
		return false;
	}
	
	public boolean isCorrectData(ObjectNode body) {
		List<Lead> leads = repository.findAll();
		
		if(body.get("mobile").asText().length() != 10) {
			return false;
		}
		
		for (Lead lead : leads) {
			if(lead.getEmail().equals( body.get("email").asText()) || lead.getMobile().equals(body.get("mobile").asLong()) ) {
				return false;
			}
			
		}
		return true;
	}
	
	public boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	public Lead updateLead(Lead ld,ObjectNode inputbody) {
		if(inputbody.has("first_name")) {
			ld.setFirst_name(inputbody.get("first_name").asText() );
		}
		if(inputbody.has("last_name")) {
			ld.setLast_name(inputbody.get("last_name").asText() );
		}
		if(inputbody.has("mobile")) {
			ld.setMobile(inputbody.get("mobile").asLong() );
		}
		if(inputbody.has("email")) {
			ld.setEmail(inputbody.get("email").asText() );
		}
		if(inputbody.has("location_type")) {
			Enum<?> ltyp = Enum.valueOf(Location_type.class, inputbody.get("location_type").asText() );
			ld.setLocation_type((Location_type)ltyp);
		}
		if(inputbody.has("location_string")) {
			ld.setLocation_string(inputbody.get("location_string").asText() );
		}
		if(inputbody.has("status")) {
			Enum<?> status = Enum.valueOf(Status.class, inputbody.get("status").asText() );
			ld.setStatus((Status)status);
		}
		if(inputbody.has("communication")) {
			ld.setCommunication(inputbody.get("communication").asText() );
		}
		
		return ld;
	}

}
