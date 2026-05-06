package com.base.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private Long id;
	private String comment;
	private String commentName;
	private Long ticketId;
	private String sourceUserName;
	private String application;
	private Long sourceId;
	private Long sourceOrgId;
	private Long sourceTicketId;
	private Long orgId;
}
