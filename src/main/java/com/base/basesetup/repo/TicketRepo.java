package com.base.basesetup.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.CommentsVO;
import com.base.basesetup.entity.TicketVO;

public interface TicketRepo extends JpaRepository<TicketVO, Long> {

	@Query(value = "select a from TicketVO a where a.assignedTo=?1 and a.mflag=false")
	List<TicketVO> findNewTicketNotification(String empcode);

	@Query(value = "select a from TicketVO a where a.assignedTo=?1")
	List<TicketVO> getAllTicketByAssignedTo(String empCode);

	@Query(value = "select a from TicketVO a where a.assignedTo=?1")
	List<TicketVO> getAllTicketByEmployee(String empCode);

	@Modifying
    @Transactional
    @Query("UPDATE TicketVO t SET t.modifiedBy=?1, t.mflag = true WHERE t.assignedTo = ?1 and t.mflag=false")
   	void updateMflagByAssignedTo(String empCode);

	@Query(value = "select a from TicketVO a where a.client=?1")
	List<TicketVO> getAllTicketByClient(String empCode);

	@Query(nativeQuery = true, value ="select * from clientTicketStatus where client=?1")
	List<Object[]> getTicketStatusByClient(String customer);

	
}
