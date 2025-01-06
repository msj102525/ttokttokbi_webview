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
<title>템플릿</title>

<link href="<%=path%>/css/custom-styles.css" rel="stylesheet" />
<link href="<%=path%>/css/event_style.css" rel="stylesheet" />
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
</script>
</head>
<body>

	<div id="edmWrap">
		<div class="main">
			<img src="<%=path%>/images/template_sample1.jpg" alt="" border="0"
				usemap="imgmap201765144442" />
			<form>
				<div class="territory01">
					<input type="text" class="input-text" id="t01" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [20, 12], [15, 15], [10, 20])" />
				</div>

				<div class="territory02">
					<input type="text" class="input-text" id="t02" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 18], [8, 24], [5, 27])" />
				</div>

				<div class="photo" id="img_div_1">
					<img id="i01" onclick="callAndPicture(id)" />
				</div>


				<div class="territory03">
					<input type="text" class="input-text" id="t03" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 5], [10, 7], [6, 9])" />
				</div>


				<div class="territory04">
					<input type="text" class="input-text" id="t04" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 11], [10, 16], [6, 18])" />
				</div>


				<div class="territory05">
					<input type="text" class="input-text" id="t05" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 5], [10, 7], [6, 9])" />
				</div>

				<div class="territory06">
					<input type="text" class="input-text" id="t06" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 11], [10, 16], [6, 18])" />
				</div>

				<div class="territory07">
					<input type="text" class="input-text" id="t07" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 5], [10, 7], [6, 9])" />
				</div>

				<div class="territory08">
					<input type="tel" class="input-text" id="t08" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [12, 11], [10, 16], [6, 18])" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>