<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath }"/>
<html>
<head>
<meta charset="UTF-8">
<title>삭제 확인</title>
</head>
<body>
	<c:if test="${check > 0 }">
		<script>
			alert("삭제 되었습니다.");
			location.href="${root}/guest/write.do?pageNumber=${pageNumber}";
		</script>
	</c:if>
	<c:if test="${check == 0 }">
		<script>
			alert("삭제 되지 않았습니다.");
			location.href="${root}/guest/write.do?pageNumber=${pageNumber}";
		</script>
	</c:if>
	
</body>
</html>