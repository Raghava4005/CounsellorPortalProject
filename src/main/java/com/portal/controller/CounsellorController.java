package com.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.portal.dto.DashboardResponse;
import com.portal.entities.Counsellor;
import com.portal.service.CounsellorService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	private CounsellorService counsellorService;

	public CounsellorController(CounsellorService counsellorService) {
		this.counsellorService = counsellorService;
	}

	@GetMapping("/")
	public String index(Counsellor counsellor, Model model) {

		model.addAttribute("counsellor", counsellor);

		return "index";
	}

	@PostMapping("/login")
	public String loginHere(Counsellor counsellor, HttpServletRequest request, Model model) {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

		if (c == null) {

			model.addAttribute("emgs", "Invalid Credtinals..");
			model.addAttribute("counsellor", counsellor);
			return "index";
		}
		else {
			
			HttpSession session =  request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {
		
		
		HttpSession session =  req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		
		DashboardResponse response = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo", response);
		
		
		return "dashboard";
	}
	@GetMapping("/register")
	public String registerPage(Counsellor counsellor, Model model) {
		
		model.addAttribute("counsellor", counsellor);
		
		return "register";
	}
	
	@PostMapping("/register")
	public String registerHere(Counsellor counsellor, Model model) {
		
	    Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		if (byEmail !=null) {
			model.addAttribute("emgs", "Duplicate Email");
			return "register";
		}
		
		boolean isRegistered = counsellorService.register(counsellor);
		 
		if (isRegistered) {
			model.addAttribute("smgs", "Registration Successful...!!");
		}
		else {
			model.addAttribute("emgs", "Registration Failed");
		}
		return "register";
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		session.invalidate();
		
		return "redirect:/";
	}
}
