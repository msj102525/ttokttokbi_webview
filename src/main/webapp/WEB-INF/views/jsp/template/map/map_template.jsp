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
<link href="<%=path%>/css/map_style.css" rel="stylesheet" />
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
</script>
</head>
<body>

	<div id="edmWrap">
		<div class="main">
			<img src="<%=path%>/images/template_sample5.jpg" alt="" border="0"
				usemap="imgmap201765144442">
			<form>
				<div class="map" id="img_div_1">
					<img id="i01" onclick="callAndPicture(id)" />
				</div>

				<div class="territory01">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [28, 12], [22, 16], [18, 20])">
				</div>

				<div class="photo_con" id="img_div_2">
					<img id="i02" onclick="callAndPicture(id)" />
				</div>

				<div class="territory02">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [16, 9], [10, 12])">
				</div>


				<div class="territory03">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [16, 9], [10, 12])">
				</div>

				<div class="territory04">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [16, 9], [10, 12])">
				</div>

				<div class="territory05">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [16, 9], [10, 12])">
				</div>

				<div class="territory06">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [8, 25])">
				</div>

				<div class="territory07">
					<input type="text" class="input-text" id="company" name="text_type"
						placeholder="텍스트를 입력해주세요" onkeyup="setVailableLength(this, [8, 25])">
				</div>

				<!--<map id="imgmap201765144442" name="imgmap201765144442">
			<area shape="rect" alt="" title="" coords="11,6,1076,523" href="" target="" />
			<area shape="rect" alt="" title="" coords="14,755,695,904" href="" target="" />
			<area shape="rect" alt="" title="" coords="747,614,1009,797" href="" target="" />
			<area shape="rect" alt="" title="" coords="33,933,795,1188" href="" target="" />
			<area shape="rect" alt="" title="" coords="412,1319,854,1512" href="" target="" />
			<area shape="rect" alt="" title="" coords="18,1610,347,1721" href="" target="" />
			<area shape="rect" alt="" title="" coords="376,1605,1045,1727" href="" target="" />
		</map>-->
			</form>
		</div>
	</div>
</body>
</html>