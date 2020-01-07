<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style type="text/css">
.subject {
	text-align: center;
	height: 50px;
}
</style>
<script type="text/javascript">
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
				var chk = "${check}";
				if(chk == "fail"){
					alert("회원 가입 실패");
					location.reload(true);//화면을 다시 한번 불러오면서 check를 리셋함 
				}
			}
</script>
</head>
<body>
	<center>

		<h2>회원 가입</h2>
		<hr>
		<form name="joinFrm" action="memberInsert" method="post"
			onsubmit="return check()">
			<table border="1">
				<tr>
					<td width="100">ID</td>
					<td><input type="text" name="m_id" title="ID"></td>
				</tr>
				<tr>
					<td width="100">PWD</td>
					<td><input type="password" name="m_pwd" title="PWD"></td>
				</tr>
				<tr>
					<td width="100">NAME</td>
					<td><input type="text" name="m_name" title="NAME"></td>
				</tr>
				<tr>
					<td width="100">BIRTH</td>
					<td><input type="text" name="m_birth" title="BIRTH"></td>
				</tr>
				<tr>
					<td width="100">ADDR</td>
					<td><input type="text" name="m_addr" title="ADDR"></td>
				</tr>
				<tr>
					<td width="100">PHONE</td>
					<td><input type="text" name="m_phone" title="PHONE"></td>
				</tr>
				<tr>
					<td colspan="2" class="subject"><input type="submit"
						value="회원가입"></td>
				</tr>
			</table>
		</form>
	</center>


</body>
<script type="text/javascript">

		//<form onsubmit="return check()"> -- check의 return 값이 전송 된다.
		//JavaScript를 통한 required 처리
		function check(){		
		var frm = document.joinFrm;//form태그의 내용을 전부 가져오기
		var length = frm.length-1;//frm의 길이는 안에 있는 요소의 갯수 (input태그가 7개이므로)
												 //그리고 <input type="submit" 은 required 처리가 필요없으므로 1 빼줌
		
		for(var i = 0; i < length; i++){
			if(frm[i].value == "" || frm[i].value == null){
				alert(frm[i].title + "을 입력하세요!");
				frm[i].focus();
				return false;//action이 실행되지 않도록 false 반환
				}
				
			}
		return true;//모든 input태그의 내용이 입력이 다 되었을 경우.
		}
</script>
</html>















