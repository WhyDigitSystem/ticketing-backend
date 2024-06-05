package com.base.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketDTO {


	private String createdBy;
	
	private String modifiedBy;
	
	private String client;
	
	private String title;
	
	private String description;
	
	private String priority;
	
	private String email;
		
}
