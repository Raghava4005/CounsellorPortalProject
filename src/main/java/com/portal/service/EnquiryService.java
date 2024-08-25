package com.portal.service;

import java.util.List;

import com.portal.dto.ViewEnqsFilterRequest;
import com.portal.entities.Enquiry;

public interface EnquiryService {

	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) throws Exception;

	public List<Enquiry> getAllEnquires(Integer counsellorId);

	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId);

	public Enquiry getEnquiryById(Integer enqId);

}
