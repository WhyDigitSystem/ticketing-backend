package com.base.basesetup.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.base.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketgen")
	@SequenceGenerator(name = "ticketgen", sequenceName = "ticketseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "ticketid")
	private Long id;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;

	@Column(name = "client")
	private String client;

	@Column(name = "docid")
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "fileupload")
	private String fileUpload;

	@Column(name = "priority")
	private String priority;

	@Column(name = "status")
	private String status;

	@Column(name = "assignedto")
	private String assignedTo;

	@Column(name = "sourceid")
	private Long sourceId;

	@Column(name = "assignedtoEmployee")
	private String assignedToEmp;

	@Column(name = "assigneddate")
	private LocalDate assignedDate;

	@Column(name = "completedby")
	private String completedBy;

	@Column(name = "completedon")
	private Date completedOn;

	private boolean mflag;

	@Column(name = "email")
	private String email;
	
	@Column(name = "sourceemail")
	private String sourceEmail;

	@Column(name = "customer")
	private String customer;

	@Column(name = "sourceorgid")
	private Long sourceOrgId;

	@Column(name = "sourcebranch")
	private String sourceBranch;

	@Column(name = "sourcebranchcode")
	private String sourceBranchCode;

	@Column(name = "projectname")
	private String projectName;

	@Column(name = "application")
	private String application;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "ticketstatus")
	private String ticketStatus;

	@Lob
	@Column(name = "imagedata", columnDefinition = "LONGBLOB")
	private byte[] imageData;

//	@OneToMany(mappedBy = "ticketVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	List<CommentsVO> commentsVO;

	@Embedded
	private CreatedUpdatedDate commondate = new CreatedUpdatedDate();

}
