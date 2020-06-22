package com.example.demo.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.controller.DemoHelp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class Scheduler {
	private Logger logger = LoggerFactory.getLogger(Scheduler.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private DemoHelp demoHelp;
	
	@PreDestroy
	public void cleanup() {
		// here  you all cleanup task should come like
		//db close, commit , clearing of all map etc
		
	}



	
	@PostConstruct
	public void collect() {
		/*
		 * try{ ObjectNode objNode = mapper.createObjectNode(); JsonNode inp=
		 * demoHelp.readInput(); int val1 = inp.get("val1").asInt(); int val2 =
		 * inp.get("val2").asInt(); objNode.put("output of sum is ", val1+val2);
		 * demoHelp.writeFile(objNode); }catch(Exception e) {
		 * logger.error("Error in processing"); }
		 */
		
	}

}