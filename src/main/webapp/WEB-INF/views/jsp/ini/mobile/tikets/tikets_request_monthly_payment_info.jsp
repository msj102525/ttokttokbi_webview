<%@page import="com.jyes.www.common.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<c:set var="call_css_name" value="_style" />
<c:if test="${affiliates_code eq 'A0001' }">
<c:set var="call_css_name" value="_style_a" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>이용권 구매</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/payment_month<c:out value="${call_css_name }" />.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	var use_pay_codes = '<c:out value="${userUsePayVo.use_pay_codes }" />';//사용중인 이용권 코드
	var is_pay_rp = '<c:out value="${userUsePayVo.is_pay_rp }" />';//사용중인 이용권 정기결제 여부
	function setData() {
		var email = $("#email").val();
		var name = $("#buyername").val();
		var p_noti = $("#p_noti").val();
		p_noti += "&email=" + email;
		p_noti += "&name=" + name;
		$("#p_noti").val(p_noti);
		var date = new Date();
		var timestamp = date.getFullYear() + "" 
		+ (((date.getMonth()+1)<10)?"0"+(date.getMonth()+1):(date.getMonth()+1)) + "" 
		+ ((date.getDay()<10)?"0"+date.getDay():date.getDay()) + "" 
		+ ((date.getHours()<10)?"0"+date.getHours():date.getHours()) + "" 
		+ ((date.getMinutes()<10)?"0"+ date.getMinutes():date.getMinutes()) + "" 
		+ ((date.getSeconds()<10)?"0"+ date.getSeconds():date.getSeconds());
		/* var date_str = "oid_" + timestamp;
		if (date_str.length != 16) {
			for (i = date_str.length; i < 16; i++) {
				date_str = date_str + "0";
			}
		} */
		//O_ID 생성
		$.ajax({
            url:'/ini/mobile/INIBillPay/get_o_id',
            type:'post',
            async: false,
            success:function(data) {
            	document.ini.orderid.value = data;
            }
        });
		/* document.ini.orderid.value = date_str; */
		if(document.ini.orderid.value==""||document.ini.orderid.value=="0") {
			alter("결제 오류 orderid 누락");
			return;
		}
		document.ini.timestamp.value = "" + timestamp;
		var hashdata = "";
		$.ajax({
            url:'/ini/mobile/INIBillPay/hashdata',
            type:'post',
            data:$('#ini').serialize(),
            async: false,
            success:function(data) {
                hashdata = data;
            }
        });
		document.ini.hashdata.value = hashdata;
	}
	
	function on_web() {
		var order_form = document.ini;
		//UTF-8 인코딩
		for (var i = 0; i < order_form.elements.length; i++) {
			if ("p_noti" == order_form.elements[i].name) {
				order_form.elements[i].value += "&o_id=" + document.ini.orderid.value
				order_form.elements[i].value = encodeURIComponent(order_form.elements[i].value);
			}
		}
		order_form.action = "<%=Config.INI_HOST_INILITE%>/inibill/inibill_card.jsp";
		order_form.submit();
	}

	function onSubmit(code) {
		var email = $("#email").val();
		var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		var checkTestId = false;
		//테스트 아이디 체크
		$.ajax({
            url:'/ini/mobile/checkTestId',
            data:{id:'<c:out value="${id}" />'},
            type:'post',
            async: false,
            success:function(data) {
            	if(data>0) {
            		checkTestId = true;
            	}
            }
        });
		if(checkTestId) {
			alert("테스트 아이디는 등록 불가합니다.");
			return;
		}
		if(is_pay_rp=="Y") {
			//같은 정기결제 이용중 체크
			if(use_pay_codes.indexOf(code)>-1) {
				alert("현재 정기결제 상품 사용중입니다. 동일 상품 중복 가입은 불가합니다.");
			} else {
				if(code=="P0012"||code=="P0033"||code=="P0046") {
					alert("무선 정기결제 상품에서 유무선 정기결제 상품 변경시, 고객센터에 문의 부탁 드립니다.");
				} else if(code=="P0009"||code=="P0032"||code=="P0045") {
					alert("유무선 정기결제 상품에서 무선 정기결제 상품 변경시, 고객센터에 문의 부탁 드립니다.");
				} else {
					alert("결제 오류 고객센터에 문의 부탁 드립니다.");
				}
			}
			return;
		}
		if(""==email) {
			alert("정기 결제는 이메일을 입력해주셔야 이용 가능합니다.");
			return;
		}
		if(email.match(regExp)==null) {
			alert("잘못된 이메일 형식입니다.");
			return;
		}
		if(!$("#monthly_payment_info").is(":checked")) {
			alert('<c:out value="${fn:trim(payTypeVo.getName())}" />'+"에 동의해주세요.");
			return;
		}
		var app_store_type = $('#ini #store_type').val();
		if(app_store_type=="1") {
			if(code=="P0033") {
				window.web.callPayTicket("wireless_fixed_subscribe_ticket", code);
			} else if(code=="P0032") {
				window.web.callPayTicket("wireless_subscribe_ticket", code);
			} else if(code=="P0046") {
				window.web.callPayTicket("wireless_fixed_subscribe_ticket_01", code);
			} else if(code=="P0045") {
				window.web.callPayTicket("wireless_subscribe_ticket_01", code);
			}
		} else {
			setData();
			var order_form = document.ini;
			var inipaymobile_type = order_form.inipaymobile_type.value;
			if (inipaymobile_type == "web")
				return on_web();
		}
	}
