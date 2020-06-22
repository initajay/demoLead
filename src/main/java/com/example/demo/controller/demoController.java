package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Lead;
import com.example.demo.entities.LeadRepository;
import com.example.demo.entities.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiParam;

@RestController
//@RequestMapping("/v1")
public class demoController {

	Logger logger = LoggerFactory.getLogger(demoController.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	LeadRepository repository;
	@Autowired
	DemoHelp demoHelp;

	@GetMapping(value = "/api/leads/{lead_id}")
	public ResponseEntity<?> getLeads(@ApiParam(value = "lead_id", required = true) @PathVariable long lead_id)
			throws JsonProcessingException {
		ObjectNode output = mapper.createObjectNode();
		if (repository.isLeadExist(lead_id)) {
			output = (ObjectNode) new ObjectMapper().readTree(mapper.writeValueAsString(repository.findById(lead_id)));
		} else {
			return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(output, HttpStatus.OK);

	}

	/*
	 * @GetMapping(value = "/api/leads") public ResponseEntity<?> getAllLeads() {
	 * ObjectNode output = mapper.createObjectNode(); //
	 * repository.findAll().forEach(student -> students.add(student));
	 * 
	 * return new ResponseEntity<>(output, HttpStatus.OK);
	 * 
	 * }
	 */
	@PostMapping(value = "/api/leads", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addLeads(@RequestBody ObjectNode inputBody) {
		ObjectNode output = mapper.createObjectNode();

		try {
			if (!demoHelp.isRequiredFieldAvalable(inputBody) || (!demoHelp.isCorrectData(inputBody))) {
				output.put("Status", "failure");
				output.put("reason", "input body do not have correct data");
				return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
			}

			Lead prd = mapper.convertValue(inputBody, Lead.class);

			if (!inputBody.has("id")) {
				prd.setId(demoHelp.getId());
			}
			if (prd.getStatus() == null) {
				Enum<?> status = Enum.valueOf(Status.class, "Created");
				prd.setStatus((Status) status);
			}
			if (prd.getCommunication() == null) {
				prd.setCommunication("");
			}
			repository.insert(prd);
			return new ResponseEntity<>(output, HttpStatus.CREATED);
		} catch (Exception ex) {
			output.put("Error", ex.getMessage());
			return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/leads"
			+ "/{lead_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLeads(@RequestBody ObjectNode inputBody,
			@ApiParam(value = "lead_id", required = true) @PathVariable long lead_id) {
		ObjectNode output = mapper.createObjectNode();
		repository.findById(lead_id);

		return new ResponseEntity<>(output, HttpStatus.ACCEPTED);

	}

	@DeleteMapping(value = "/api/leads/{lead_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteLeads(@ApiParam(value = "lead_id", required = true) @PathVariable String lead_id) {
		ObjectNode output = mapper.createObjectNode();

		return new ResponseEntity<>(output, HttpStatus.OK);

	}

	@PutMapping(value = " /api/mark_lead/{lead_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> markLeads(@RequestBody ObjectNode inputBody,
			@ApiParam(value = "lead_id", required = true) @PathVariable String lead_id) {
		ObjectNode output = mapper.createObjectNode();

		// Lead ld = new Lead();

		return new ResponseEntity<>(output, HttpStatus.ACCEPTED);

	}

}
