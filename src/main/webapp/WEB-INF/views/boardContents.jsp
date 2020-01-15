<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 보기</title>
 <script type="text/javascript">
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
				var chk = "${check}";
				if(chk != ""){
					alert(chk);
					location.reload(true);//화면을 다시 한번 불러오면서 check를 리셋함 
				}
			}
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
      			var delChk=${deleteCheck};
      			if(delChk !=1){
      				//input속성 중 type바꾸기
      				console.log(delChk);
      				document.getElementById("deleteButton").setAttribute("type","hidden");
      			}
      		}	
</script>
</head>
<body>
	<center>
		<h2>상세 보기</h2>
		<hr>
		<table>
			<tr height="30">
				<td width="100" bgcolor="pink" align="center">NUM</td>
				
				
				<td colspan="5">${board.bnum}</td>
				<td align="right">
					<form action="delBoard" name="delBoard" method="get">
							<input type="hidden"value="${board.bnum}"  name="bnum">
							<input id="deleteButton" type="button" value="글삭제"  onclick="confirmDelete(${board.bnum})" >
					</form>
				</td>
			</tr>
			<tr height="30">
				<td bgcolor="pink" align="center">WRITER</td>
				<td width="150">${board.mname}</td>
				<td bgcolor="pink" align="center">DATE</td>
				<td width="200">${board.bdate}</td>
				<td bgcolor="pink" align="center">VIEWS</td>
				<td width="100">${board.bviews}</td>
			</tr>
			<tr height="30">
				<td bgcolor="pink" align="center">TITLE</td>
				<td colspan="5">${board.btitle}</td>
			</tr>
			<tr height="200">
				<td bgcolor="pink" align="center">CONTENTS</td>
				<td colspan="5">${board.bcontents}</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="5">
				<c:if test="${empty bfList}">
					  첨부된 파일이 없습니다.
   			 </c:if> 
  				<c:if test="${!empty bfList}">
						<c:forEach var="file" items="${bfList}">
							<a href="./download?sysFileName=${file.bf_sysName}">${file.bf_oriName}</a>
							<!-- 현재 주소는 http://localhost/memberboard/board/contents?bnum=.. 이다.
									그리고 가고 싶은 곳은 http://localhost/memberboard/board/download이므로 점 1개 -->
						</c:forEach>
					</c:if></td>
			</tr>
		</table>
		<p />
		
			<table>
				<tr>
		<form name="rFrm" id="rFrm">
					<td><textarea rows="3" cols="50" name="r_contents" id="comment"></textarea></td>
					<td><input type="button" value="댓글전송" onclick="replyInsert(${board.bnum})" style="width: 70px; height: 50px"></td>
		</form>		
					<td>
		<form name="mFrm" id="mFrm" action="updateFrm" method="get">
					<input type="hidden"value="${board.bnum}"  name="bnum">
					<input type="submit" value="수정" >
		</form>
		
					<button onclick="location.href='./list?pageNum=${pageNum}'">돌아가기</button>
					</td>
				
			</tr>
				
				
			</table>
		
		<p />
		<table>
			<tr bgcolor="skyblue" align="center" height="30">
				<td width="100">WRITER</td>
				<td width="200">CONTENTS</td>
				<td width="200">DATE</td>
			</tr>
		</table>
		<!-- rTable에 댓글 리스트만 Ajax로 처리위해 -->
		<table id="rTable">
			<c:forEach var="r" items="${rList}">
				<tr height="25" align="center">
					<td width="100">${r.r_id}</td>
					<td width="200">${r.r_contents}</td>
					<td width="200">${r.r_date}</td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
<script src ="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="../resources/js/jquery.serializeObject.js"></script>
<script type="text/javascript">
	
		function replyInsert(bnum) {
			var replyFrm = $("#rFrm").serializeObject();			
			replyFrm.r_bnum = bnum;//글번호 추가
			replyFrm.r_id = '${mb.m_id}';
		
			//객체에 데이터 추가! r_bnum은 무엇?
			console.log(replyFrm);
			$.ajax({
				url: "replyInsert",
				type: "post",
				data: replyFrm,
				dataType: "json",
				success: function(data) {
					console.log(data.rList);//map에 들어있는 key값으로 받아온 value값 확인!
					var rList = "";
					for(var i =0; i <data.rList.length;i++){
						rList += "<tr height='25' align='center'>"
									+"<td width='100'>"+data.rList[i].r_id+"</td>"
									+"<td width='200'>"+data.rList[i].r_contents+"</td>"
									+"<td width='200'>"+data.rList[i].r_date+"</td></tr>";				
									//form의 댓글에서 for문의 내용을 다 지우고 가져온 rList의 데이터를 사용해서 새로 작성!
						}
					$('#rTable').html(rList);//얘의 역할은...?
				},
			error: function (error) {
				alert(error);
				}
			})
			
			
		}

		function confirmDelete(bnum) {
				theForm=document.delBoard;
				//document.객체의 이름으로 저장 하나의 태그를 변수로 저장
				var chk = confirm("정말 삭제하시겠습니까?");
				if (chk) {
					return theForm.submit();
				}
			}	
		</script>
		}

</script>
</html>