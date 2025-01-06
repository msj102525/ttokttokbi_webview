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
<link href="<%=path%>/css/holiday_style.css" rel="stylesheet" />
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
	window.onload = function() {
		var test = document.getElementsByTagName("textarea");

		for (var i = 0; i < test.length; i++) {
			test[i].onfocus = function textareaOnFocus() {
				var target = this;
				moveCaretToEnd(target);
				// Work around Chrome's little problem
				window.setTimeout(function() {
					moveCaretToEnd(target);
				}, 1);
			}
		}
	}

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
	function init() {
		setPlaceholderMultiple('#test1', '텍스트를 입력해주세요');
		setPlaceholderMultiple('#test2', '텍스트를 입력해주세요');
	}
</script>
</head>
<body onload="init();">
	<div id="edmWrap">
		<div class="main">
			<img src="<%=path%>/images/template_sample4.jpg" alt="" border="0"
				usemap="imgmap201765144442">
			<form>
				<div class="territory01">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [28, 10], [22, 16], [18, 20])">
				</div>
				<div class="photo" id="img_div_1">
					<img id="i01" onclick="callAndPicture(id)" />
				</div>

				<div class="photo_con" id="img_div_2">
					<img id="i02" onclick="callAndPicture(id)" />
				</div>

				<div class="territory02">
					<textarea id="test1" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="setVailableLength(this, [28, 20], [22, 30], [18, 35])"></textarea>
				</div>

				<div class="territory03">
					<textarea id="test2" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="setVailableLength(this, [16, 25], [12, 32], [10, 38])"></textarea>
				</div>

				<div class="territory04">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 28], [10, 35])">
				</div>
			</form>
		</div>
	</div>
</body>
</html>