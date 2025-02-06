document.addEventListener("DOMContentLoaded", () => {
    const useragent = document.querySelector("#useragent").value;
    const privacyPolicy = document.querySelector("#privacyPolicy");
    const terms = document.querySelector("#terms");
    const notice = document.querySelector("#notice");
    const faq = document.querySelector("#faq");
    const caEmail = document.querySelector("#caEmail");
    const caKakao = document.querySelector("#caKakao");

    ///////////////////////////////////////////////////////

    privacyPolicy.addEventListener("click", (e) => {

        fetch(`/ttb/privacy/policy`, {
            method: 'GET',
            headers: {
                'User-Agent': useragent
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                window.location.href = response.url;
            })
            .catch((error) => {
                console.error("Error:", error);
                alert("개인정보처리방치 페이지 오류");
            });

    });

    terms.addEventListener("click", (e) => {
        fetch(`/ttb/terms`, {
            method: 'GET',
            headers: {
                'User-Agent': useragent
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                window.location.href = response.url;
            })
            .catch((error) => {
                console.error("Error:", error);
                alert("이용약관 페이지 오류");
            });

    });

    notice.addEventListener("click", (e) => {
        fetch(`/ttb/get_notice_list`, {
            method: 'GET',
            headers: {
                'User-Agent': useragent
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                window.location.href = response.url;
            })
            .catch((error) => {
                console.error("Error:", error);
                alert("공지사항 페이지 오류");
            });

    });

    faq.addEventListener("click", (e) => {
        fetch(`/ttb/faq`, {
            method: 'GET',
            headers: {
                'User-Agent': useragent
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                window.location.href = response.url;
            })
            .catch((error) => {
                console.error("Error:", error);
                alert("FAQ 페이지 오류");
            });

    });

    const isAndroidInterface = () => {
        return typeof window.AndroidInterface !== 'undefined' && window.AndroidInterface !== null;
    };


    caEmail.addEventListener("click", () => {
        try {
            if (!isAndroidInterface()) {
                console.error('Android Interface is not available');
                alert('앱에서만 사용 가능한 기능입니다.');
                return;
            }
            window.AndroidInterface.caEmail();
        } catch (error) {
            console.error('Error calling caEmail:', error);
            alert('이메일 기능 실행 중 오류가 발생했습니다.');
        }
    });

    caKakao.addEventListener("click", () => {
        try {
            if (!isAndroidInterface()) {
                console.error('Android Interface is not available');
                alert('앱에서만 사용 가능한 기능입니다.');
                return;
            }
            window.AndroidInterface.caKakao();
        } catch (error) {
            console.error('Error calling caKakao:', error);
            alert('카카오 기능 실행 중 오류가 발생했습니다.');
        }
    });

});
