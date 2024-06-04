package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.InvoiceDetailsVO;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetailsVO, Long> {

	@Query(nativeQuery = true, value="select sum(a.amount) from invoicedetails a where a.invoiceid=?1")
	double findTotalAmount(Long id);

}
