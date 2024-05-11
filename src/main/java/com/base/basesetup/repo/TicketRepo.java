package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.basesetup.entity.TicketVO;

public interface TicketRepo extends JpaRepository<TicketVO, Long> {

}
