<%@page import="com.jyes.www.vo.TemplateVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>템플릿1</title>

<link href="<%=path%>/css/custom-styles.css" rel="stylesheet" />
<link href="<%=path%>/css/name_style.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/js/template_common.js"></script>
<style type="text/css">
/*reset*/
* {
	margin: 0;
	padding: 0;
}
</style>
<title>Insert title here</title>
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
		setPlaceholderMultiple('#test1', '텍스트를 입력해주세요');
	}
</script>
</head>
<body onload="init();">
	<div id="edmWrap">
		<div class="main">
			<img src="<%=path%>/images/name_bg.png" alt="" border="0"
				usemap="imgmap201765144442">
			<form>
				<div class="photo" id="img_div_1" onclick="callAndPicture('i01')">
					<img id="i01" />
				</div>

				<div class="name">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [20, 8], [16, 12], [10, 18])">
				</div>

				<div class="position">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 16], [10, 20])">
				</div>

				<div class="territory01">
					<textarea id="test1" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="setVailableLength(this, [11, 35])"></textarea>
				</div>

				<div class="territory02">
					<input type="tel" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [14, 22])">
				</div>

				<div class="territory03">
					<input type="tel" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [14, 22])">
				</div>

				<div class="territory04">
					<input type="email" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [14, 22])">
				</div>
			</form>
		</div>
	</div>
</body>
</html>