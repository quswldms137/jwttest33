<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Index</h1>
<hr>
<button onclick="loadDoc()">Get 요청</button>
<div id="demo"></div>
<hr>
<h2>Join</h2>
<form name="frm_join">
	<input type="text" name="username" placeholder="Input username....."><br>
	<input type="password" name="password" placeholder="Input password....."><br>
	<input type="text" name="name" placeholder="Input name....."><br>
	<input type="text" name="email" placeholder="Input email....."><br>
	<input type="button" onclick="join()" value="기자등록">
</form>
<div id="demo4"></div>
<hr>
<h2>Login</h2>
<form name="frm">
	<input type="text" name="username" placeholder="Input username....."><br>
	<input type="password" name="password" placeholder="Input password....."><br>
	<input type="button" onclick="login()" value="로그인">
</form>
<div id="demo2"></div>
<div id="demo3"></div>
<script>
	function loadDoc() {
		const token = localStorage.getItem("jwtToken");
		
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
			document.getElementById("demo").innerHTML = this.responseText;
		}
		xhttp.open("GET", "http://localhost:8888/api/articles");
		xhttp.setRequestHeader("Authorization", "Bearer " + token);
		xhttp.send();
	}
	//회원가입
	function join(){
		const form = document.forms['frm_join'];
		var formData = new FormData(form);
		var params = new URLSearchParams();

	    for (var pair of formData.entries()) {
	        params.append(pair[0], pair[1], pair[2], pair[3]);
	    }
		
	    var serializedData = params.toString();
	    console.log(serializedData);
	    
	    const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
			document.getElementById("demo4").innerHTML = this.responseText;
		}
		xhttp.open("POST", "http://localhost:8888/join");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(serializedData);
	    
	}
	//로그인
	function login() {
		const form = document.forms['frm'];
		var formData = new FormData(form);
		var params = new URLSearchParams();

	    for (var pair of formData.entries()) {
	        params.append(pair[0], pair[1]);
	    }
		
	    var serializedData = params.toString();
	    console.log(serializedData);
		
		//const formData = $(form).serialize();
		
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
			var headers = xhttp.getAllResponseHeaders();
			console.log("headers : " + headers);
			var token = xhttp.getResponseHeader("Authorization");
			console.log("JWT Token: " + token.split(" ")[1]);
			document.getElementById("demo2").innerHTML = token.split(" ")[1];
			
			localStorage.setItem("jwtToken", token.split(" ")[1]);
			// 헤더 값 읽기
			let role = xhttp.getResponseHeader("Role");
			let username = xhttp.getResponseHeader("writer");
			let email = xhttp.getResponseHeader("Email");
			localStorage.setItem("role", role);
			localStorage.setItem("writer", username);
			localStorage.setItem("email", email);
			
			console.log("role : " + role);
			console.log("writer : " + username);
			console.log("email : " + email);
			
			if(role == 'ROLE_REPORTER'){
				document.getElementById("demo3").innerHTML = "<button onclick='writeArticle()'>기사쓰기</button>";
			}else if(role == 'ROLE_MANAGER'){
				document.getElementById("demo3").innerHTML = "<button onclick='requestManager()'>관리자페이지</button>";
			}
		}
		xhttp.open("POST", "http://localhost:8888/login");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(serializedData);
	}
	//기사작성 페이지로 이동
	function writeArticle(){
		alert("write Article....");
		location.href="/writeArticle";
	}
</script>
</body>
</html>