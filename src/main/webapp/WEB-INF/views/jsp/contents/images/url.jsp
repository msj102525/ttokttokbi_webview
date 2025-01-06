<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name=”format-detection” content=”telephone=no”>
<title>템플릿</title>
<style type="text/css">
.refusal {
	background-color: #ddd;
}
.refusal p {
	padding: 20px;
	text-align: center;
	font-weight: bold;
	font-size: 1em;
	margin: 0px;
}
</style>
<script type="text/javascript">
function unsubscribe(num) {
	location.href="tel:"+num;
}
</script>
</head>
<body style="margin: 0;">
<div>
	<img src="${templateVo.getSynthesis_image_url() }" style="width: 100%;">
</div>
<div class="refusal" onclick="unsubscribe('080-566-5678')">
	<p>
		무료수신거부 080-566-5678
	</p>
</div>
</body>
</html>