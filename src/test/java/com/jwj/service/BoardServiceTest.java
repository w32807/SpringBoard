package com.jwj.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
//경로 : src/test/java/com.jwj.dao/BoardServiceTest.java
public class BoardServiceTest {
	//2가지 test
	//1. service bean이 잘 생성 되는지
	@Setter(onMethod_ = {@Autowired})//test 할 때는 매개변수가 여러개라고 가정하자.
	private BoardService bServ;
	
	@Test
	public void testExist() {
		log.info(bServ);
		assertNotNull(bServ);
	}
	//2. getboardList()가 정상적으로 작동하는지 여부

	@Test
	public void testGetBoardList() {
		ModelAndView mv = bServ.getBoardList(null);//처음 게시판 글이 뜰 때 null이 들어감
		log.info(mv.getModel());//ModelAndView의 인스턴스 mv에서 Model(데이터)를 꺼내와서 확인하자.
		log.info(mv.getViewName());//ModelAndView의 인스턴스 mv에서 viewNamel(JSP주소)를 꺼내와서 확인하자.
		
	}
	
	@Test
	public void testGetBoardContents() {
		ModelAndView mv = bServ.getBoardContents(44);	
		log.info(mv.getModel());
		log.info(mv.getViewName());
	}
}