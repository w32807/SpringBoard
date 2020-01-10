package com.jwj.dao;



import com.jwj.dto.MemberDto;

public interface MemberDao {
	
	public boolean memberInsert(MemberDto mb);
	
	public String getSecurityPwd(String id); //아이디로 패스워드를 가져옴
	
	public MemberDto getMemberInfo(String id);
	//아이디로 그 사용자의 정보를 다 가져옴
	//얘는 view에 있는 정보만 가져옴

	public MemberDto getMemberInfo2(String getSessionId);
	//얘는 member 정보 다 가져옴 (위의 메소드와의 차이는 쿼리문 참고)

	public boolean memberUpdate(MemberDto member);
}






