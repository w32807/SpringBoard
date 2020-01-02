package com.jwj.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
//경로 : src/main/java/com.jwj.dto/BoardDto.java
public class BoardDto {
	//게시판의 내용을 보여주기 위한 클래스. 
	//Dto는 원하는 데이터를 DB에서 처리를 하기 위해서 Dto에 담아 DB에서 처리를 함
	//Dto의 클래스와 DB의 테이블과 1-1대응이라 보면 된다.
	private int bnum;
	private String btitle;
	private String bcontents;
	private String bid;
	private String mname;
	private Timestamp bdate;//시간을 나타내기 위한 변수
	private int bviews;
	

}
