package com.portal.service;

import com.portal.dto.DashboardResponse;
import com.portal.entities.Counsellor;

public interface CounsellorService {

	public Counsellor login(String email, String pwd);

	public boolean register(Counsellor counsellor);

	public DashboardResponse getDashboardInfo(Integer counsellorId);

	Counsellor findByEmail(String email);
}
