package com.jwj.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
				//암호화가 되어 password가 저장되면, DB에서 아주 긴 데이터로 들어가기 때문에, 
				//DB에서100자 이상의 데이터공간으로 잡아주자
				try {
						mDao.memberInsert(member);
						//회원 가입성공이면 로그인 화면으로 돌아감 (아이디(primary key가 중복되지 않으면 성공!)
						view= "redirect:/";
						rttr.addFlashAttribute("check", "회원 가입 성공!");
					} catch (Exception e) {
						view = "redirect:joinFrm";//실패하면 다시 회원가입 페이지로 돌아감
						rttr.addFlashAttribute("check", "fail");
					}
				mav.setViewName(view);//데이터를 담지 않는다면, 굳이 ModelAndView를 쓰지 않고 String을 반환해도 된다.
				return mav;
			}
			
			
			public ModelAndView loginProc(MemberDto member, RedirectAttributes rttr) {
				mav = new ModelAndView();
				String view = null;
				//암호화된 비밀번호와 입력한 비밀번호를 비교할 때, 복호화가 불가능하니 매칭처리를 해주자.
				BCryptPasswordEncoder pwdEncode = new BCryptPasswordEncoder();
				
				//DB에서 암호화된 비번 구하기
				String encPwd = mDao.getSecurityPwd(member.getM_id());
				if(encPwd != null) {
					//아이디가 존재하며, 암호화된 패스워드를 가져왔다.
					if(pwdEncode.matches(member.getM_pwd(), encPwd)) {
					//사용자가 입력한 비밀번호와 암호화된 비밀번호가 같으면 true, 다르면 false
						member = mDao.getMemberInfo(member.getM_id());
						//사용자가 잘 입력했으니, 그 사용자의 모든 정보를 가져온다.
						session.setAttribute("mb", member);//로그인 유지를 위해 session에 넣어준다
						rttr.addFlashAttribute("check","로그인 성공!");
						view = "redirect:board/list";//게시글 목록 화면으로 전환
					}
					else {
						view = "redirect:/";
						rttr.addFlashAttribute("check","패스워드가 틀립니다.");
					}
				}
				else {
					//아이디가 존재하지 않는다.
					view = "redirect:/";
					rttr.addFlashAttribute("check","해당 아이디가 없습니다.");
				}
				mav.setViewName(view);
				return mav;
			}


			public ModelAndView getMember() {
				mav = new ModelAndView();
				MemberDto sessionMember = (MemberDto) session.getAttribute("mb");
				String getSessionId = sessionMember.getM_id();
				MemberDto member = mDao.getMemberInfo2(getSessionId);
				mav.addObject("member",member);
				mav.setViewName("updateMember");
				
				return mav;
			}


			public String userUpdate(MemberDto member, RedirectAttributes rttr) {
				String view = null;
				BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
				
				String encPwd = pwdEncoder.encode(member.getM_pwd());
				member.setM_pwd(encPwd);
				try {
					mDao.memberUpdate(member);
					view= "redirect:board/list";
					rttr.addFlashAttribute("check", "정보 수정 성공!");
				} catch (Exception e) {
					view = "redirect:updateMember";
					rttr.addFlashAttribute("check", "fail");
				}
					mav.setViewName(view);
				return view;
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}//class의 끝
