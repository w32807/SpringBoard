package com.jwj.dto;

import lombok.Data;

@Data
public class MemberDto {
		String m_id;             //회원 아이디
		String m_pwd;         //회원 비밀번호
		String m_name;       //회원 이름
		String m_birth;        //회원 생년월일
		String m_addr;        //회원 주소
		String m_phone;      //회원 전화번호
		int m_point;             //회원 포인트
		String g_name;        //회원 등급이름
		
	
}
