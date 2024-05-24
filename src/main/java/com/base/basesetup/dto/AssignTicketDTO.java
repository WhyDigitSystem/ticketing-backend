package com.base.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTicketDTO {
	
	private Long id;
		
	private String modifiedBy;
		
	private String status;
	
	private String assignedTo;

	private String AssignedToEmployee;

}
