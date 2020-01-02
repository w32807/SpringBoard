package com.jwj.dbtest;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j

//경로 : src/test/java/com.jwj.dbtest/DataSourceTest.java
public class DataSourceTest {
	
		@Setter(onMethod_ = {@Autowired})
		private DataSource dataSource;
		//root-context.xml의 id = dataSource인 bean을 테스트 한다.
		//앞선 레스토랑과의 차이점은?
		
		@Test
		public void testConnection() {
			try (Connection con = dataSource.getConnection()){
				//try의 괄호안에, close가 되어야할 애들
				//DB close, file 입출력 close 을 전에는 finally로 처리했는데 여기서는 try with resources 구문을 사용.
				//연결 성공을 하면 실행이 되고 나서 close를 해주고, 연결을 실패하더라도 catch문 실행 후 close를 해준다.
				log.info(con);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
	
	
}
