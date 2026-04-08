package com.base.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
	
	private Long userid;
	
	private String message;
	
	private Long chatToUser;

}
