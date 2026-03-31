package com.base.basesetup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.basesetup.dto.ChatDTO;
import com.base.basesetup.entity.ChatVO;
import com.base.basesetup.repo.ChatRepository;

@Service
public class ChatServiceImpl implements ChatServices {

	@Autowired
	ChatRepository chatRepository;
	
	@Override
	public ChatVO createChatVO(ChatDTO chatDTO) {
		
		ChatVO chatVO= new ChatVO();
		chatVO.setUserid(chatDTO.getUserid());
		chatVO.setMessage(chatDTO.getMessage());
		chatVO.setChatTo(chatDTO.getChatToUser());
		return chatRepository.save(chatVO);
	}

	@Override
	public List<ChatVO> getAllChatByUser(Long userId) {
		
		return chatRepository.findAllByUserid(userId);
	}

	@Override
	public List<ChatVO> getAllChatByAdmin(Long userId, Long chatTouser) {
		
		return chatRepository.findAllByUseridAndChatTo(userId,chatTouser);
	}

}
