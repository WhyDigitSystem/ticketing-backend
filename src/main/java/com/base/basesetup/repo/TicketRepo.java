package com.base.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.TicketVO;

public interface TicketRepo extends JpaRepository<TicketVO, Long> {

	@Query(value = "select a from TicketVO a where a.assignedTo=?1 and a.mflag=false")
	List<TicketVO> findNewTicketNotification(String empcode);
}
