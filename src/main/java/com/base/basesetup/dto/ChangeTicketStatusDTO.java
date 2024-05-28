package com.base.basesetup.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.PrePersist;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTicketStatusDTO {
	
	private Long id;
	
	private String empCode;
	
	private String status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss a")
	@Column(name="createdon",length = 25)
	private String createdon;
	
	public void onSave() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		this.createdon = dateFormat.format(currentDate);
	}

}
