package com.base.basesetup.entity;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.base.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ticketgen")
	@SequenceGenerator(name="ticketgen",sequenceName = "ticketseq",initialValue = 1,allocationSize = 1)
	@Column(name="ticketid")
	private Long id;
	
	private boolean cancel;
	
	@Column(name="createdby")
	private String createdBy;
	
	
	@Column(name="modifiedby")
	private String modifiedBy;
	
	
	private String client;
	
	@Column(name="docid")
	private String docId;
	
	@Column(name="docdate")
	private LocalDate docDate = LocalDate.now();
	
	private String title;
	
	private String description;
	
	@Column(name="fileupload")
	private String fileUpload;
	
	private String priority;
	
	private String status;
	
	@Column(name="assignedto")
	private String assignedTo;
	
	@Column(name="assignedtoEmployee")
	private String assignedToEmp;
	
	@Column(name="assigneddate")
	private Date assignedDate;
	
	@Column(name="completedby")
	private String completedBy;
	
	@Column(name="completedon")
	private Date completedOn;
	
	private boolean mflag;
	
	private String email;
	
	@Lob
    @Column(name = "imagedata", columnDefinition="LONGBLOB")
    private byte[] imageData;

//	@OneToMany(mappedBy = "ticketVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	List<CommentsVO> commentsVO;
	
	@Embedded
    private CreatedUpdatedDate commondate = new CreatedUpdatedDate();
	
}
