package com.jwj.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
}//class의 끝

