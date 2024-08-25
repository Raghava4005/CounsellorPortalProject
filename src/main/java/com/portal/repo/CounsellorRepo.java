package com.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portal.entities.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {

	public Counsellor findByEmailAndPwd(String email, String pwd);

	public Counsellor findByEmail(String email);
}
