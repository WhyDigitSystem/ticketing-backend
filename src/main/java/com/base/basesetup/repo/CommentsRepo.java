package com.base.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.CommentsVO;

public interface CommentsRepo extends JpaRepository<CommentsVO, Long> {
	@Query(nativeQuery = true, value = "select * from comments where ticketid=?1 order by createdon asc")
	List<CommentsVO> findCommentsByTicketId(Long ticketId);

}
