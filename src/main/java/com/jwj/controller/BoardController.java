package com.jwj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	
	//글 쓰기 관련 작업 2가지.
	//1. 리스트 화면에서 글쓰기 화면으로 전환하기
	//DB안가고 화면전환만 하는 방법
	@GetMapping("/writeFrm")
	public String writeFrm() {
			///writeFrm요청이 들어오면 실행 됨
		return "writeFrm";
	}
	//2. 글쓰기 화면에서 들어 온 데이터를 처리
	@PostMapping("/boardWrite")//form의 action과 같아야 함.
	public ModelAndView boardWrite(MultipartHttpServletRequest multi, RedirectAttributes rttr) {
		//RedirectAttributes rttr - 한번 쓰고 말 데이터를 추가해주자.
		log.info("boardWrite()");
		mav = bServ.boardInsert(multi, rttr);
		//여기에서, jsp에서 들어온 form데이터를 service에 넣어줌(이 때는 위와 다른 브라우저의 요청이 들어오는 것)
		return mav;
	}
	
	
	
	
	
	
	
	
}
