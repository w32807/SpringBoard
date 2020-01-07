<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %><!-- 이 페이지에서는 session을 못쓰게 한다. session에 관련된 데이터를 안 받는다는 의미 -->
<html>
<head>
	<title>스프링 게시판 홈</title>
	 <script type="text/javascript">
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
				var chk = "${check}";
				if(chk != ""){
					alert(chk);
					location.reload(true);//화면을 다시 한번 불러오면서 check를 리셋함 
				}
			}
      </script>
</head>
<body>
	<center>
	<h2>	회원제 게시판 </h2><hr>
		<form action="login" method="post">
			<table border="1">
					<tr>
						<td colspan="2" align="center" bgcolor="skyblue">로그인</td>					
					</tr>
					<tr>
						<td><input type="text" name="m_id"  placeholder="아이디"  required="required"> </td>
						<td rowspan="2"><input type="submit" value="로그인" ></td>					
					</tr>
					<tr>
						<td><input type="password" name="m_pwd" placeholder="비밀번호" required="required"> </td>					
					</tr>
					<tr>
						<td colspan="2" align="center" bgcolor="skyblue" >JIP Board</td>					
					</tr>
					<tr>
					<tr>
						<td colspan="2" align="center" ><a href="./joinFrm">회원가입</a></td>	
																		<!-- a태그의 href form의 action과 주소 차이 확인하기 -->				
					</tr>			
					</tr>
			</table>
		</form>	
	<a href="board/list">게시판 목록 가기</a></body>
	
	</center>
</body>
</html>
