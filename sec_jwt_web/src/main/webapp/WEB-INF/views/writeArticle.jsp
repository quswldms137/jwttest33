<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Write Article</h1>
<form name="frm" id="myForm">
	<input type="text" name="title" placeholder="Input title....."><br>
	<input type="text" name="content" placeholder="Input content....."><br>
	<input type="text" name="writer" placeholder="Input writer....."><br>
	<input type="file" name="file">
	<input type="button" value="기사등록" onclick="regArticle()"><br>
</form>
<hr>
<div id="demo">
	<!-- 여기에 get 데이터 넣기 -->
</div>
<hr>
<img src="http://localhost:8888/images/s_12f6e2c8-1bc1-4b59-9a58-88dd7d0e8785_math_img_2.jpg" />
<hr>

<button onclick="f()">가져오기</button>
<div id="demo2">
	<!-- 여기에 get 데이터 넣기 -->
</div>
<script>
var writer = localStorage.getItem('writer');
document.querySelector('input[name="writer"]').value = writer;

function f(){
	
	alert("f().......");
	const token = localStorage.getItem("jwtToken");
	const token_writer = localStorage.getItem("writer");
	const token_role = localStorage.getItem("role");
	const token_email = localStorage.getItem("email");
	
	const xhttp = new XMLHttpRequest();
	
	xhttp.onload = function() {
		const result = JSON.parse(this.responseText);
		console.log("result : " + result.title);
		console.log("response header(writer): " + xhttp.getResponseHeader("writer"))
		document.getElementById("demo2").innerHTML = result.title;
	}
	
	/*
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			// 요청이 완료되고 응답이 성공적으로 도착한 경우
			const result = JSON.parse(this.responseText);
			console.log("Response received: " + this.responseText);
			console.log("Response header (writer): " + this.getResponseHeader("Writer"));
			
			document.getElementById("demo2").innerHTML = result.title;
		} else if (this.readyState == 4) {
			// 요청이 완료되었지만 응답이 실패한 경우
			console.error("Request failed with status code: " + this.status);
		}
	};
	*/
	
	xhttp.open("GET", "http://localhost:8888/reporter/article/1");
	//xhttp.setRequestHeader("Content-type", "multipart/form-data");
	xhttp.setRequestHeader("Authorization", "Bearer " + token);
	xhttp.setRequestHeader("writer", token_writer);
	xhttp.setRequestHeader("role", token_role);
	xhttp.setRequestHeader("email", token_email);
	//xhttp.setRequestHeader("Access-Control-Expose-Headers", "Authorization, writer, Role");//위에 set한 header속성들을 폭로한다. 
	xhttp.send();
}

function regArticle() {
	const form = document.forms['frm'];
	var formData = new FormData(form);
	var params = new URLSearchParams();

    for (var pair of formData.entries()) {
        params.append(pair[0], pair[1]);
    }
	
    var serializedData = params.toString();
    console.log(serializedData);
	
	const token = localStorage.getItem("jwtToken");
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		const result = this.responseText;
		console.log("result : " + result);
		document.getElementById("demo").innerHTML = result;
		document.getElementById('myForm').reset();
		
	}
	xhttp.open("POST", "http://localhost:8888/reporter/article");
	//xhttp.setRequestHeader("Content-type", "multipart/form-data");
	xhttp.setRequestHeader("Authorization", "Bearer " + token);
	xhttp.send(formData);
}

</script>
</body>
</html>