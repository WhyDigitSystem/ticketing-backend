package com.base.basesetup.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDTO {


	private Long id;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private String employee;
	
	private String code;
	
	private String department;
	
	private Date doj;
	
	private String gender;
	
	private String branch;
	
	private String designation;
	
	private LocalDate dob;
	
	private String password;
	
	private boolean active;
	
	private String email;
	
		
}
