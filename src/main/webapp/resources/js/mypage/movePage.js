document.addEventListener("DOMContentLoaded", () => {
    const privacyPolicy = document.querySelector("#privacyPolicy");
    const terms = document.querySelector("#terms");
    const useragent = document.querySelector("#useragent").value;



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

});
