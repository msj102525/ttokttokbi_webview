<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" /> 
</head>
<body>
	<script>
		var actioin_type = -1;
		<c:choose>
		<c:when test="${returnValue eq '1'}">
		alert("결제 성공");
		actioin_type = 1;
		</c:when>
		<c:when test="${returnValue eq '0'}">
		//alert("결제 실패");
		//alert('<c:out value="${code}" />(<c:out value="${msg}" />)');
		alert('<c:out value="${msg}" />');
		</c:when>
		<c:otherwise>
		//alert("결제 취소");
		//alert('<c:out value="${code}" />(<c:out value="${msg}" />)');
		alert('<c:out value="${msg}" />');
		</c:otherwise>
		</c:choose>
		if(actioin_type==1) {
			window.web.paySucess();
		} else {
			window.web.payFirstUrlMove();
		}
	</script>
</body>
</html>