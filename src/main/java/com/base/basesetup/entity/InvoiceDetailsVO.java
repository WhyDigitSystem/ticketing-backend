package com.base.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="invoicedetails")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invoicedetailsgen")
	@SequenceGenerator(name = "invoicedetailsgen", sequenceName = "invoicedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "invoicedetailsid")
	private Long id;
	
	@Column(name="description")
	private String description;
	@Column(name="quantity")
	private int quantity;
	@Column(name="price",precision = 10,scale = 2)
	private double price;
	@Column(name="amount",precision = 10,scale = 2)
	private double amount; 
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "invoiceid")
	private InvoiceVO invoiceVO;

}
