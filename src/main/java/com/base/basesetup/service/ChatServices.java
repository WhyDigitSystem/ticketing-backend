package com.base.basesetup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.basesetup.dto.ChatDTO;
import com.base.basesetup.entity.ChatVO;

@Service
public interface ChatServices {
	
	
	ChatVO createChatVO(ChatDTO chatDTO);
	
	List<ChatVO>getAllChatByUser(Long userId);
	
	List<ChatVO>getAllChatByAdmin(Long userId,Long chatTouser);

}
