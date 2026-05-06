package com.base.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.ChatVO;

public interface ChatRepository extends JpaRepository<ChatVO, Long> {

	@Query(nativeQuery = true,value = "select *  from chat where userid=?1 or chatto=?1")
	List<ChatVO> findAllByUserid(Long userId);

	@Query(nativeQuery = true,value = "select *  from chat where userid=?1 or chatto=?2")
	List<ChatVO> findAllByUseridAndChatTo(Long userId, Long chatTouser);

}
