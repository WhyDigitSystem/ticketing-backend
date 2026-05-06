package com.base.basesetup.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.SUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.basesetup.dto.InvoiceDTO;
import com.base.basesetup.dto.InvoiceDetailsDTO;
import com.base.basesetup.entity.InvoiceDetailsVO;
import com.base.basesetup.entity.InvoiceVO;
import com.base.basesetup.exception.ApplicationException;
import com.base.basesetup.repo.InvoiceDetailsRepository;
import com.base.basesetup.repo.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	InvoiceDetailsRepository invoiceDetailsRepository;

	@Override
	public InvoiceVO createInvoiceVO(InvoiceDTO invoiceDTO) {
		InvoiceVO invoiceVO=new InvoiceVO();
		invoiceVO.setInvoiceNo(invoiceDTO.getInvoiceNo());
		invoiceVO.setInvoiceTo(invoiceDTO.getInvoiceTo());
		invoiceVO.setAddress(invoiceDTO.getAddress());
		List<InvoiceDetailsVO> invoiceDetailsVO = new ArrayList<>();
		if (invoiceDTO.getInvoiceDetailsDTO() != null) {
			for (InvoiceDetailsDTO invoiceDetailsDTO : invoiceDTO.getInvoiceDetailsDTO()) {
				InvoiceDetailsVO detailsVO=new InvoiceDetailsVO();
				detailsVO.setDescription(invoiceDetailsDTO.getDescription());
				detailsVO.setQuantity(invoiceDetailsDTO.getQuantity());
				detailsVO.setPrice(invoiceDetailsDTO.getPrice());
				detailsVO.setAmount(invoiceDetailsDTO.getPrice()*invoiceDetailsDTO.getQuantity());
				detailsVO.setInvoiceVO(invoiceVO);
				invoiceDetailsVO.add(detailsVO);
				}
		}
		invoiceVO.setInvoiceDetailsVO(invoiceDetailsVO);
		InvoiceVO invoiceVO2 =invoiceRepository.save(invoiceVO);
		double totamount=invoiceDetailsRepository.findTotalAmount(invoiceVO2.getId());
		invoiceVO2.setTotal(totamount);		
		return invoiceRepository.save(invoiceVO2);
	}

	@Override
	public List<InvoiceVO> getAllInvoiceVO() {
		// return ticketRepo.findAll();
		return invoiceRepository.findAll();
	}

	@Override
	public InvoiceVO getInvoiceById(Long Id) {
		// TODO Auto-generated method stub
		return invoiceRepository.findById(Id).get();
	}

	@Override
	public InvoiceVO updateInvoice(InvoiceDTO invoiceDTO) throws ApplicationException {
		InvoiceVO invoiceVO = new InvoiceVO();
		if (ObjectUtils.isNotEmpty(invoiceDTO.getId())) {
			invoiceVO = invoiceRepository.findById(invoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Invoice details"));
		}

		getInvoiceVOFromInvoiceDTO(invoiceDTO, invoiceVO);

		// Update customer details excluding customer type and customer code
		// Update or add new address details
		List<InvoiceDetailsVO> invoiceDetailsVO = new ArrayList<>();
		if (invoiceDTO.getInvoiceDetailsDTO() != null) {
			for (InvoiceDetailsDTO invoiceDetailsDTO : invoiceDTO.getInvoiceDetailsDTO()) {
//				InvoiceDetailsVO dt=invoiceDetailsRepository.findById(invoiceDetailsDTO.getId()).get();
				if (invoiceDetailsDTO.getId()!=0) {
					InvoiceDetailsVO detailsVO = invoiceDetailsRepository.findById(invoiceDetailsDTO.getId()).orElseThrow(
							() -> new ApplicationException("Invoice details not found for ID: " + invoiceDetailsDTO.getId()));
					detailsVO.setDescription(invoiceDetailsDTO.getDescription());
					detailsVO.setQuantity(invoiceDetailsDTO.getQuantity());
					detailsVO.setPrice(invoiceDetailsDTO.getPrice());
					detailsVO.setAmount(invoiceDetailsDTO.getPrice()*invoiceDetailsDTO.getQuantity());
					detailsVO.setInvoiceVO(invoiceVO);
					invoiceDetailsVO.add(detailsVO);
				} 
				else
				{
					InvoiceDetailsVO detailsVO=new InvoiceDetailsVO();
					detailsVO.setDescription(invoiceDetailsDTO.getDescription());
					detailsVO.setQuantity(invoiceDetailsDTO.getQuantity());
					detailsVO.setPrice(invoiceDetailsDTO.getPrice());
					detailsVO.setAmount(invoiceDetailsDTO.getPrice()*invoiceDetailsDTO.getQuantity());
					detailsVO.setInvoiceVO(invoiceVO);
					invoiceDetailsVO.add(detailsVO);
				}
			}
		}
		invoiceVO.setInvoiceDetailsVO(invoiceDetailsVO);
		InvoiceVO invoiceVO2= invoiceRepository.save(invoiceVO);
		double totamount=invoiceDetailsRepository.findTotalAmount(invoiceVO2.getId());
		invoiceVO2.setTotal(totamount);
		return invoiceRepository.save(invoiceVO2);
	}

	private void getInvoiceVOFromInvoiceDTO(InvoiceDTO invoiceDTO, InvoiceVO invoiceVO) throws ApplicationException {
		InvoiceVO existInvoice= invoiceRepository.findById(invoiceDTO.getId())
				.orElseThrow(() -> new ApplicationException("Invoice with ID " + invoiceDTO.getId() + " not found"));
		
		invoiceVO.setInvoiceNo(invoiceDTO.getInvoiceNo());
		invoiceVO.setInvoiceTo(invoiceDTO.getInvoiceTo());
		invoiceVO.setAddress(invoiceDTO.getAddress());
		
	}

	@Override
	public void deleteInvoiceDetails(Long id) {
		
		 invoiceDetailsRepository.deleteById(id);
		
	}

}
