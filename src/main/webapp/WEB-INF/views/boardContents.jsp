<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- 경로 : src/main/webapp/WEB-INF/views.boardCOntents.jsp -->

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>번호 : ${board.bnum}</p>
<p>제목 : ${board.btitle}</p>
<p>작성자 : ${board.mname}</p>
<p>작성일 : ${board.bdate}</p>
<p>조회수 : ${board.bviews}</p>
<p>글 내용 : ${board.bcontents}</p>


</body>
</html>




