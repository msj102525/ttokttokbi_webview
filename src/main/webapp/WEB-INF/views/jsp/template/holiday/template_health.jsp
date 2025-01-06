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
<title>템플릿1</title>
<link href="<%=path%>/css/custom-styles.css" rel="stylesheet" />
<link href="<%=path%>/css/template_health_style.css" rel="stylesheet" />
<style type="text/css">
/*reset*/
* {
	margin: 0;
	padding: 0;
}

::-webit-scrllbar {
	display: none;
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
</script>
</head>
<body>
	<div id="edmWrap">
		<div class="main">

			<img src="<%=path%>/images/sample16.png" alt="" border="0"
				usemap="imgmap201765144442">
			<form>
				<div class="photo" id="img_div_1" onclick="callAndPicture('i01')">
					<img id="i01" />
				</div>

				<div class="territory01">
					<input type="text" class="input-text" id="t01" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [15, 15], [10, 20])" />
				</div>

				<div class="territory02">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [32, 6], [26, 9], [20, 15])">
				</div>

				<div class="territory03">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [15, 15], [10, 20])">
				</div>

				<div class="territory04">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [15, 15], [10, 20])">
				</div>
			</form>
		</div>
	</div>
</body>
</html>