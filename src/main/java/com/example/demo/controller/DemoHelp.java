package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class DemoHelp {

	private static final Logger logger = LoggerFactory.getLogger(DemoHelp.class);

	private static final ObjectMapper mapper = new ObjectMapper();
	private static int inc = 0;

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

}
