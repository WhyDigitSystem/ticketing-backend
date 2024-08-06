package com.base.basesetup.repo;

import java.util.List;
import java.util.Set;

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

	@Query(nativeQuery = true, value ="select * from ticketstatus where client=?1")
	List<Object[]> getTicketStatusByClient(String customer);

	@Query(nativeQuery = true,value="select a.assignedto,a.assignedto_employee,sum(case when status='Completed' then a.total else 0 end) Completed,\r\n"
			+ "sum(case when status='Inprogress' then a.total else 0 end) Inprogress from(\r\n"
			+ "select assignedto,assignedto_employee,status,count(*)total from ticket group by assignedto,assignedto_employee,status)a group by a.assignedto,a.assignedto_employee")
	Set<Object[]> getEmployeeTicketStatusCounts();

	@Query(nativeQuery = true, value ="select status, count(status)count from ticket group by status")
	Set<Object[]> getStatusCountDetails();

	@Query(nativeQuery = true, value ="SELECT \r\n"
			+ "    high,\r\n"
			+ "    normal,\r\n"
			+ "    medium,\r\n"
			+ "    (high + normal + medium) AS total\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        SUM(CASE WHEN t.priority = 'High' THEN 1 ELSE 0 END) AS high,\r\n"
			+ "        SUM(CASE WHEN t.priority = 'Normal' THEN 1 ELSE 0 END) AS normal,\r\n"
			+ "        SUM(CASE WHEN t.priority = 'Medium' THEN 1 ELSE 0 END) AS medium\r\n"
			+ "    FROM \r\n"
			+ "        ticket t\r\n"
			+ "    WHERE \r\n"
			+ "        t.status = 'Inprogress'\r\n"
			+ ") AS subquery")
	Set<Object[]> getPriorityStatusCountDetails();

	
}
