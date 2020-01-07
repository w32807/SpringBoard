package com.jwj.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jwj.dto.MemberDto;
import com.jwj.service.MemberService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	//세션(로그아웃에서 활용)
	@Setter(onMethod_ = @Autowired)
	private HttpSession session;
	
	//서비스
	@Setter(onMethod_ = @Autowired)
	private MemberService mserb;
	
	//ModelAndView 객체
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
