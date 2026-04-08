package com.base.basesetup.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chat")
public class ChatVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "chatsgen")
	@SequenceGenerator(name="chatsgen",sequenceName = "chatsseq",initialValue = 1,allocationSize = 1)
	@Column(name="chatid")
	private Long id;
	
	private Long userid;
	
	private String message;
	
	private LocalDateTime chatat=LocalDateTime.now();
	
	@Column(name="chatto")
	private Long chatTo;

}
