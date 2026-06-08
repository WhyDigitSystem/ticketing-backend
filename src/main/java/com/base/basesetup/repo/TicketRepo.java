package com.base.basesetup.repo;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.TicketVO;

public interface TicketRepo extends JpaRepository<TicketVO, Long> {

	@Query(value = "select a from TicketVO a where a.assignedTo=?1 and a.mflag=false")
	List<TicketVO> findNewTicketNotification(String empcode);

	@Query(value = "select a from TicketVO a where a.email=?1")
	List<TicketVO> getAllTicketByAssignedTo(String empCode);

	@Query(value = "select a from TicketVO a where a.assignedTo=?1")
	List<TicketVO> getAllTicketByEmployee(String empCode);

	@Modifying
	@Transactional
	@Query("UPDATE TicketVO t SET t.modifiedBy=?1, t.mflag = true WHERE t.assignedTo = ?1 and t.mflag=false")
	void updateMflagByAssignedTo(String empCode);

	@Query(value = "select a from TicketVO a where a.client=?1")
	List<TicketVO> getAllTicketByClient(String empCode);

	@Query(nativeQuery = true, value = "select * from ticketstatus where client=?1")
	List<Object[]> getTicketStatusByClient(String customer);

	@Query(nativeQuery = true, value = "select a.assignedto,a.assignedto_employee,sum(case when status='Completed' then a.total else 0 end) Completed,\r\n"
			+ "sum(case when status='Inprogress' then a.total else 0 end) Inprogress from(\r\n"
			+ "select assignedto,assignedto_employee,status,count(*)total from ticket group by assignedto,assignedto_employee,status)a group by a.assignedto,a.assignedto_employee")
	Set<Object[]> getEmployeeTicketStatusCounts();

	@Query(nativeQuery = true, value = "select status, count(status)count from ticket group by status")
	Set<Object[]> getStatusCountDetails();

	@Query(nativeQuery = true, value = "select sum(high) high,sum(normal)normal,sum(mediums)mediums,sum(totalcount)totalcount,sum(highper)highper,sum(normalper)normalper,sum(mediumper) mediumper from (\r\n"
			+ "   select count(*) as high,0 normal,0 mediums,0 totalcount,0 highper,0 normalper,0 mediumper from ticket where  upper(priority)='High' and (assignedto=?1 or 'ALL'=?1)\r\n"
			+ "   union \r\n"
			+ "   select 0 high,count(*) normal,0 mediums,0 totalcount ,0 highper,0 normalper,0 mediumper from ticket where  upper(priority)='Normal' and (assignedto=?1 or 'ALL'=?1)\r\n"
			+ "   union \r\n"
			+ "   select 0 high,0 normal,count(*) mediums,0 totalcount ,0 highper,0 normalper,0 mediumper from ticket where  upper(priority)='Medium' and (assignedto=?1 or 'ALL'=?1)\r\n"
			+ "   union\r\n"
			+ "   select 0 high,0 normal,0 mediums,count(*) totalcount ,0 highper,0 normalper,0 mediumper from ticket where (assignedto=?1 or 'ALL'=?1)\r\n"
			+ "   union\r\n"
			+ "   select 0 high,0 normal,0 mediums,0 totalcount ,\r\n"
			+ "    round(sum(case when upper(priority) = 'high' then  1 else  0 end) * 100.0 / count(*),2) as  \r\n"
			+ "   highper,0 normalper,0 mediumper from ticket where (assignedto=?1 or 'ALL'=?1)\r\n"
			+ "   union \r\n"
			+ "   select 0 high,0 normal,0 mediums,0 totalcount , 0 highper,\r\n"
			+ "   round(sum(case when upper(priority) = 'Normal' then  1 else  0 end) * 100.0 / count(*),2) as \r\n"
			+ "   normalper,0 mediumper from ticket where (assignedto=?1 or 'ALL'=?1)\r\n"
			+ "   union \r\n"
			+ "   select 0 high,0 normal,0 mediums,0 totalcount , 0 highper,0 normalper,\r\n"
			+ "   round(sum(case when upper(priority) = 'Medium' then  1 else  0 end) * 100.0 / count(*) ,2) as \r\n"
			+ "    mediumper from ticket where (assignedto=?1 or 'ALL'=?1)) as a")
	Set<Object[]> getPriorityStatusCountDetails(String assignedTo);

	TicketVO findBySourceId(Long sourceId);

	@Query(nativeQuery = true, value = "SELECT *\r\n" + "FROM ticket\r\n" + "WHERE ('ALL' = ?1 OR application =?1)\r\n"
			+ "  AND (?2 IS NULL\r\n" + "       OR DATE(createdon) >= ?2)\r\n" + "  AND (?3 IS NULL\r\n"
			+ "       OR DATE(createdon) <= ?3)\r\n" + "ORDER BY ticketid DESC")
	List<TicketVO> getTicketReports(String application, String fromDate, String toDate);

	@Query(nativeQuery = true, value = "select application from ticket ")
	Set<Object[]> getApplicationDetails();
	
	@Query(nativeQuery = true, value = "SELECT *\r\n"
			+ "FROM ticket\r\n"
			+ "WHERE (assignedto = ?1 OR 'ALL' = ?1)\r\n"
			+ "ORDER BY ticketid DESC\r\n"
			+ "LIMIT 10")
	List<TicketVO> getRecentTicket(String application);
	
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    assignedto,\r\n"
			+ "    COUNT(*) AS totalcount\r\n"
			+ "FROM ticket\r\n"
			+ "WHERE ('ALL' = ?1 OR assignedto = ?1)\r\n"
			+ "GROUP BY assignedto\r\n"
			+ "ORDER BY totalcount DESC")
	Set<Object[]> getRecentTopAssign(String application);

}
