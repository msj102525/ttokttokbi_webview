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

    caEmail.addEventListener("click", () => {
        window.AndroidInterface.caEmail();
    });

    caKakao.addEventListener("click", () => {
        window.AndroidInterface.caKakao();
    });

});
