package com.example.demo.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LeadRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class LeadRowMapper implements RowMapper<Lead> {
		@Override
		public Lead mapRow(ResultSet rs, int rowNum) throws SQLException {
			Lead lead = new Lead();
			lead.setId(rs.getLong("id"));
			lead.setFirst_name(rs.getString("first_name"));
			lead.setLast_name(rs.getString("last_name"));
			lead.setMobile(rs.getString("mobile"));
			lead.setEmail(rs.getString("email"));
			Enum<?> location_type = Enum.valueOf(Location_type.class, rs.getString("Location_type"));
			lead.setLocation_type((Location_type) location_type);
			lead.setLocation_string(rs.getString("location_string"));
			Enum<?> status = Enum.valueOf(Status.class, rs.getString("Status"));
			lead.setStatus((Status) status);
			lead.setCommunication(rs.getString("communication"));
			return lead;
		}
	}

	public List<Lead> findAll() {
		return jdbcTemplate.query("select * from lead", new LeadRowMapper());
	}

	public Lead findById(long id) {
		return jdbcTemplate.queryForObject("select * from lead where id=?", new Object[] { id },
				new BeanPropertyRowMapper<Lead>(Lead.class));
	}

	public boolean isLeadExist(long id) {
		String sql = "select count(*) from lead where id=?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public boolean isEmailExist(String email,long id) {
		String sql = "select count(*) from lead where id!=? and email=?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { id,email }, Integer.class);

		if (count == 1) {
			return true;
		}
		return false;
	}
	
	public boolean isMobileExist(String mobile,long id) {
		String sql = "select count(*) from lead where id!=? and mobile=?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { id,mobile }, Integer.class);

		if (count == 1) {
			return true;
		}
		return false;
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from lead where id=?", new Object[] { id });
	}

	public int insert(Lead lead) {
		return jdbcTemplate.update(
				"insert into lead (id, first_name,last_name, mobile,email,Location_type,location_string,Status,communication) "
						+ "values(?,?,?, ?,?,?,?, ?, ?)",
				new Object[] { lead.getId(), lead.getFirst_name(), lead.getLast_name(), lead.getMobile(),
						lead.getEmail(), lead.getLocation_type().toString(), lead.getLocation_string(),
						lead.getStatus().toString(), lead.getCommunication() });
	}

	public int update(Lead lead) {
		return jdbcTemplate.update("update lead "
				+ " set first_name = ?, last_name = ?, mobile = ?, email = ?, Location_type = ?, location_string = ?, Status = ?, communication = ? "
				+ " where id = ?",
				new Object[] { lead.getFirst_name(), lead.getLast_name(), lead.getMobile(), lead.getEmail(),
						lead.getLocation_type().toString(), lead.getLocation_string(), lead.getStatus().toString(),
						lead.getCommunication(), lead.getId() });
	}
}