package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lead {
	@Id
	private long id;
	private String first_name;
	private String last_name;
	private Long mobile;
	private String email;
	
    @Enumerated(EnumType.STRING)
	private Location_type location_type; 
    
	private String location_string;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String communication;
	
	/*
	 * public location_type getlocation_type() { return locationTypeStr; }
	 * 
	 * 
	 * public void setlocation_type(location_type lctp) {
	 * this.setLocationTypeStr(lctp); }
	 * 
	 * public status getStatus() { return sts; }
	 * 
	 * 
	 * public void setStatus(status sts) { this.setStatus(sts); }
	 */
}
