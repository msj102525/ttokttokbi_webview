<%@page import="com.jyes.www.common.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("crcn", "\r\n"); //Enter PC
	pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<c:set var="call_css_name" value="" />
<c:set var="representation_number" value="<%=Config.REPRESENTATION_NUMBER %>" />
<c:if test="${affiliates_code eq 'A0001' }">
<c:set var="call_css_name" value="_a" />
<c:set var="representation_number" value="<%=Config.REPRESENTATION_NUMBER_SMATRO %>" />
</c:if>
<c:set var="representation_number" value="${fn:substring(representation_number,0,3)}-${fn:substring(representation_number,3,7)} - ${fn:substring(representation_number,7,11)}" />
<!DOCTYPE html>
<html lang="ko"> 
<head> 
	<meta charset="utf-8"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>상단: 헤더명</title> 
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/reset.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/call<c:out value="${call_css_name }" />.css">
	<style type="text/css">
	.nav ul li{
	    width: auto;
	    white-space:nowrap;
	}
	/* #scroller {
		overflow: hidden;
		position: absolute;
	} */
	</style>
	<script type="text/javascript" src="<%=path%>/js/iscroll.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
	var myScroll;
	function loaded () {
		myScroll = new IScroll('#wrapper', { scrollX: true, scrollY: false, mouseWheel: true, click: true, bounce: false });
	}
	$(document).ready(function(){
		/*qna*/
		setLiEvent();
		setCategoryDisplay();
		loaded(); //적용시 wrapper overflow: auto -> hidden으로 변경 기기별 이슈가 많아 보임 확인 필요 position: relative;
	});
	
	function setLiEvent() {
		$('.qanda li').on('click',function(){	
			var active = $(this).hasClass('active');
			$('.qanda li').removeClass('active');
			if(active==false){
				$(this).addClass('active');
				$('.panel').stop().slideUp(200);
				$(this).stop().find('.panel').slideDown(200);
				return false;
			}else if(active==true){
				$(this).removeClass('active');
				$(this).stop().find('.panel').slideUp(200);
				return false;
			}	
		});
	}
	
	function setCategoryDisplay() {
		var ul_width = 0;
		var array = new Array();
		$("#wrapper").css("height", $("#qna_category > li").eq(0).outerHeight()+"px");
		$("#qna_category > li").each(function(index) {
			array[index] = $(this).outerWidth()+1;//outerWidth 소수점 버림 때문에 UI 틀어짐 이슈발생 +1 로 이슈 해결
			ul_width += array[index];
		});
		$("#qna_category").css("width", ul_width+"px");
		$("#scroller").css("width", ul_width+"px");
		$("#qna_category > li").each(function(index) {
			$(this).css("width", array[index]+"px");
		});
	}
	
	function setBoard() {
		var email = (arguments[0] == null) ? "" : arguments[0];
		var text = (arguments[1] == null) ? "" : arguments[1];
		text = text.replace(/<br>/gi, "\r\n");//App 줄바꿈 포함 파라미터 전달시 오류로 인해 br 태크로 치환해서 넘겨준것을 다시 줄바꿈으로 치환
		var id = "${id}";
		var affiliates_code = "${affiliates_code}";
		$.ajax({
			url : "<%=path %>/board/setBoardAjax",
			type : 'POST',
			data : {id:id, affiliates_code:affiliates_code, email:email, text:text},
			async : true,
			dataType : "json",
			success : function(data) {
				if(data.msg=='1') {
					alert("정상 처리 되었습니다.");
				} else {
					alert("에러");
				}
			}
		});
	}
	
	function getBoardList() {
		var obj = (arguments[0] == null) ? "" : arguments[0];
		var board_type = (arguments[1] == null) ? "" : arguments[1];
		var category_code = (arguments[2] == null) ? "" : arguments[2];
		if(board_type="FAQ") {
			$(obj).parent().children('li').removeClass('main');
			$(obj).addClass('main');
			$.ajax({
				url : "<%=path %>/board/getFaqBoardCategoryContentsListAjax",
				type : 'POST',
				data : {category_code:category_code},
				async : true,
				dataType : "json",
				success : function(data) {
					if(data.msg==1) {
						var html = "";
						for (var i = 0; i < data.al_tbBoardFaqCategoryContentsList.length; i++) {
							var subject = (data.al_tbBoardFaqCategoryContentsList[i].subject==null)?"":data.al_tbBoardFaqCategoryContentsList[i].subject;
							var text = (data.al_tbBoardFaqCategoryContentsList[i].text==null)?"":data.al_tbBoardFaqCategoryContentsList[i].text;
							text = text.replace(/\r\n/gi, "<br/>");
							var sub_html = '';
							sub_html += '<li>';
							
							sub_html += '<div class="fd_num3"><span class="fir1">Q</span></div>';
							sub_html += '<span class="fd_arr"></span>';
							sub_html += '<div class="fd_text">';
							sub_html += '<h3><a href="#open-panel">';
							sub_html += '<span>'+subject+'</span></a>';
							sub_html += '</h3>';
							sub_html += '</div>';
							
							/* sub_html += '<span class="fd_num"><span class="fir1">Q</span></span>';
							sub_html += '<span class="fd_arr"></span>';
							sub_html += '<h3><a class="accordion-opener" href="#open-panel"><span class="over">'+subject+'</span></a></h3>'; */
							
							sub_html += '<div class="panel" style="position: relative; visibility: visible; display: none;">';
							sub_html += '<div class="fd_num2"><span class="fir">A</span></div>';
							sub_html += '<div class="text1">'+text+'</div>';
							sub_html += '</div>';
							sub_html += '</li>';
							html += sub_html;
						}
						$("#qna_list").html(html);
						setLiEvent();
					} else {
						alert("조회실패");
					}
				}
			});
		}
	}
	
	</script>
