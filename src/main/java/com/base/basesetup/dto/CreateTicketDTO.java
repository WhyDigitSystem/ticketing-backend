package com.base.basesetup.dto;

import javax.persistence.Column;

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

	private Long sourceId;

	private String customer;

	private Long sourceOrgId;

	private String sourceBranch;

	private String sourceBranchCode;

	private String projectName;

	private String application;

	private String sourceEmail;
	
	private String ticketStatus;
	
	private String sourceDocId;

}
