package com.base.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.TicketCommentImageVO;

public interface TicketCommentImageRepo  extends JpaRepository<TicketCommentImageVO, Long>{

	@Query("select a from TicketCommentImageVO a where a.commentId=?1")
	List<TicketCommentImageVO> findCommentImageByComentId(Long commentId);

}
