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
		mav = mserb.memberInsert(member, rttr);
		log.warn("memberInsert()");
		return mav;
	}
	
	@PostMapping("login")
	public ModelAndView loginProc(MemberDto member, RedirectAttributes rttr) {
		mav = mserb.loginProc(member, rttr);
		log.warn("loginProc()");
		
		return mav;
	}
	
	@GetMapping("logout")
	public String logout() {

		session.invalidate();
		//세션에 저장된 모든 정보 삭제		
		//근데 얘는 일관성에 위배 됨 왜??
		
		return "home";
	}
	
	@GetMapping("updateMember")//회원 정보 수정 버튼을 누르면 여기로 옴
	public ModelAndView getMember() {
		//아이디를 가지고 회원 정보를 가져 온 뒤, 다시 수정 페이지에 넘겨준다.
				
		return mserb.getMember();
	}
	
	@PostMapping("userUpdate")
	public String userUpdate(MemberDto member, RedirectAttributes rttr) {
		String view = mserb.userUpdate(member,rttr);
		
		return view;
	}
	
	
	
	
	

	
	
}
