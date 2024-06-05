package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.basesetup.entity.InvoiceVO;

public interface InvoiceRepository extends JpaRepository<InvoiceVO, Long> {

}
