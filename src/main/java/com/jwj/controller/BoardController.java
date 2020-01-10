package com.jwj.controller;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jwj.dto.BoardDto;
import com.jwj.dto.ReplyDto;
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
	
	
	
	//첨부파일 다운로드 처리를 위한 메소드
	@GetMapping("/download")
	public void fileDownload(String sysFileName, HttpServletRequest req ,HttpServletResponse resp) {
		//form에서 데이터가 넘어올 때, bean과 같은 이름이 있으면 거기에 넣어주고
		// 아니라면 그냥 그 이름대로 데이터가 넘어온다.
		bServ.fileDown(sysFileName, req, resp);
	}
	
	//500에러코드 처리 (404는 안됨)
	@ExceptionHandler(RuntimeException.class)//실행 중에 발생되는 예외와 관련된 annotation
	public String expHandler(Model model, Exception e) {
		e.printStackTrace();
		model.addAttribute("exception",e);
		return "error/error500";
	}
	
	//수정 버튼을 누르면 여기로 와서 해당 글 번호의 내용을 모두 불러온다
	@GetMapping("/updateFrm")
	public ModelAndView intoUpdateFrm(Integer bnum,  RedirectAttributes rttr) {
		mav = bServ.getBCforUpdate(bnum,rttr);
		return mav;
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(MultipartHttpServletRequest multi, BoardDto board,  RedirectAttributes rttr) {
		
		String view = bServ.updateBoard(multi, board,rttr);
		return view;
	}
	
	@PostMapping(value = "/replyInsert", produces = "application/json; charset=utf-8")
																					//text/html은 텍스트이고 html이다.
																					//시스템에서 만든 것이고 json이다 라는 뜻.
	//dispather를 안 거치고 바로 html로 보냄
	//model을 써야만 디스패처서블릿을 간다.
	@ResponseBody
	public Map<String, List<ReplyDto>> replyInsert(ReplyDto reply){
		Map<String, List<ReplyDto>> rmap = bServ.replyInsert(reply);
		
		return rmap;
	}
	
	
	@GetMapping("delBoard")//글 삭제를 위한 controller 메소드
	public String delBoard (@RequestParam("bnum")int bnum, RedirectAttributes rttr) {
		String view = bServ.delBoard(bnum,rttr);
		return view;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
