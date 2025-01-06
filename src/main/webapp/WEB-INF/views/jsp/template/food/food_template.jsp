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
<link href="<%=path%>/css/template_style.css" rel="stylesheet" />
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
		setPlaceholderMultiple('#test3', '텍스트를 입력해주세요');
	}
</script>
</head>
<body style="font-family: 'Jeju Gothic', sans-serif;" onload="init();">
	<div id="edmWrap">
		<div class="main">
			<img src="<%=path%>/images/template_sample2.jpg" alt="" border="0"
				usemap="imgmap201765144442">
			<form>
				<div class="photo" id="img_div_1">
					<img id="i01" onclick="callAndPicture(id)" />
				</div>

				<div class="territory01">
					<img src="<%=path%>/images/title.png">
					<textarea id="test1" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="chkword(this,6)"></textarea>
				</div>

				<div class="territory02">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [20, 8], [16, 12], [10, 15])">
				</div>

				<div class="territory03">
					<textarea id="test2" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="chkword(this,55)"></textarea>
				</div>

				<div class="territory04">
					<textarea id="test3" name="text_type" tag="텍스트를 입력해주세요"
						onkeyup="chkword(this,30)"></textarea>
				</div>

				<div class="photo_con" id="img_div_2">
					<img id="i02" onclick="callAndPicture(id)" />
				</div>

				<div class="territory05">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="chkword(this,6)">
				</div>

				<div class="territory06">
					<input type="tel" pattern="[0-9]*" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="chkword(this,13)">
				</div>
			</form>
		</div>
	</div>
</body>
</html>