</script>
</head>
<body>
	<form id="ini" name="ini" method="post">
		<input type="hidden" id="inipaymobile_type" name="inipaymobile_type" value="web" />
		<input type="hidden" id="mid" name="mid" value="<c:out value="${p_mid}" />" />
		<input type="hidden" id="buyername" name="buyername" value="<c:out value="${name}" />" />
		<input type="hidden" id="goodname" name="goodname" value="<c:out value="${payTypeVo.getName()}" />" />
		<input type="hidden" id="price" name="price" value="<c:out value="${payTypeVo.getPrice()}" />" />
		<input type="hidden" id="orderid" name="orderid" value="" />
		<input type="hidden" id="returnurl" name="returnurl" value="<c:out value="${host}" />/ini/mobile/INIBillPayReturn" />
		<input type="hidden" id="merchantreserved" name="merchantreserved" value="" />
		<input type="hidden" id="timestamp" name="timestamp" value="" />
		<input type="hidden" id="period_custom" name="period_custom" value="" />
		<input type="hidden" id="p_noti" name="p_noti" value="id=<c:out value="${id}" />&affiliates_code=<c:out value="${affiliates_code}" />&pay_type_code=<c:out value="${payTypeVo.getCode()}" />&sim=<c:out value="${sim}" />&googleadid=<c:out value="${googleadid}" />&pkgInstaller=<c:out value="${pkgInstaller}" />" />
		<input type="hidden" id="hashdata" name="hashdata" value="" />
		<input type="hidden" id="store_type" name="store_type" value="<c:out value="${app_store_type }" />" />
	</form>
	
	
	
	
	<div class="wrap">
		<div class="box2">
			
			<!-- 유무선 정기 결제 -->
	        <c:if test="${payTypeVo.getIs_pay_wl()=='Y' }">
	        <div class="title">
	             <p class="left_title">총 결제금액:</p>
	             <p class="right_tilte">월 <fmt:formatNumber value="${payTypeVo.getPrice() + 4400 }" groupingUsed="true"/>원(VAT포함)</p> 
	        </div>
	     	<div class="title">
	         	 <p class="left_title">유선 결제금액:</p>
	         	 <p class="right_tilte">월 <fmt:formatNumber value="${payTypeVo.getPrice() }" groupingUsed="true"/>원(VAT포함)</p> 
	     	</div>
	        <div class="title">
	             <p class="left_title">결제내역 안내 이메일 주소</p>
	             <input type="text" name="email" id="email" placeholder="이메일을 입력해주세요." value="<c:out value="${email}" />" />
	        </div>
	        <p style="margin-top: 5px;color: red;">※유무선 이용권의 경우, 정기결제 14,300원 이외에 KT 회선 청약이 필요하며 4,400원이 KT 회선으로 추가 부과됩니다.</p>  
	        </c:if>
	        
	        <!-- 무선 정기 결제 -->
	        <c:if test="${payTypeVo.getIs_pay_wl()!='Y' }">
			<div class="title">
				<p class="left_title">결제금액:</p>
				<p class="right_tilte">월 <fmt:formatNumber value="${payTypeVo.getPrice() }" groupingUsed="true"/>원(VAT포함)</p>
			</div>
			<div class="title">
	            <p class="left_title">결제내역 안내 이메일 주소</p>
	            <input type="text" name="email" id="email" placeholder="이메일을 입력해주세요." value="<c:out value="${email}" />" />
	        	<p class="redtxt"><br/>※무선 정기결제 구매 시 1회 맞춤형 콘텐츠 제작권 제공, 월 1회 수정 지원해드립니다.</p>
	        </div>
	        </c:if>
	        
		</div>
		
		<div class="box5">
		    <div class="title">
				<p class="left_title"><label><c:out value="${payTypeVo.getName() }" />에 동의합니다. <input type="checkbox" name="monthly_payment_info" id="monthly_payment_info" value="Y" /></label></p>
		    </div>
		</div>
		
		<div class="box3">
			<div class="btn_wrap">
				<a class="btn" href="javascript:void(0);" onclick="onSubmit('${payTypeVo.getCode()}');">결제하기</a>
			</div>
		</div>
		
	</div>
</body>
</html>