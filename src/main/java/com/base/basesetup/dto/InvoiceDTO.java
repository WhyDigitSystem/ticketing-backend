package com.base.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
	
	private Long id;
	private String invoiceNo;
	private String invoiceTo;
	private String address;
	private List<InvoiceDetailsDTO>invoiceDetailsDTO;

}
