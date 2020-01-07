package com.jwj.dao;



import com.jwj.dto.MemberDto;

public interface MemberDao {
	
	public boolean memberInsert(MemberDto mb);
	
	public String getSecurityPwd(String id); //아이디로 패스워드를 가져옴
	
	public MemberDto getMemberInfo(String id);//아이디로 그 사용자의 정보를 다 가져옴
}






