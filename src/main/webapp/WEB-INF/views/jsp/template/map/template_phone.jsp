<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	/* response.setHeader("Cache-Control","no-store");  
	response.setHeader("Pragma","no-cache");  
	response.setDateHeader("Expires",0);  
	if (request.getProtocol().equals("HTTP/1.1"))
	    response.setHeader("Cache-Control", "no-cache"); */
%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/js/template_common.js"></script>
<title>Insert title here</title>
<link href="<%=path%>/css/custom-styles.css" rel="stylesheet" />
<link href="<%=path%>/css/template_phone_style.css" rel="stylesheet" />
<style type="text/css">
/*reset*/
* {
	margin: 0;
	padding: 0;
}
</style>
<script type="text/javascript">
	function moveCaretToEnd(el) {
		if (typeof el.selectionStart == "number") {
			el.selectionStart = el.selectionEnd = el.value.length;
		} else if (typeof el.createTextRange != "undefined") {
			el.focus();
			var range = el.createTextRange();
			range.collapse(false);
			range.select();
		}
	}

	var textarea = document.getElementById("test1");

	textarea.onfocus = function() {
		moveCaretToEnd(textarea);

		// Work around Chrome's little problem
		window.setTimeout(function() {
			moveCaretToEnd(textarea);
		}, 1);
	};
	function init() {
		setPlaceholderMultiple('#t03', '텍스트를 입력해주세요');
	}
</script>
</head>
<body onload="init();">

	<div id="edmWrap">
		<div class="main">

			<!--20170727수정 시작-->
			<img src="<%=path%>/images/sample17_bg.png" alt="" border="0"
				usemap="imgmap201765144442">
			<form>
				<div class="photo" id="img_div_1" onclick="callAndPicture('i01')">
					<img id="i01" />
				</div>
				<!--20170727수정 끝-->

				<div class="territory01">
					<input type="text" class="input-text" id="t01" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [20, 4], [15, 6], [10, 8])">
				</div>

				<div class="territory02">
					<input type="text" class="input-text" id="t02" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [18, 12], [15, 15], [10, 18])">
				</div>

				<div class="territory03">
					<textarea id="t03" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="setVailableLength(this, [12, 63])"></textarea>
				</div>
			</form>
		</div>
	</div>

</body>
</html>