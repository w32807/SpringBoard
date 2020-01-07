<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<style type="text/css">
         table{
            width: 600px;
         }
         table, td, th{
            border: 1px solid;
            border-collapse: collapse;
            padding: 5px;
         }
         input[type='text']{
            width: 98%
         }
         textarea{
            width: 98%;
            resize: none;
         }
         th{
            background-color: royalblue;
         }
      </style>
      <script type="text/javascript">
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
				var chk = ${check};
				if(chk == 1){
					alert("글 등록 실패");
				}
			}
      </script>
</head>
<body>
	<center>
		<h3>글쓰기</h3>
		<hr>
		<form action="boardWrite" method="post" enctype="multipart/form-data">

			<table>

				<tr>

					<th>작성자</th>

					<td><input type="text" name="bid" required></td>

				</tr>

				<tr>

					<th>제목</th>

					<td><input type="text" name="btitle" required></td>

				</tr>

				<tr>

					<th>내용</th>

					<td><textarea name="bcontents" rows="20">

  </textarea></td>

				</tr>

				<tr>

					<th>파일첨부</th>

					<td><input type="file" name="files" id="files"
						onchange="fileChk(this)" multiple="multiple" >
						<input type="file" name="files" id="files"
						onchange="fileChk(this)" multiple="multiple" >
						<!-- 파일에 뭔가 들어가면, onchange라는 이벤트가 발생 되고..그 때 fileChk(this)라는 함수를 실행해라. 
								this의 의미는 여기 안에 있는 fileChk를 가리킴-->
						 <input type="hidden" 	id="filecheck" value="0" name="fileCheck"></td>

				</tr>

				<tr>

					<td colspan="2" align="center"><input type="submit"
						value="글작성"> <input type="reset" value="취소"> <input
						type="button" value="돌아가기" onclick="location.href='./list'">

					</td>

				</tr>

			</table>

		</form>

	</center>
</body>
<script src ="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	//파일 입력이 되었을 때, > <input type="hidden" id="filecheck" value="0" name="fileCheck">의 value 값을 1로 바꿔주자.
	function fileChk(elem) {
		console.log(elem);
		console.dir(elem.value);
		
		if(elem.value == ""){//value에 파일이 들어가고, 파일이 없으면...
			console.log("empty");
			$("#filecheck").val(0); //위의 input hidden의 value
			                                 //fileCheck 아이디를 가지는 태그의 값을 0으로 넣자.
		}
		else{//value에 파일이 들어가고, 파일이 있으면...
			console.log("not empty");
			$("#filecheck").val(1);//위의 input hidden의 value
		}
	}

</script>
</html>