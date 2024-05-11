package com.base.basesetup.dto;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDTO {


	private String createdBy;
	
	private String modifiedBy;
	
	private String employee;
	
	private String code;
	
	private String department;
	
	private Date doj;
		
}
