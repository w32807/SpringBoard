package com.jwj.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jwj.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	ModelAndView mav;
	
	@GetMapping("/")
	public String home(Locale locale, Model model) {
		log.warn("home()");
		return "home";
	}
	@GetMapping("joinFrm")
	public String joinFrm() {
		log.warn("joinFrm()");
		
		return "joinFrm";
	}
	@PostMapping("memberInsert")
	public ModelAndView memberInsert(MemberDto member, RedirectAttributes rttr) {
		
		
		
		return mav;
	}
	
	
}
