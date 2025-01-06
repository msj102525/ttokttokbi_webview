<%@page import="com.jyes.www.common.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<c:set var="call_css_name" value="" />
<c:if test="${affiliates_code eq 'A0001' }">
<c:set var="call_css_name" value="_a" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>이용권 구매</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style<c:out value="${call_css_name }" />.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	var sim = '<c:out value="${sim }" />';//사용중인 이용권 정기결제 여부
	var googleadid = '<c:out value="${googleadid }" />';//사용중인 이용권 정기결제 여부
	var pkgInstaller = '<c:out value="${pkgInstaller }" />';//설치위치 패키지명
	function on_web() {
		var order_form = document.ini;
		var paymethod = order_form.paymethod.value;
		//UTF-8 인코딩
		for (var i = 0; i < order_form.elements.length; i++) {
			if ("P_NOTI_TEMP" == order_form.elements[i].name) {
				order_form.P_NOTI.value = encodeURIComponent(order_form.elements[i].value)
			}
		}
		order_form.target = "_self";
		order_form.action = "<%=Config.INI_HOST_MOBILETX%>/smart/" + paymethod + "/";
		order_form.submit();
	}

	function onSubmit() {
		var order_form = document.ini;
		var inipaymobile_type = order_form.inipaymobile_type.value;
		if (inipaymobile_type == "web")
			return on_web();
	}
</script>
<script type="text/javascript">
	/**
	 * pGoods:상품명(필수)
	 * pAmt:가격(필수)
	 * pUname:구매자이름(필수)
	 * pMname:상점이름(선택)
	 * pMobile:휴대폰번호(선택)
	 * 이메일주소(선택)
	 */
	function setRequestInfo() {
		var pGoods = (arguments[0] == null) ? "" : arguments[0];
		var pAmt = (arguments[1] == null) ? "" : arguments[1];
		var pMobile = (arguments[2] == null) ? "" : arguments[2];
		var pEmail = (arguments[3] == null) ? "" : arguments[3];
		if (pGoods != "") {
			document.ini.P_GOODS.value = pGoods;
		}
		if (pAmt != "") {
			document.ini.P_AMT.value = pAmt;
		}
		if (pMobile != "") {
			document.ini.P_MOBILE.value = pMobile;
		}
		if (pEmail != "") {
			document.ini.P_EMAIL.value = pEmail;
		}
	}
	function selectPay() {
		var pGoods = (arguments[0] == null) ? "" : arguments[0];
		var pAmt = (arguments[1] == null) ? "" : arguments[1];
		var pCode = (arguments[2] == null) ? "" : arguments[2];
		var app_store_type = $('#form1 #store_type').val();
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
		if(app_store_type=="1") {
			if(pCode=="P0007"||pCode=="P0034") {
			// if(pCode=="P0007"||pCode=="P0034"||pCode=="P0044") {
				window.web.callPayTicket("custom_contents_ticket", pCode);
			}
		} else {
			var order_form = document.ini;
			order_form.P_NOTI.value = "";
			/**
			 * pGoods:상품명(필수)
			 * pAmt:가격(필수)
			 * pMobile:휴대폰번호(선택)
			 * 이메일주소(선택)
			 */
			setRequestInfo(pGoods, pAmt, '', '');
			//O_ID 생성
			$.ajax({
	            url:'/ini/mobile/INIBillPay/get_o_id',
	            type:'post',
	            async: false,
	            success:function(data) {
	        		order_form.P_OID.value = data;
	            }
	        });
			if(order_form.P_OID.value==""||order_form.P_OID.value=="0") {
				alter("결제 오류 o_id 누락");
				return;
			}
			try {
				if (document.ini.P_UNAME.value == ""
						|| document.ini.P_NOTI_TEMP.value.split("&")[0].split("=")[1] == ""
						|| document.ini.P_NOTI_TEMP.value.split("&")[1].split("=")[1] == "") {
					alert("구매 오류");
					return;
				} else {
					document.ini.P_NOTI_TEMP.value += "&pay_type_code=" + pCode;
					document.ini.P_NOTI_TEMP.value += "&o_id=" + order_form.P_OID.value+"&sim="+sim+"&googleadid="+googleadid+"&pkgInstaller="+pkgInstaller;
				}
			} catch (e) {
				alert("구매 오류:" + e.message);
				return;
			}
			onSubmit();
		}
	}
	$(function() {
		document.querySelector('#toggle-button').addEventListener('click', function() {
			if ($(".up").css("display")=="none") {
				$(".up").show();
				$(".down").hide();
				$(".section.collapsible").hide();
			} else {
				$(".up").hide();
				$(".down").show();
				$(".section.collapsible").show();
			}
			//$(".up").toggle();
		});
	});
	function googlePaycall() {
		var code = (arguments[0] == null) ? "" : arguments[0];
		selectPay('이미지 제작','44000','P0034');
	}
