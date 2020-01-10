<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <% response.setStatus(200); //에러인 500을 정상적으로 받으라는 의미. 200은 요청을 정상적으로 처리 
    // 404는 에러페이지라서 JSP를 호출 하지 않음. 그래서 404페이지도 일반 페이지처럼 취급 해 주어야 한다.
    											//그래서 404코드를 200으로 바꾸자.%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 URL 오류</title>
</head>
<body>
<center>
	<h2>해당 페이지를 찾을 수 없습니다.</h2>
	<p>요청하신 페이지를 찾을 수 없습니다.</p>
	<p>다시 확인 바랍니다.</p>
</center>
</body>
</html>



