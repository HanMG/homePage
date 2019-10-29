<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath }"/>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
<link rel="stylesheet" href="${root}/css/guest/write.css" />
</head>
<body>
	<jsp:include page="../../../index.jsp"/>
	<div id="container">
		<form action="${root }/guest/writeOk.do" method="get" class="box">
			<div class="title">
				<span>이름</span>
				<input type="text" name="irum" size="12"/>
				<span>비밀번호</span>
				<input type="password" name="pwd" size="12" />
			</div>
			
			<div class="content">
				<textarea name="message" cols="53" rows="5"></textarea>
			</div>
			
			<div class="title" style="text-align: right; padding-right: 8px;">
				<input type="submit" value="확인" />
				<input type="reset" value="취소"/>
			</div>
		</form>
	</div>
</body>
</html>