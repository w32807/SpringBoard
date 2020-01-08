package com.jwj.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {
//경로 : src/main/java/com.jwj.util/SessionInterceptor.java
	//인터셉트를 하는 두 가지 방법
	@Setter(onMethod_ = @Autowired)
	private HttpSession session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	//즉 prehandle은 컨트롤러로 들어가기 전 처리 (들어가기 전에 인터셉트함!)
			throws Exception {
		log.warn("preHandle - 인터셉트");
		
		 if(session.getAttribute("mb") == null) {//자바 소스에서는 session의 정보를 이렇게 꺼내온다.
			 //session이 비어있다면...
			 response.sendRedirect("../");
			 return false;
		 }
		 return true;
	}//여기서 끝내면, 로그인 하지 않은 첫 화면에서는 계속 홈 화면으로 돌아간다.
	 // 그래서 이 메소드를 적용 시키지 않을 uri를 제외시켜준다.
	 //제외는 servlet-context.xml에서!
	
	
	
	// 로그아웃 후 뒤로가기 막기
	// postHandle 메소드를 재정의하여, 브라우저의 캐시 제거
	// 브라우저는 session정보까지 모두 저장을 함. 
	// 로그아웃을 해도 브라우저에서 페이지 자체를 저장하기 때문에
	// 그러한 캐시를 제거하여 뒤로가기를 막는다
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			if(request.getProtocol().equals("HTTP/1.1")) {
				//브라우저의 프로토콜이 1.1버전, 1.0버전이 있다.
				response.setHeader("Cache-Control", "no-cache, no-store , must-revalidate");
				//응답의 header 부분에 명령을 내려줌! 
			}
			else {//프로토콜 버전이 HTTP/1.0일 때
				response.setHeader("Pragma", "no-cache");
			}
			
			response.setDateHeader("Expires", 0);
			//일종의 타이머로서, 캐시가 있으면 바로 폐기를 해라.(만료기능, 지정시간)
	}
	//프로토콜은 header와 body부분이 있다.
	
	
}//class의 끝

