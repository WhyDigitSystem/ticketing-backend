package com.base.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.TicketCommentImageVO;

public interface TicketCommentImageRepo  extends JpaRepository<TicketCommentImageVO, Long>{

	@Query(nativeQuery = true,value = "select a.* from ticketcommentimage a where a.comment_id=?1")
	List<TicketCommentImageVO> findCommentImageByComentId(Long commentId);

}
