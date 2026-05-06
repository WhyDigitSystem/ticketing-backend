package com.base.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticketcommentimage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCommentImageVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "commentimagegen")
	@SequenceGenerator(name="commentimagegen",sequenceName = "commentimageseq",initialValue = 1,allocationSize = 1)
	@Column(name="ticketcommentimageid")
	private Long id;
	
	@Lob
    @Column(name = "commentimage", columnDefinition="LONGBLOB")
    private byte[] commentImage;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "commentId")
    private CommentsVO commentsVO;
	

}
