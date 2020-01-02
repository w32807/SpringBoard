package com.jwj.dao;

import javax.sound.midi.MidiDevice.Info;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jwj.dto.BoardDto;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
//경로 : src/test/java/com.jwj.dao/BoardDaoTest.java
public class BoardDaoTest {
	
	@Setter(onMethod_ = @Autowired)
	private BoardDao dao;
	
	@Test
	public void testGetList() {
			dao.getList(1).forEach(board -> log.info(board));
			//람다식. 앞의 board라는 변수에 하나하나 forEach로 꺼내와서 log.info(board)에 넣겠다.
	}
	
	@Test
	public void testGetContents() {
		BoardDto boardDto = dao.getContents(44);
		log.info(boardDto);
		
	}
}
