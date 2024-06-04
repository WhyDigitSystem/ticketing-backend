package com.base.basesetup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.basesetup.dto.InvoiceDTO;
import com.base.basesetup.entity.InvoiceVO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.exception.ApplicationException;

@Service
public interface InvoiceService {
	
	InvoiceVO createInvoiceVO(InvoiceDTO invoiceDTO);
	
	List<InvoiceVO>getAllInvoiceVO();
	
	InvoiceVO getInvoiceById(Long Id);
	
	InvoiceVO updateInvoice(InvoiceDTO invoiceDTO) throws ApplicationException;
	
	void deleteInvoiceDetails(Long id);
	
	

}
