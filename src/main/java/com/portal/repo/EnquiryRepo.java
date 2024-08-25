package com.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.portal.entities.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
	
	@Query(value = "select * from enquiry_tab where counsellor_id=:counsellorId", nativeQuery = true)
	public List<Enquiry> getEnquiriesByCounsellorId(Integer counsellorId);
	
}
