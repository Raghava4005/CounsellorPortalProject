package com.portal.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.portal.dto.ViewEnqsFilterRequest;
import com.portal.entities.Counsellor;
import com.portal.entities.Enquiry;
import com.portal.repo.CounsellorRepo;
import com.portal.repo.EnquiryRepo;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	private EnquiryRepo enquiryRepo;

	private CounsellorRepo counsellorRepo;

	public EnquiryServiceImpl(EnquiryRepo enquiryRepo, CounsellorRepo counsellorRepo) {
		this.enquiryRepo = enquiryRepo;
		this.counsellorRepo = counsellorRepo;
	}

	@Override
	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) throws Exception {

		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);

		if (counsellor == null) {
			throw new Exception("No Counsellor is found...!!");
		}

		enquiry.setCounsellor(counsellor);
		Enquiry save = enquiryRepo.save(enquiry);

		if (save.getEnquiryId() != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<Enquiry> getAllEnquires(Integer counsellorId) {

		return enquiryRepo.getEnquiriesByCounsellorId(counsellorId);
	}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {

		// QBE impl (Dynamic query prep)

		Enquiry enq = new Enquiry();

		if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}

		if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}

		if (StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}

		Counsellor c = counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(c);

		Example<Enquiry> of = Example.of(enq);

		List<Enquiry> enqList = enquiryRepo.findAll(of);

		return enqList;

	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return enquiryRepo.findById(enqId).orElse(null);

	}

}
