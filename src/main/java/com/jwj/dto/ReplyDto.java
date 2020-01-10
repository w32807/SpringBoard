package com.jwj.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReplyDto {
		
			private int r_num;
			private int r_bnum;
			private String r_contents;
			private String r_id;
			
			// 자바의 Map Object를 json Object로 바꾸어 화면에 출력한다.
			// 날짜가 json 형식으로 들어가게 하기 위해서!
			// 숫자나 문자열은 그냥 써도 알아서 변환해줌
			@JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
			private Timestamp r_date;
	//data 처리시 json 객체로 변환할 때 jackson이 원활하게 처리하도록 pattern을 지정
	
	
}
