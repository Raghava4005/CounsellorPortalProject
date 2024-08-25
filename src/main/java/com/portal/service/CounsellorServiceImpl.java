package com.portal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.portal.dto.DashboardResponse;
import com.portal.entities.Counsellor;
import com.portal.entities.Enquiry;
import com.portal.repo.CounsellorRepo;
import com.portal.repo.EnquiryRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	private CounsellorRepo counsellorRepo;

	private EnquiryRepo enquiryRepo;

	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enquiryRepo) {
		this.counsellorRepo = counsellorRepo;
		this.enquiryRepo = enquiryRepo;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		Counsellor counsellor = counsellorRepo.findByEmailAndPwd(email, pwd);
		return counsellor;
	}

	@Override
	public Counsellor findByEmail(String email) {
		return counsellorRepo.findByEmail(email);
	}

	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor = counsellorRepo.save(counsellor);
		if (null != savedCounsellor.getCounsellorId()) {
			return true;
		}
		return false;

	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {

		DashboardResponse response = new DashboardResponse();

		List<Enquiry> enqList = enquiryRepo.getEnquiriesByCounsellorId(counsellorId);

		int totalEnq = enqList.size();

		int enrolledEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
				.collect(Collectors.toList()).size();
		int openEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Open")).collect(Collectors.toList())
				.size();
		int lostEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Lost")).collect(Collectors.toList())
				.size();

		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setOpenEnqs(openEnqs);
		response.setLostEnqs(lostEnqs);
		return response;
	}

}
