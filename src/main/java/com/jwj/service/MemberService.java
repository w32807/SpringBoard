package com.jwj.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jwj.dao.MemberDao;
import com.jwj.dto.MemberDto;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service//안 붙여주면 스캔 대상에서 제외함
@Slf4j
public class MemberService {
		//세션 처리를 위한 세션 객체 
			@Setter(onMethod_=@Autowired)
			private HttpSession  session;//session Interface
			//세션에 관한 Bean을 생성 안했지만, 사용자가 접속을 하면 자동으로 tomcat에서 session bean을 생성하고,
			//그 bean을 가져와서 @Autowired가 가능하다.
		
		//Dao 객체
			@Setter(onMethod_=@Autowired)
			private MemberDao mDao;
	
		//데이터 전달용 ModelAndView
			private ModelAndView mav;
			
			
			
			public ModelAndView memberInsert(MemberDto member, RedirectAttributes rttr) {
				mav = new ModelAndView();
				String view = null;
				//비밀번호 암호화 처리(인코드 : 아날로그 > 디지털 / 디코드 : 디지털 > 아날로그)
				//스프링 시큐리티는 암호화는 하지만, 복호화는 안함.
				//사용자가 입력한 패스워드와, 암호화 되어 저장된 패스워드가 일치하는지는 메소드 이용하여 스프링이 알아서 처리하게 한다.
				BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
				
				//원래 암호를 인코딩하여 String에 저장
				String encPwd = pwdEncoder.encode(member.getM_pwd());
				member.setM_pwd(encPwd);//암호화된 패스워드를 다시 넣어주기
				try {
						mDao.memberInsert(member);
						//회원 가입성공이면 로그인 화면으로 돌아감 (아이디(primary key가 중복되지 않으면 성공!)
						view= "redirect:/";
						rttr.addFlashAttribute("check", "회원가입 성공!");
					} catch (Exception e) {
						view = "redirect:joinFrm";//실패하면 다시 회원가입 페이지로 돌아감
						rttr.addFlashAttribute("check", "fail");

					}
				mav.setViewName(view);//데이터를 담지 않는다면, 굳이 ModelAndView를 쓰지 않고 String을 반환해도 된다.
				return mav;
			}
			
			
}//class의 끝
