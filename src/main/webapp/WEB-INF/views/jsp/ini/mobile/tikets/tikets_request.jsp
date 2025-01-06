﻿﻿﻿﻿﻿<%@page import="com.jyes.www.common.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<c:set var="call_css_name" value="_style" />
<c:set var="representation_number" value="<%=Config.REPRESENTATION_NUMBER %>" />
<c:if test="${affiliates_code eq 'A0001' }">
<c:set var="call_css_name" value="_style_a" />
<c:set var="representation_number" value="<%=Config.REPRESENTATION_NUMBER_SMATRO %>" />
</c:if>
<c:set var="representation_number" value="${fn:substring(representation_number,0,3)}-${fn:substring(representation_number,3,7)} - ${fn:substring(representation_number,7,11)}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>이용권 구매</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/pay<c:out value="${call_css_name }" />.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	var use_pay_codes = '<c:out value="${userUsePayVo.use_pay_codes }" />';//사용중인 이용권 코드
	var is_pay_rp = '<c:out value="${userUsePayVo.is_pay_rp }" />';//사용중인 이용권 정기결제 여부
	var sim = '<c:out value="${sim }" />';//사용중인 이용권 정기결제 여부
	var googleadid = '<c:out value="${googleadid }" />';//사용중인 이용권 정기결제 여부
	var pkgInstaller = '<c:out value="${pkgInstaller }" />';//설치위치 패키지명
	var app_version = '<c:out value="${app_version }" />';//app 버전
	$(function() {
		/* var offset = $('#move_<c:out value="${code }"/>').offset();
        $('html, body').animate({scrollTop : offset.top}, 400); */
	});
	
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
		var app_store_type = $('#move_mmpi #store_type').val();
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
			alert("현재 정기결제 상품 사용중입니다. 정기결제 해지 후, 일반결제 구매가 가능하십니다.");
			return;
		}
		if(app_store_type=="1") {
			//1개월 이용권
			if(pCode=="P0028") {
				window.web.callPayTicket("wireless_one_month_ticket", pCode);
			//3개월 이용권
			} else if(pCode=="P0029") {
				window.web.callPayTicket("wireless_three_months_ticket", pCode);
			}
			return;
		}
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
	
	function moveDepositInfo() {
		var code = (arguments[0] == null) ? "" : arguments[0];
		var name = $('#move_mmpi #name').val();
		var id = $('#move_mmpi #id').val();
		var affiliates_code = $('#move_mmpi #affiliates_code').val();
		var email = $('#move_mmpi #email').val();
		var store_type = $('#move_mmpi #store_type').val();
		if(is_pay_rp=="Y") {
			alert("현재 정기결제 상품 사용중입니다. 정기결제 해지 후, 일반결제 구매가 가능하십니다.");
			return;
		}
		if(store_type=="1") {
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
			//6개월 이용권
			if(code=="P0030") {
				window.web.callPayTicket("wireless_six_months_ticket", code);
			//12개월 이용권
			} else if(code=="P0031") {
				window.web.callPayTicket("wireless_one_year_ticket", code);
			}
			return;
		}
		location.href = "/pay/tiketsRequestDepositInfo?code="+code+"&name="+name+"&id="+id+"&affiliates_code="+affiliates_code+"&email="+email+"&store_type="+store_type;
	}
	function moveMonthlyPaymentInfo() {
		var code = (arguments[0] == null) ? "" : arguments[0];
		//정기결제 이용중 체크
		if(is_pay_rp=="Y") {
			//같은 정기결제 이용중 체크
			if(use_pay_codes.indexOf(code)>-1) {
				alert("현재 정기결제 상품 사용중입니다. 동일 상품 중복 가입은 불가합니다.");
			} else {
				if(code="P0012"||code=="P0033"||code=="P0046") {
					alert("무선 정기결제 상품에서 유무선 정기결제 상품 변경시, 고객센터에 문의 부탁 드립니다.");
				} else if(code="P0009"||code=="P0032"||code=="P0045") {
					alert("유무선 정기결제 상품에서 무선 정기결제 상품 변경시, 고객센터에 문의 부탁 드립니다.");
				} else {
					alert("결제 오류 고객센터에 문의 부탁 드립니다.");
				}
			}
			return;
		}
		$("#code").val(code);
		$('#move_mmpi').attr({action:"/ini/mobile/tikets/tiketsRequestMonthlyPaymentInfo", method:'get'}).submit();
	}
	function googlePaycall() {
		var code = (arguments[0] == null) ? "" : arguments[0];
		moveMonthlyPaymentInfo(code);
	}
	function showMsg() {
		var code = (arguments[0] == null) ? "" : arguments[0];
		//버전 구분
		if(app_version!="") {
			window.web.showFixedMontlyDescDialog(code);
			return;
		} else {
			alert("유무선 정기결제 구매 시 안내사항입니다.\n\n·유선 자동문자 발송 시, 등록하신 휴대폰 번호로 발송됩니다.\n·인터넷 전화의 경우, 현재 KT 정책에 의해 당분간 가입이 불가능합니다.");
			moveMonthlyPaymentInfo(code);
			return;
		}
	}
	function showProductInfoMsg() {
		var code = (arguments[0] == null) ? "" : arguments[0];
		var msg = "상품 정보 오류";
		if(app_version!="") {
			//12개월
			if("P0031" == code) {
				msg = "무선 12개월 이용권은 기존 196,000원에서 55% 할인된 가격인 88,000원의 저렴한 가격으로 이용하실 수 있습니다.<br/>기본으로 제공하는 서비스는 무선 수신/발신 자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>결제 시, 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 2회 수정 가능</div>";
			//무선 정기 결제
			} else if("P0032" == code) {
				msg = "무선 정기결제 이용권은 기존 19,800원에서 50%할인 된 가격인 9,900원에 저렴한 가격으로 이용하실 수 있습니다.<br/>기본으로 제공하는 서비스는 무선 수신/발신 자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>계정당, 최초 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			//유무선 정기 결제	
			} else if("P0033" == code) {
				msg = "유무선 정기결제 이용권은 기존 25,400원에서 39% 할인된 가격인 15,400원의 저렴한 가격으로 이용하실 수 있습니다.<br/>(KT 회선 청약 4,400원 별도 청구)<br/>기본으로 제공하는 서비스는 유선 및 무선에 대한 수신/발신<br/>자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>계정당, 최초 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			//신규 무선 정기 결제
			} else if("P0045" == code) {
				msg = "무선 정기결제 이용권은 기존 19,800원에서 56%할인 된 가격인 8,800원에 저렴한 가격으로 이용하실 수 있습니다.<br/>기본으로 제공하는 서비스는 무선 수신/발신 자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>계정당, 최초 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			//신규 유무선 정기 결제	
			} else if("P0046" == code) {
				msg = "유무선 정기결제 이용권은 기존 25,400원에서 44% 할인된 가격인 14,300원의 저렴한 가격으로 이용하실 수 있습니다.<br/>(KT 회선 청약 4,400원 별도 청구)<br/>기본으로 제공하는 서비스는 유선 및 무선에 대한 수신/발신<br/>자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>계정당, 최초 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			}
			window.web.showHtmlDialog(msg);
		} else {
			//12개월
			if("P0031" == code) {
				msg = "무선 12개월 이용권은 기존 196,000원에서 55%할인 된 가격인 88,000원에 저렴한 가격으로 이용하실 수 있습니다.\n기본으로 제공하는 서비스는 무선 수신/발신 자동문자 발송, 단체문자 발송을 제공하고 있습니다.\n특가 상품 혜택으로는 결제 시, 1회 맞춤형 콘텐츠 제작권을 드리고 있습니다.\n\n※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.\n※맞춤형 콘텐츠 제작 후, 14일 이내 2회 수정 가능";
			//무선 정기 결제
			} else if("P0032" == code) {
				msg = "무선 정기결제 이용권은 기존 19,800원에서 50%할인 된 가격인 9,900원에 저렴한 가격으로 이용하실 수 있습니다.\n기본으로 제공하는 서비스는 무선 수신/발신 자동문자 발송, 단체문자 발송을 제공하고 있습니다.\n특가 상품 혜택으로는 계정당, 최초 1회 맞춤형 콘텐츠 제작권을 드리고 있습니다.\n\n※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.\n※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			//유무선 정기 결제	
			} else if("P0033" == code) {
				msg = "유무선 정기결제 이용권은 기존 25,400원에서 39% 할인된 가격인 15,400원의 저렴한 가격으로 이용하실 수 있습니다.\n(KT 회선 청약 4,400원 별도 청구)\n기본으로 제공하는 서비스는 유선 및 무선에 대한 수신/발신\n자동문자 발송, 단체문자 발송을 제공하고 있습니다.\n특가 상품 혜택으로는 계정당, 최초 1회 맞춤형 콘텐츠 제작권을 드리고 있습니다.\n\n※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.\n※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			//신규 무선 정기 결제
			} else if("P0045" == code) {
				msg = "무선 정기결제 이용권은 기존 19,800원에서 56%할인 된 가격인 8,800원에 저렴한 가격으로 이용하실 수 있습니다.<br/>기본으로 제공하는 서비스는 무선 수신/발신 자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>계정당, 최초 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			//신규 유무선 정기 결제	
			} else if("P0046" == code) {
				msg = "유무선 정기결제 이용권은 기존 25,400원에서 44% 할인된 가격인 14,300원의 저렴한 가격으로 이용하실 수 있습니다.<br/>(KT 회선 청약 4,400원 별도 청구)<br/>기본으로 제공하는 서비스는 유선 및 무선에 대한 수신/발신<br/>자동문자 발송, 단체문자 발송을 제공하고 있습니다.<br/>특가 상품 혜택으로는 <font color='red'>계정당, 최초 1회 맞춤형 콘텐츠 제작권</font>을 드리고 있습니다.<br/><br/>※ 맞춤형 콘텐츠란? 고객의 업종에 맞게 홍보 전단지, 명함, 모바일 홈페이지 등에 사용할 수 있는 고객 맞춤 이미지를 전문 디자이너가 직접 제작해드리는 서비스 입니다.<br/>※ 맞춤형 콘텐츠 제작 후, 14일 이내 1회 수정 가능";
			}
			alert(msg);
		}
	}
	
    $(function () {
        $('.tabcontent > div').hide();
        $('.tabnav a').click(function () {
        	Detail(false);
            $('.tabcontent > div').hide().filter(this.hash).fadeIn();
            $('.tabnav a').removeClass('active');
            $(this).addClass('active');
            return false;
        }).filter(':eq(0)').click();
    });
    
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
		<input type="hidden" id="P_NOTI_TEMP" name="P_NOTI_TEMP" value="id=<c:out value="${id}" />&affiliates_code=<c:out value="${affiliates_code}" />&name=<c:out value="${name}" />&email=<c:out value="${email}" />" />
		<input type="hidden" id="P_MID" name="P_MID" value="<c:out value="${p_mid}" />" />
		<input type="hidden" id="P_NEXT_URL" name="P_NEXT_URL" value="<c:out value="${host}" />/ini/mobile/INIStdPayReturn" />
		<input type="hidden" id="inipaymobile_type" name="inipaymobile_type" value="web" />
		<input type="hidden" id="paymethod" name="paymethod" value="wcard" />
		<input type="hidden" id="P_HPP_METHOD" name="P_HPP_METHOD" value="1" />
		<input type="hidden" id="P_CHARSET" name="P_CHARSET" value="utf8" />
		<input type="hidden" id="P_RESERVED" name="P_RESERVED" value="twotrs_isp=Y&block_isp=Y&twotrs_isp_noti=N&apprun_check=Y" />
	</form>
	<form name="move_mmpi" id="move_mmpi" method="get">
		<input type="hidden" id="name" name="name" value="<c:out value="${name}" />" />
		<input type="hidden" id="id" name="id" value="<c:out value="${id}" />" />
		<input type="hidden" id="affiliates_code" name="affiliates_code" value="<c:out value="${affiliates_code}" />" />
		<input type="hidden" id="email" name="email" value="<c:out value="${email}" />" />
		<input type="hidden" id="code" name="code" value="" />
		<input type="hidden" id="sim" name="sim" value="<c:out value="${sim }" />" />
		<input type="hidden" id="googleadid" name="googleadid" value="<c:out value="${googleadid }" />" />
		<input type="hidden" id="pkgInstaller" name="pkgInstaller" value="<c:out value="${pkgInstaller }" />" />
		<input type="hidden" id="store_type" name="store_type" value="<c:out value="${app_store_type }" />" />
	</form>
	
	<div class="wrap">
	
		<ul class="tabnav">
            <li><a href="#tab01">일반고객</a></li>
            <li><a href="#tab02" style="padding:0 20px;">대행사 전용</a></li>
        </ul>
	
		<c:choose>
		<c:when test="${app_store_type eq '1' and (app_version eq null or app_version eq '')}">
		
		<p style="font-size: 17px;">특가 상품 구성</p>
		
		<div class="box1" style="margin-top: 10px;">
			<div class="box5" >
				<div class="title">
					<p class="left_title" style="margin :3px;">무선 12개월 이용권</p> 
				</div>
			</div>
            <div class="title box_mini">
                 <p class="pay_div"><span class="discount2">기간결제:</span><del> 196,000원</del></p>
                 <div class="pay_div2"><p class="price">88,000원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
                 <div class="right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="moveDepositInfo('${payTypeVo_P0031.getCode() }');">구매</a></div><!--200211-->
                 <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0031.getCode() }');">자세히 알기 ></a></div><!--200211-->     
            </div>
        </div>
		<div class="box4">
	         <P>
				※ 통신사 정책에 따라 일일 무료 문자 초과 시 별도 과금이 청구 될 수 있습니다.<br/>
				※ 중도 해지 시 할인 받은 금액 만큼 차감 후 환불 처리 됩니다.
			</P>
		</div>
		
		</c:when>
		<c:otherwise>
		
		<div class="tabcontent">
            <div id="tab01">
		
				<c:if test="${affiliates_code eq 'A0002'}">
				
				<p style="font-size: 17px;">무료 이용권 등록하기 </p>
				
				<div class="box2">
					<div class="title">
						<p style="float: left;padding-top: 5px; font-size: 14px;">무료 이용권 번호를 입력해주세요. &nbsp;</p>
						<div class="btn_wrap right_tilte"><a class="btn7" href="javascript:void(0);" onclick="window.web.callFreeTicketRegistPage();">등록</a></div>
					</div>
				</div>
				
				</c:if>
		
				<p class="box5tit">특가 상품 구성 </p>
		
				<div class="box1" style="margin-top: 10px;">
				
					<div class="box5">
						<div class="title">
							<p class="left_title" style="margin :3px;">무선 12개월 이용권</p>
					    </div>
					</div>
		            <div class="title box_mini">
		                 <p class="pay_div"><span class="discount2">기간결제: </span><del> 196,000원</del></p>
		                 <div class="pay_div2"><p class="price">88,000원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
		                 <div class="right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="moveDepositInfo('${payTypeVo_P0031.getCode() }');">구매</a></div><!--200211-->
		                 <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0031.getCode() }');">자세히 알기 ></a></div><!--200211-->  
		            </div>
		            
					<div class="box5">
						<div class="title">
						<p class="left_title" style="margin :3px;">무선 정기 이용권</p> 
						</div>
					</div>
		             <div class="title box_mini" style="border-bottom:none;">
		                 <p class="pay_div"><span class="discount2">정기결제: </span><del>19,800원</del></p><!--200128-->
		                 <div class="pay_div2"><p class="price">9,900원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
		                 <!-- <div class="pay_div2"><p class="price">8,800원</p><span style="font-size: 14px;">(VAT 포함)</span></div>20230620 -->
		                 <div class="right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="moveMonthlyPaymentInfo('${payTypeVo_P0032.getCode() }');">구매</a></div><!--200211-->
		                 <%-- <div class="right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="moveMonthlyPaymentInfo('${payTypeVo_P0045.getCode() }');">구매</a></div><!--20230620--> --%>
		                 <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0032.getCode() }');">자세히 알기 ></a></div><!--200211-->
		                 <%-- <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0045.getCode() }');">자세히 알기 ></a></div><!--20230620--> --%>     
		            </div>
		            <%-- 
		            <div class="title box_mini" style="border-bottom:none;">
		            	<p class="pay_div"><span class="discount2">비즈링 결합 결제 </span></p><!--200128-->
		                 <div class="pay_div2"><p class="price">9,900원</p><span style="font-size: 14px;">(VAT 포함)</span></div><!--20230620-->
		                 <div class="right_tilte pdr15"><a class="btn7" href="https://pf.kakao.com/_kxncpj" style="background:red; border:red;">상담</a></div><!--20230620-->
		                 <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0045.getCode() }');">자세히 알기 ></a></div><!--20230620-->
		                 <!-- <div class="btn_wrap clearfix" style="color:red;">비즈링 + JSAM 결합상품 결제는 <br/>카카오톡으로 상담 요청 드립니다.</div> 20230620 -->
		                 <div class="btn_wrap clearfix"><p style="color:red;">비즈링 (3,300원) + JSAM (6,600원)<br/></p><p>무선 정기 이용권 가격으로<br/>통화연결음과 콜백서비스를 동시에!!<br/></p><p style="color:red;">결합 상품 결제 문의는 카카오톡으로<br/> 상담 요청해주세요</p></div> <!--20230912-->
					</div>
					--%>
					
					<div class="box5">
						<div class="title">
							<p class="left_title" style="margin :3px;">유무선 정기 이용권</p> 
						</div>
					</div>
		             <div class="title box_mini">
		                 <p class="pay_div"><span class="discount2">정기결제: </span><del>25,400원</del></p>
		                 <!-- <div class="pay_div2"><p class="price">15,400원</p><span style="font-size: 14px;">(VAT 포함)</span></div> -->
		                 <div class="pay_div2"><p class="price">14,300원</p><span style="font-size: 14px;">(VAT 포함)</span></div><!--20230620-->
		                 <%-- <div class="right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="showMsg('${payTypeVo_P0033.getCode() }');">구매</a></div><!--200211--> --%>
		                 <div class="right_tilte pdr15"><a class="btn7" href="javascript:void(0);" onclick="showMsg('${payTypeVo_P0046.getCode() }');">구매</a></div><!--20230620-->
		                 <%-- <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0033.getCode() }');">자세히 알기 ></a></div><!--200211--> --%>  
		                 <div class="btn_wrap clearfix"><a class="add" href="javascript:void(0);" onclick="showProductInfoMsg('${payTypeVo_P0046.getCode() }');">자세히 알기 ></a></div><!--20230620-->  
		            </div>
		            
		        </div>
        		<div class="box4">
					<c:choose>
					<c:when test="${app_store_type eq '1' }">
					<P>
						※ 정기결제의 경우, 이용기간 30일 기준으로 자동으로 결제됩니다.<br/>
						※ 통신사 정책에 따라 일일 무료 문자 초과 시 별도 과금이 청구 될 수 있습니다.<br/>
						※ 중도 해지 시 할인 받은 금액 만큼 차감 후 환불 처리 됩니다.
					</P>
					</c:when>
					<c:otherwise>
					<P>
						※ 정기결제의 경우, 이용 기간 만료 하루 전 자동으로 결제됩니다.<br/>
						※ 통신사 정책에 따라 일일 무료 문자 초과 시 별도 과금이 청구 될 수 있습니다.<br/>
						※ 중도 해지 시 할인 받은 금액 만큼 차감 후 환불 처리 됩니다.
					</P>
					</c:otherwise>
					</c:choose>
				</div>
        	</div>
        	<div id="tab02">
        		<!--20190410-->
        		<p class="title_red">
                                    해당 상품은 "영업 대행사" 전용 상품이므로 일반 고객님께서는 상담신청이 불가함을 안내 드립니다.
                </p>
                <p class="box5tit">JSAM+비즈링 결합 - 대행사 전용 </p>
                <div class="box1" style="margin-top: 10px;">
                    <div class="box5">
                        <div class="title">
                            <p class="left_title" style="margin :3px;">SKT</p>
                        </div>
                    </div>
                    <div class="title box_mini">
                        <p class="pay_div"><span class="discount2">JSAM+비즈링:</span> <del>13,750원</del></p>
                        <div class="pay_div2"><p class="price" style="color: #ff0000;">9,900원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
                        <div class="right_tilte pdr15"><a class="btn7" href="javascript:;" onclick="window.web.payCallCustomerCenter('<%=Config.KAKAOPLUS_ID%>');" style="background: #ff0000; border: 1px solid #ff0000">상담</a></div><!--231116-->
                        <!--<div class="btn_wrap clearfix">JSAM(9,900원) 이용가격에<br/>비즈링(3,850원)까지 이용하실 수 있는 기회!!</div>231116-->
                    </div>
                    <div class="box5">
                        <div class="title">
                            <p class="left_title" style="margin :3px;">KT, LG</p>
                        </div>
                    </div>
                    <div class="title box_mini">
                        <p class="pay_div"><span class="discount2">JSAM+비즈링:</span> <del>13,750원</del></p>
                        <div class="pay_div2"><p class="price" style="color: #ff0000;">10,400원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
                        <div class="right_tilte pdr15"><a class="btn7" href="javascript:;" onclick="window.web.payCallCustomerCenter('<%=Config.KAKAOPLUS_ID%>');" style="background: #ff0000; border: 1px solid #ff0000">상담</a></div><!--231116-->
                        <!--<div class="btn_wrap clearfix">JSAM(9,900원) 이용가격에<br />비즈링(3,850원)까지 이용하실 수 있는 기회!!</div>231116-->
                    </div>
                </div>
                <div class="box4">
					<c:choose>
					<c:when test="${app_store_type eq '1' }">
					<P>
						※ 통신사 정책에 따라 일일 무료 문자 초과 시 별도 과금이 청구 될 수 있습니다.<br/>
						※ 중도 해지 시 할인 받은 금액 만큼 차감 후 환불 처리 됩니다.
					</P>
					</c:when>
					<c:otherwise>
					<P>
						※ 통신사 정책에 따라 일일 무료 문자 초과 시 별도 과금이 청구 될 수 있습니다.<br/>
						※ 중도 해지 시 할인 받은 금액 만큼 차감 후 환불 처리 됩니다.
					</P>
					</c:otherwise>
					</c:choose>
				</div>
            </div>
		</div>
		<div class="box3">
	     	<div>
			    <form name="SimpleForm">
			        <a href="javascript:Detail(true);">▶ 제이예스(주) 사업자 정보 <span> click</span></a><br />
			    </form>
			    <form name="DetailForm">
			        <a href="javascript:Detail(false);">▶ 제이예스(주) 사업자 정보 <span> click</span></a><br />
			        <br />
			        <p>
			        	대표이사 : 정용관 | 사업자등록번호 : 113-86-30615 <br/>
						주소 : 서울시 영등포구 영등포동8가 92 knk디지털타워 606호<br/>
		       			상호명 : 제이예스주식회사 | 법인 등록번호 : 110111-4121143 <br/>
						문의전화 : <c:out value="010-5756-9604" />(평일 : 10:00-17:00)
					</p>
					<br />
			    </form>
			</div>
		</div>
		<div class="box3" style="display: none;">
			<div class="btn_white_wrap">
				<a class="btn_white" href="javascript:void(0);" onclick="window.web.close();">닫기</a>
			</div>
		</div>
		<p style="font-size: 17px; display: none;">일반 상품 구성 </p>

		<div class="box1" style="margin-top: 15px;  display: none;">

	        <div class="box7">
				<div class="title">
					<p class="left_title" style="margin :3px;">1개월 이용권</p>
				</div>
			</div>
		
			<div class="title box_mini">
				<p class="pay_div"><del>35,000원</del><span class="discount">할인가</span></p>
				<div class="pay_div2"><p class="price">17,500원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
				<div class="btn_wrap right_tilte pdr15"><a class="btn1" href="javascript:void(0);" onclick="selectPay('${payTypeVo_P0028.getName() }','${payTypeVo_P0028.getPrice() }','${payTypeVo_P0028.getCode() }');">구매</a></div>
            </div>
            
            <div class="box7">
                <div class="title">
                 <p class="left_title" style="margin :3px;">3개월 이용권</p>
                </div>
            </div>
            
             <div class="title box_mini"> 
                 <p class="pay_div"><del>88,000원</del><span class="discount">할인가</span></p>
                 <div class="pay_div2"><p class="price">44,000원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
                 <div class="btn_wrap right_tilte pdr15"><a class="btn1" href="javascript:void(0);" onclick="selectPay('${payTypeVo_P0029.getName() }','${payTypeVo_P0029.getPrice() }','${payTypeVo_P0029.getCode() }');">구매</a></div>
            </div>
            
            <div class="box7">
                <div class="title">
                 <p class="left_title" style="margin :3px;">6개월 이용권</p>
                </div>
            </div>
            
            <div class="title box_mini">
                 <p class="pay_div"><del>138,000원</del><span class="discount">할인가</span></p>
                 <div class="pay_div2"><p class="price">66,000원</p><span style="font-size: 14px;">(VAT 포함)</span></div>
                 <div class="btn_wrap right_tilte pdr15"><a class="btn1" href="javascript:void(0);" onclick="moveDepositInfo('${payTypeVo_P0030.getCode() }');">구매</a></div>
            </div>
            
        </div>
		</c:otherwise>
		</c:choose>
		</div>
	</div>
	
	<script>
    Detail(false);
    function Detail(isShow) {
        if (isShow)
        {
            SimpleForm.hidden = true;
            DetailForm.hidden = false;
        }
        else
        {
            SimpleForm.hidden = false;
            DetailForm.hidden = true;
        }
    }
	</script>

</body>
</html>