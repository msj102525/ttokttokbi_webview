var timer;

function isEmpty(value) {
	if (value == ""
			|| value == null
			|| value == undefined
			|| (value != null && typeof value == "object" && !Object
					.keys(value).length)) {
		return true
	} else {
		return false
	}
}

function chkword(obj, maxlength) {
	var str = obj.value; // 이벤트가 일어난 컨트롤의 value 값
	var str_length = str.length; // 전체길이
	// 변수초기화
	var max_length = maxlength; // 제한할 글자수 크기
	var i = 0; // for문에 사용
	var ko_byte = 0; // 한글일경우는 2 그밗에는 1을 더함
	var li_len = 0; // substring하기 위해서 사용
	var one_char = ""; // 한글자씩 검사한다
	var str2 = ""; // 글자수를 초과하면 제한할수 글자전까지만 보여준다.
	for (i = 0; i < str_length; i++) {
		// 한글자추출
		one_char = str.charAt(i);
		ko_byte++;
	}
	// 전체 크기가 max_length를 넘지않으면
	if (ko_byte <= max_length) {
		li_len = i + 1;
	}
	// 전체길이를 초과하면
	if (ko_byte > max_length) {
		str2 = str.substr(0, max_length);
		obj.value = str2;
	}
	obj.focus();
}

/* arr[폰트크기, 글자 제안 수] */
function setVailableLength(obj, arr_1, arr_2, arr_3) {
	var str = obj.value; // 이벤트가 일어난 컨트롤의 value 값
	var str_length = str.length; // 전체길이
	var maxLength_1 = 0, maxLength_2 = 0, maxLength_3 = 0;
	if (arr_1 != null) {
		maxLength_1 = arr_1[1];
	}
	if (arr_2 != null) {
		maxLength_2 = arr_2[1];
	}
	if (arr_3 != null) {
		maxLength_3 = arr_3[1];
	}
	if (str_length <= maxLength_1) {
		obj.style.fontSize = arr_1[0] + 'px';
		chkword(obj, arr_1[1]);
	} else {
		if (maxLength_2 > 0) {
			if (str_length > maxLength_1 && str_length <= maxLength_2) {
				obj.style.fontSize = arr_2[0] + 'px';
				chkword(obj, arr_2[1]);
			} else {
				if (maxLength_3 > 0) {
					if (str_length > maxLength_2) {
						obj.style.fontSize = arr_3[0] + 'px';
						chkword(obj, arr_3[1]);
					}
				} else {
					obj.style.fontSize = arr_2[0] + 'px';
					chkword(obj, arr_2[1]);
				}
			}
		} else {
			obj.style.fontSize = arr_1[0] + 'px';
			chkword(obj, arr_1[1]);
		}
	}

}

function setPlaceholderMultiple(id, placeholder) {
	var value = $(id).val();
	if (isEmpty(value)) {
		$(id).attr('value', placeholder);
	}
	$(id).focus(function() {
		if ($(this).val() == placeholder) {
			$(this).attr('value', '');
		}
	});
	$(id).blur(function() {
		if ($(this).val() == '') {
			$(this).attr('value', placeholder);
		}
	});
}

function callAndPicture(id) {
	id = '#' + id;
	var width = $(id).width();
	var height = $(id).height();
	window.AndroidInterface.callAndPicture(id, width, height);
}
function setImageTag(id, data) {
	$(id).attr('src', 'data:image/png;base64,' + data);
	var cap_id = id + '_cap'
	$(cap_id).val('data:image/png;base64,' + data);
}
function checkBorderNone() {
	var textArr = document.getElementsByName("text_type");
	for (var i = 0; i < textArr.length; i++) {
		if (textArr[i].style.border == 'none'
				&& textArr[i].style.overflow == 'hidden') {
			clearInterval(timer);
			break;
		}
	}
	window.AndroidInterface.captureTemplete();
}
function save(isSaveImage) {
	/*
	 * cont_path = path; // code 200:성공, 201:텍스트 미입력, 202:이미지 미입력 var imgArr =
	 * document.getElementsByName("image_type"); for (var i = 0; i <
	 * imgArr.length; i++) { if (isEmpty(imgArr[i].value)) { // 이미지 체크 if
	 * (isSaveImage == "Y") { window.AndroidInterface .saveResult("202", "이미지를
	 * 등록해주시기 바랍니다.\n미등록 시 샘플이미지로 발송됩니다.\n\n샘플이미지로 저장하시겠습니까?"); return; } } } //
	 * text 입력 유효성 체크 var textArr = document.getElementsByName("text_type"); for
	 * (var i = 0; i < textArr.length; i++) { if (isEmpty(textArr[i].value)) {
	 * window.AndroidInterface.saveResult("201", "입력하지 않은 텍스트가 있습니다.\n모두 입력해
	 * 주세요."); return; } }
	 */
	// 최상단 이동
	$("body").scrollTop(0);
	// text 보더 제거, 포커스 해제, placeholder 제거
	var textArr = document.getElementsByName("text_type");
	for (var i = 0; i < textArr.length; i++) {
		textArr[i].style.border = 'none';
		textArr[i].style.overflow = 'hidden';
		textArr[i].blur();
		textArr[i].placeholder = '';
		var tagStr = textArr[i].getAttribute("tag");
		if (!isEmpty(tagStr) && tagStr == textArr[i].value) {
			textArr[i].value = '';
		}
	}
	// textarea 속성 제거 checker
	timer = setInterval("checkBorderNone()", 500);
}
