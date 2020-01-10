package com.jwj.dao;

import java.util.*;

import com.jwj.dto.BfileDto;
import com.jwj.dto.BoardDto;
import com.jwj.dto.ReplyDto;

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
	
	//게시글 등의 내용을 BoardDto 인스턴스에 넣어 DB에 입력 할 메소드
	//여기서 메소드는 데이터를 받아서 가져오는게 아니라 데이터를 담아서 DB 쪽으로 넘기는 역할 
	//insert가 잘 처리되면 true 값이 넘어온다. - Mybatis가 알아서 잘 처리해줌.
	public boolean boardInsert(BoardDto board);
	//true는 잘 넘어오는데 false가 넘어오면 예외처리가 된다.
	
	public boolean fileInsert(Map<String, String> fmap);
	//파일들의 이름이 저장 된 map을 매개변수로 받음
	
	public List<BfileDto> getBfList(Integer bnum);
	//파일이 여러 개일 경우도 있으니, List로 잡아주자
	
	public String getOriName(String sysFileName);
	//다운로드를 위한 original Name을 얻어오기 위한 메소드
	// 겹치지 않는 sysFileName으로 original name을 찾아온다.
	
	public boolean updateBoard(BoardDto board);
	//board의 내용을 업데이트하고, 다시 그것을 가져와 화면에 나타낼 예정
	
	public List<ReplyDto> getReplyList(Integer bnum);
	//이미 작성 된 댓글을 List로 가져오자!
	
	public boolean replyInsert(ReplyDto reply);
	//댓글을 넣자
	
	public boolean upView(Integer bnum);
	//조회수를 늘려주자
	public boolean delBoardFile(int bnum);
	
	public boolean delBoardReply(int bnum);
	
	public boolean delBoardContents(int bnum);
}