</head>  
<body>
	<div class="wrap">
		<div class="box_wrap">
			<div class="box" onclick="window.web.callRequestCenter();">
				<p><img src="<%=path%>/img/at.png"></p>
				<p style="margin-top: 5px;">이메일 문의</p>
			</div>
			<div class="box2" onclick="window.web.payCallCustomerCenter('<%=Config.KAKAOPLUS_ID%>');">
				<p><img src="<%=path%>/img/kakao.png"></p>
				<p style="margin-top: 5px;">카카오톡 문의</p>
			</div>
		</div>
		<div class="btn_wrap">
			<div class="btn" onclick="window.web.callRequestPersonalPromotion();">맞춤형 콘텐츠 의뢰</div>
			<div class="btn" onclick="window.web.callMoveWebPage('<%=Config.HOST_WWW_URL%>');" <c:if test="${affiliates_code eq 'A0001' }">style="display: none;"</c:if>>JSAM 홈페이지</div>
		</div>
   	</div>
   	<div style="background-color: #e9edf0;padding: 12px 5px;"><p>* 자주 묻는 질문</p></div>
	<div id="wrapper" class="nav" style="overflow: hidden; position: relative;">
		<div id="scroller">
			<ul id="qna_category">
             	<c:forEach var="tbBoardFaqCategoryList" items="${al_tbBoardFaqCategoryList}" varStatus="status">
             	<li onclick="getBoardList(this,'FAQ','${tbBoardFaqCategoryList.code}');" <c:if test="${status.first}">class="main"</c:if>>${tbBoardFaqCategoryList.name }</li>
             	</c:forEach>
			</ul>
		</div>
	</div>
   	<div id="qanda">
	<div class="qanda">
		<div class="tbl_head">
			<ul id="qna_list">
				<c:forEach var="tbBoardFaqCategoryContentsList" items="${al_tbBoardFaqCategoryContentsList}" varStatus="status">
				<li>
					<div class="fd_num3"><span class="fir1">Q</span></div>
					<span class="fd_arr"></span>
					<div class="fd_text">
						<h3><a href="#open-panel">
							<span><c:out value="${tbBoardFaqCategoryContentsList.subject }" /></span></a>
						</h3>
					</div>
					
					<%-- <span class="fd_num"><span class="fir1">Q</span></span>
					<span class="fd_arr"></span>
					<h3><a class="accordion-opener" href="#open-panel">
						<span class="over"><c:out value="${tbBoardFaqCategoryContentsList.subject }" /></span></a>
					</h3> --%>
					
					<div class="panel" style="position: relative; visibility: visible; display: none;">
						<div class="fd_num2"><span class="fir">A</span></div>
						<div class="text1">${fn:replace(tbBoardFaqCategoryContentsList.text, crcn, br) }</div>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>	
	</div>
</div>
</body> 
</html> 