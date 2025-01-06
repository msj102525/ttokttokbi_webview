<%@page import="com.jyes.www.common.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<c:set var="representation_number" value="<%=Config.REPRESENTATION_NUMBER %>" />
<c:if test="${affiliates_code eq 'A0001' }">
<c:set var="representation_number" value="<%=Config.REPRESENTATION_NUMBER_SMATRO %>" />
</c:if>
<c:set var="representation_number" value="${fn:substring(representation_number,0,3)}-${fn:substring(representation_number,3,7)} - ${fn:substring(representation_number,7,11)}" />
<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>이용권 구매</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/payment_style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
var use_pay_codes = '<c:out value="${userUsePayVo.use_pay_codes }" />';//사용중인 이용권 코드
var is_pay_rp = '<c:out value="${userUsePayVo.is_pay_rp }" />';//사용중인 이용권 정기결제 여부
//자바스크립트 부분
function payInfoMsg(code) {
	var name = $("#name").val();
	var payName = $("#payName").val();
	var price = $("#price").val();
	if(is_pay_rp=="Y") {
		alert("현재 정기결제 상품 사용중입니다. 정기결제 해지 후, 일반결제 구매가 가능하십니다.");
		return;
	}
	price = numberWithCommas(price);
	var msg = "[JSAM] "+name+" 회원님의 신청정보입니다.\n상품명 : 무선 "+payName+"\n금액 : "+price+"원\n입금계좌 : 우리은행 1005-801-829858\n예금주 : 제이예스 주식회사";
	window.web.payInfoMsg(msg);
}
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
</script>
</head>
<body>
	<input type="hidden" id="name" name="name" value="<c:out value="${name }" />" />
	<input type="hidden" id="payName" name="payName" value="<c:out value="${payTypeVo.getName() }" />" />
	<input type="hidden" id="price" name="price" value="<c:out value="${payTypeVo.getPrice() }" />" />
	<div class="wrap">
		<%-- <c:if test="${affiliates_code eq 'A0001' }"> --%>
		<div class="box1">
			<div class="title_first">
				<p class="left_title">무통장 입금 안내</p>
			</div>
		</div>
		<%-- </c:if> --%>
		<p style="line-height: 18px;">해당상품은 무통장입금만 가능하며 입금후 영업일 기준 2일 이내 승인되어 사용하실 수 있습니다. <br/><br/>
		구매 입금 시 입금자명에 고객님 성함을 입력해주시기 바랍니다.<br/>입금 완료 후 JSAM 카카오 플러스로 입금자명, 핸드폰 번호를 기재해주시면 빠른 확인과 승인이 가능합니다.<br/> 궁금하신 점이 있으시면 카카오 플러스로 문의주시기 바랍니다.</p> <br/>
		<p style="line-height: 18px;">[전자세금계산서 발행]<br/>
		- 입금완료 후 사업자등록증 사본(이메일 첨부)을 고객센터를 통해 전달해 주셔야 합니다.<br/>
		(입금일 기준 익월 10일 이내 요청시 발행가능)</p>
		<div class="box2">
	        <div class="title">
	         	<p class="left_title">신청 상품:</p>
	         	<p class="right_tilte">무선 <c:out value="${payTypeVo.getName() }" /></p>
	        </div>
			<div class="title">
				<p class="left_title">입금 금액:</p>
				<p class="right_tilte"><fmt:formatNumber value="${payTypeVo.getPrice() }" groupingUsed="true"/>원(VAT포함)</p>
			</div>
			<div class="title">
				<p class="left_title">은행:</p>
				<p class="right_tilte">
					<img src="<%=path%>/images/logo.png" />우리은행
				</p>
			</div>
			<div class="title">
				<p class="left_title">계좌번호:</p>
				<p class="right_tilte">1005-801-829858</p>
			</div>
			<div class="title">
				<p class="left_title">예금주:</p>
				<p class="right_tilte">제이예스 주식회사</p>
			</div>
		</div>
		<div class="box3">
			<div class="btn_white_wrap">
				<a class="btn_white" href="javascript:void(0);" onclick="javascript:payInfoMsg('${payTypeVo.getCode()}');">결제정보 문자로 보내기</a>
			</div>
			<div class="btn_white_wrap" style="display: none;">
				<a class="btn_white1" href="javascript:void(0);" onclick="window.web.payFirstUrlMove();">닫기</a>
			</div>
		</div>
	</div>
</body>
</html>