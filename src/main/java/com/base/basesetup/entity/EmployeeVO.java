package com.base.basesetup.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.base.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employeegen")
	@SequenceGenerator(name="ticketgen",sequenceName = "employeeseq",initialValue = 1,allocationSize = 1)
	@Column(name="employeeid")
	private Long id;
	
	private boolean cancel;
	
	@Column(name="createdby")
	private String createdBy;
	
	
	@Column(name="modifiedby")
	private String modifiedBy;
	
	
	private String client;
	
	@Column(name="employee")
	private String employee;
	
	@Column(name="code")
	private String code;
	
	private String gender;
	
	private String branch;
		
	private String department;
	
	private String designation;
	
	private LocalDate dob;
	
	private String role="Employee";
	
	private String password;
	
	private boolean active;
	
	private Date doj;
	
	
	@Embedded
    private CreatedUpdatedDate commondate = new CreatedUpdatedDate();
	
}
