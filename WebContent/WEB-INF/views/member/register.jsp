<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="${root}/css/member/register.css" />
<script src="${root}/javascript/member/register.js"></script>
</head>
<body>
	<div class="container">
		<div>
			<p>
				<strong>회원가입(*필수입력사항입니다.)</strong>
			</p>
		</div>
		<div>
			<form action="${root}/member/registerOk.do" method="post" onsubmit="return regusterForm(this)">
				<div class="item">
					<div>아이디</div>
					<div>
						*<input type="text" name="id"/> <input type="button" value="아이디중복" name="idcheck"/>
					</div>
				</div>
				<div class="item">
					<div>비밀번호</div>
					<div>
						*<input type="password" name="pwd"/>
					</div>
				</div>
				<div class="item">
					<div>비밀번호 확인</div>
					<div>
						*<input type="password" name="pwdcheck" />
					</div>
				</div>
				<div class="item">
					<div>이름</div>
					<div>
						*<input type="text" name="name" />
					</div>
				</div>
				<div class="item">
					<div>주민번호</div>
					<div>
						*<input type="text" name="jumin1" />-<input type="text" name="jumin2" />
					</div>
				</div>
				<div class="item">
					<div>이메일</div>
					<div>
						<input type="email" name="email" />
					</div>
				</div>
				<div class="item">
					<div>우편번호</div>
					<div>
						<input type="text" name="zipcode" /> <input type="button" value="우편번호검색" />
					</div>
				</div>
				<div class="item">
					<div>주소</div>
					<div>
						<input type="text" name="address" style="width: 80%;" />
					</div>
				</div>
				<div class="item">
					<div>직업</div>
					<div>
						<select name="job">
							<option value="백수">백수</option>
							<option value="학생">학생</option>
							<option value="직장인">직장인</option>
							<option value="건물주">건물주</option>
						</select>
					</div>
				</div>
				<div class="item">
					<div>메일수신</div>
					<div>
						<input type="radio" name="mailing" value="yes" /> yes <input type="radio" name="mailing" value="no" /> no
					</div>
				</div>
				<div class="item" style="border-bottom: 2px solid black;">
					<div>관심분야</div>
					<div>
						<input type="checkbox" name="interest" value="경제" id="ch1" /> <label for="ch1">경제</label> 
						<input type="checkbox" name="interest" value="IT" id="ch2" /> <label for="ch2">IT</label>
						<input type="checkbox" name="interest" value="음악" id="ch3" /> <label for="ch3">음악</label>
						<input type="checkbox" name="interest" value="미술" id="ch4" /> <label for="ch4">미술</label>
					</div>
				</div>
				<div class="item-button">
					<input type="submit" value="가입" /> <input type="reset" value="취소" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>