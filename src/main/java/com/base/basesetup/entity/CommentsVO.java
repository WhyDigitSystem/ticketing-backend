package com.base.basesetup.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
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
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentgen")
	@SequenceGenerator(name = "commentgen", sequenceName = "commentseq", initialValue = 1, allocationSize = 1)
	@Column(name = "commentid")
	private Long id;
	@Column(name = "comment")
	private String comment;
	@Column(name = "commentname")
	private String commentName;
	@Column(name = "commenttime")
	private LocalDateTime commentsTime= LocalDateTime.now();
	@Column(name = "ticketid")
	private Long ticketId;

//	@ManyToOne
//	@JsonBackReference
//	@JoinColumn(name = "ticketid")
//	TicketVO ticketVO;

	@Embedded
	private CreatedUpdatedDate commondate = new CreatedUpdatedDate();

}
