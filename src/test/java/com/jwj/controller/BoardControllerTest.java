package com.jwj.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"//서비스 연동
										,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})//컨트롤러 스캔		
@Log4j
//경로 : src/test/java/com.jwj.controller/BoardControllerTest.java

public class BoardControllerTest {
	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;//jsp를 통해 web을 통해 다녀와야 하는데 그냥 테스트를 위해, 웹 환경을 대신해 처리할 녀석이 필요함. (가상의 웹)
	
	private MockMvc mock;//실제같이 보이는 모형
	//WebAppConfiguration를 통해 웹같은 녀석과 연결하고, MockMvc를 통해 가상의 웹을 만든다
	
	@Before
	public void setup() {
		//가상의 웹환경을 만들자.
		//WebAppConfiguration과 MockMvc를 생성하기 위한 메소드.
		this.mock = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testGetList() throws Exception{
		log.info(mock.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn().getModelAndView().getModelMap()
				);//mock을 실행하기 위해 가상의 request를 만들자.
				  //request를 만들고, "board/list"가 get 방식으로 요청이 되고 /list를 통해
				  //@GetMapping("/list") 메소드에 들어가고. 데이터가 mav에 들어가고 
				  //getModelMap로 데이터를 mapping(가져온다) 한다 
	}
	
	@Test
	public void tesetGetContents() throws Exception{
		log.info(mock.perform(MockMvcRequestBuilders.get("/board/contents?bnum=44"))
				.andReturn().getModelAndView().getModelMap()
				);
		
		
		
	}

}
