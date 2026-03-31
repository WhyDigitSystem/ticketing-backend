package com.base.basesetup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="invoice")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invoicegen")
	@SequenceGenerator(name = "invoicegen", sequenceName = "invoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "invoiceid")
	private Long id;
	
	@Column(name="invoiceno")
	private String invoiceNo;
	@Column(name="invoiceto")
	private String invoiceTo;
	@Column(name="address")
	private String address;
	@Column(name="total",precision = 10,scale = 2)
	private double total; 
	
	@OneToMany(mappedBy = "invoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InvoiceDetailsVO>invoiceDetailsVO;

}
