package com.jwj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.jwj.dao.BoardDao;
import com.jwj.dto.BoardDto;
import com.jwj.util.Paging;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Service //@Componet의 일종인데, Service bean임을 나타내주는 annotation이다.
@Slf4j //로그를 찍는데 사용하는 annotation
//경로 : src/main/java/com.jwj.Service/BoardService.java
public class BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardDao bDao;//게시판 정보가 담긴 List를 가져오기 위함.
	
	//ModelAndView 객체 (데이터를 view에 넘겨줌)
	//데이터는 Model에, 화면은 View에.
	//Model에 데이터를 집어넣고 View에 JSP파일을 지정해주고 return을 하면 그 JSP파일로 넘어간다.

	ModelAndView mav;//스프링에서 제공하는 객체
	
	public ModelAndView getBoardList(Integer pageNum) {
		//int가 아닌 이유는, Integer 객체로써 다루려 하기 때문
		log.info("getBoardList() - pageNum : " + pageNum);//@Slf4j를 사용하여 실행이 잘 되는지 확인하기 위한 로그
		mav = new ModelAndView();
		
		int num = (pageNum == null)? 1 : pageNum;//맨 처음에는 넘어오는 페이지 넘버가 없기 때문에 1페이지부터 시작함
		
		List<BoardDto> bList = bDao.getList(num);//페이지 번호를 가져오고, 그 번호에 해당하는 List를 가져온다.
		mav.addObject("bList", bList);//bList라는 이름으로 bList 데이터를 넣겠다.
	//------추가분-----------------------------------------------------------------------------------------
		mav.addObject("paging",getPaging(num));
	//-----------------------------------------------------------------------------------------------------
		mav.setViewName("boardList");//mav를 보낼 jsp파일의 이름
		return mav;//데이터를 담은 mav가 controller쪽으로 return 된다.
	}

	
	//------추가분-----------------------------------------------------------------------------------------
	private Object getPaging(int num) {
		//전체 글 개수 구하기(from DB)
		int maxNum = bDao.getBoardCount();
		int listCount = 10;//페이지 당 글 갯수
		int pageCount = 2; //한 그룹당 페이지 갯수
		String listName = "list";//BoardController의 RequestMapping 과 똑같아야 함.
		Paging paging = new Paging(maxNum, num, listCount, pageCount, listName);
		String pagingHtml = paging.makeHtmlPaging();		
		
		return pagingHtml;
	}
	//-----------------------------------------------------------------------------------------------------

	
	
	public ModelAndView getBoardContents(Integer bnum) {
		mav = new ModelAndView();//지역 변수
		BoardDto board = bDao.getContents(bnum);
		
		//파일 목록불러오는 처리 ...
		//댓글 불러오는 처리...
		mav.addObject("board", board);
		mav.setViewName("boardContents");
		return mav;
	}
}











