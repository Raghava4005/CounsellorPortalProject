package com.portal.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.dto.ViewEnqsFilterRequest;
import com.portal.entities.Enquiry;
import com.portal.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	private EnquiryService enquiryService;

	public EnquiryController(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		Enquiry enquiry = new Enquiry();
		
		model.addAttribute("enquiry", enquiry);
		 model.addAttribute("isEdit", false);

		return "enquiryForm";
	}

	@PostMapping("/addEnquiry")
	public String saveEnquiry(@ModelAttribute("enquiry") Enquiry enquiry,@RequestParam(value = "isEdit", required = false) Boolean isEdit,  HttpServletRequest req, Model model)
			throws Exception {

		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		boolean isSaved;

	    if (Boolean.TRUE.equals(isEdit)) {
	        // Update existing enquiry
	        isSaved = enquiryService.addEnquiry(enquiry, counsellorId);
	        if (isSaved) {
	            model.addAttribute("smgs", "Enquiry Updated..!!");
	        } else {
	            model.addAttribute("emgs", "Failed to update enquiry");
	        }
	    } else {
	        // Add new enquiry
	        isSaved = enquiryService.addEnquiry(enquiry, counsellorId);
	        if (isSaved) {
	            model.addAttribute("smgs", "Enquiry Added..!!");
	        } else {
	            model.addAttribute("emgs", "Failed to add enquiry");
	        }
	    }
		
        /*
		boolean isSaved = enquiryService.addEnquiry(enquiry, counsellorId);

		if (isSaved) {
			model.addAttribute("smgs", "Enquiry Added..!!");
		} else {
			model.addAttribute("emgs", "Failed to add enquiry");
		}*/
		
		
	    model.addAttribute("isEdit", isEdit);
		model.addAttribute("enquiry", new Enquiry());
		
		
		return "enquiryForm";

	}

	@GetMapping("/viewEnqs")
	public String viewAllEnq(HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		List<Enquiry> enquiry = enquiryService.getAllEnquires(counsellorId);

		model.addAttribute("viewEnqs", enquiry);

		ViewEnqsFilterRequest viewEnqsFilterRequest = new ViewEnqsFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", viewEnqsFilterRequest);

		return "viewEnqs";
	}

	@PostMapping("/filterEnq")
	public String filterEnquiry(ViewEnqsFilterRequest viewEnqsFilterRequest, HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		List<Enquiry> enqsList = enquiryService.getEnquiriesWithFilter(viewEnqsFilterRequest, counsellorId);

		model.addAttribute("viewEnqs", enqsList);

		return "viewEnqs";

	}

	@GetMapping("/editEnq")
	public String editEnq(@RequestParam("enquiryId") Integer enquiryId, Model model) {

		Enquiry enquiry = enquiryService.getEnquiryById(enquiryId);
		model.addAttribute("enquiry", enquiry);
		model.addAttribute("isEdit", true);
		return "enquiryForm";
	}

}
