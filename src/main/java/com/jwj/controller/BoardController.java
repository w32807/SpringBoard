package com.jwj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jwj.service.BoardService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


//URI을 받아옴 http://localhost/memberboard/board/가 붙은 모든 메소드은 이 클래스로 들어와라.
//@RequestMapping가 붙은 메소드는 그 주소를 가진 class에 들어가서 실행 된다.
//경로 : src/main/java/com.jwj.dao/BoardController.java
@Controller
@Slf4j
@RequestMapping("/board/*")
public class BoardController {
	//이 컨트롤러로 들어오는 요청의 uri는 /board/
	
	@Setter(onMethod_ = @Autowired)
	private BoardService bServ;
	
	ModelAndView mav;//service에서 넘어오는 ModelandView를 받기 위한 변수
	
	@GetMapping("/list")//get방식으로 전송되는 method. 
	//http://localhost/memberboard/board/list 으로, 이 메소드를 실행하도록 해주는 annotation
	public ModelAndView boardList(Integer pageNum) {
		log.info("boardList()");
		mav = bServ.getBoardList(pageNum);
		
		return mav;
	}
	
	@GetMapping("/contents")
	public ModelAndView boardContents(Integer bnum) {//form에서 넘겨주는 name과 controller의 매개변수 명과 같아야 한다.
		log.info("boardContents - bnum = " + bnum);
		mav = bServ.getBoardContents(bnum);
		
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