</script>
</head>
<body>
	<form name="ini" id="form1" method="post" accept-charset="euc-kr">
		<input type="hidden" id="P_OID" name="P_OID" value="" />
		<input type="hidden" id="P_GOODS" name="P_GOODS" value="" />
		<input type="hidden" id="P_AMT" name="P_AMT" value="" />
		<input type="hidden" id="P_MOBILE" name="P_MOBILE" value="" />
		<input type="hidden" id="P_EMAIL" name="P_EMAIL" value="" />
		<input type="hidden" id="P_NOTI" name="P_NOTI" value="" />
		<input type="hidden" id="P_UNAME" name="P_UNAME" value="<c:out value="${name}" />" />
		<input type="hidden" id="P_NOTI_TEMP" name="P_NOTI_TEMP" value="id=<c:out value="${id}" />&affiliates_code=<c:out value="${affiliates_code}" />" />
		<input type="hidden" id="P_MID" name="P_MID" value="<c:out value="${p_mid}" />" />
		<input type="hidden" id="P_NEXT_URL" name="P_NEXT_URL" value="<c:out value="${host}" />/ini/mobile/INIStdPayReturn" />
		<input type="hidden" id="inipaymobile_type" name="inipaymobile_type" value="web" />
		<input type="hidden" id="paymethod" name="paymethod" value="wcard" />
		<input type="hidden" id="P_HPP_METHOD" name="P_HPP_METHOD" value="1" />
		<input type="hidden" id="P_CHARSET" name="P_CHARSET" value="utf8" />
		<input type="hidden" id="P_RESERVED" name="P_RESERVED" value="twotrs_isp=Y&block_isp=Y&twotrs_isp_noti=N&apprun_check=Y" />
		<input type="hidden" id="store_type" name="store_type" value="<c:out value="${app_store_type }" />" />
	</form>
	<div class="wrap">
		<div class="box1">
			<div class="title">
				<p class="left_title">맞춤형 컨텐츠</p>
			</div>
			<p style="line-height: 18px; word-break: keep-all; padding-bottom: 20px; font-size: 14px;">맞춤형 컨텐츠 제작은 고객들의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등 고객 맞춤 이미지 제작을 전문 디자이너가 직접 작업해드립니다.</p>
			<div class="box_pay">
				<div class="box5">
					<div class="title">
						<p class="left_title">맞춤형 컨텐츠 제작 구매</p> 
					</div>
				</div>
				<div class="pay_div2"><p class="price">44,000원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
				<div class="btn_wrap right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="selectPay('맞춤형 컨텐츠 제작','44000','P0034');">구매</a></div>
				<p style="word-break: keep-all; color:#ff0000; padding-top:12px;">맞춤형 컨텐츠 제작 첫 구매 시 44,000원에 제작권을 구매 하실 수 있으며 맞춤형 컨텐츠 제작 재구매 시 25%할인 된 가격인 33,000원에 제작권을 구매 하실 수 있습니다.</p>
				<div class="btn_wrap2"></div><!--200131-->
        	</div>
		</div>
		<div class="container">
			<div class="section">
				<p style="font-size: 14px;">맞춤형 컨텐츠 제작 구매 이용안내</p>
				<button id="toggle-button" style="outline: 0;">
					<img src="<%=path%>/img/arrow.png" class="up"/>
					<img src="<%=path%>/img/close_arrow.png" class="down" style="display: none;"/>
				</button>
			</div>
			<div class="section collapsible" style="display: none;">
				<p>
					* 맞춤형 컨텐츠 제작 구매 후 14일 이내 최대 2회까지 수정 가능합니다. 단, 제작 의뢰 이미지와 전혀 다른 신규 이미지로 수정 요청 시 별도 제작권 구매 후 요청<br/><br/>
					* 맞춤형 컨텐츠 제작 재구매는 수정 2회를 모두 이용한 고객님께 25% 할인 된 가격으로 판매되는 제작권입니다.<br/><br/>
					* 사용기간은 구매일로부터 1년이며 사용기간 내 미 사용 시 80%금액 환불 가능합니다.
				</p>
			</div>
		</div>
	</div>
	<c:if test="${app_version ne ''}">
	<div class="btn_wrap">
		<a class="btn2" href="javascript:void(0);" onclick="window.web.moveToMakePersonalRequest('N','맞춤형 컨텐츠 제작 구매를 하신 후 이미지 제작 요청을 해주시기 바랍니다.','P0034');">맞춤형 컨텐츠 제작 요청</a>
	</div>
	</c:if>
</body>
</html>