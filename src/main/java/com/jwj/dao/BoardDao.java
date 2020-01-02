package com.jwj.dao;

import java.util.*;

import com.jwj.dto.BoardDto;

//경로 : src/main/java/com.jwj.dao/BoardDao.java
public interface BoardDao {
	//Mybatis가 들어가는 DAO는 interface로 잡아주자
	
	public List<BoardDto> getList(int pageNum);//게시판 글을 저장할 list이며, 매개변수로 각각의 페이지 번호가 들어간다.
	public BoardDto getContents(Integer bnum);//지금은 매개변수 타입이 상관없지만, Integer는 null임일 때 분기할 때 쓰거나, 일반적으로는 Integer로 쓴다.
	//sevlet context에서 보내줄 때, int로 잡아줘도 되지만, 보통 들어올 때 wrapper클래스로 감싸서 들어오기 때문에 integer로 쓴다.
	
	//전체 글 갯수를 가져오는 메소드
	public int getBoardCount();
	//** Mybatis에서는 매개 변수를 1개밖에 전달 못한다. - Mybatis interface에서 매개변수를 1개밖에 못 받는다는 얘기.
	//그래서 여러개를 전달하고 싶을 때는, Map을 이용하여 Map 1개를 전달한다.
}





