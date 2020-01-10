<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <% response.setStatus(200); //에러인 500을 정상적으로 받으라는 의미. 200은 요청을 정상적으로 처리 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서버 오류</title>
</head>
<body>
<center>
	<h2>서버에서 다음의 오류가 발생했습니다.</h2>
	<p>오류 : ${exception.getMessage() }</p>
	<p>관리자에게 문의하세요 ㅋㅋ</p>
</center>
</body>
</html>



