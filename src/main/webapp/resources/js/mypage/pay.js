document.addEventListener("DOMContentLoaded", () => {
    const modal = document.querySelector("#payModal");
    const openModal = document.querySelector("#payBtn");
    const closeModal = document.querySelector("#payModal #closeModal");
    const payTabs = document.querySelectorAll("#payTab");
    const ticketContents = document.querySelectorAll("#ticketContent");
    const ticketPayBtn = document.querySelector("#ticketPayBtn");
    const id = document.querySelector("#updateUserInfoForm #id").value;
    const affiliatesCode = document.querySelector("#updateUserInfoForm #affiliatesCode").value;
    const approachPath = document.querySelector("#updateUserInfoForm #approachPath").value;
    const name = document.querySelector("#updateUserInfoForm #approachPath").value;

    const confirmModalPay = document.getElementById('confirmModalPay');
    const confirmCancelBtnPay = document.getElementById('confirmCancelBtnPay');
    const confirmSubmitBtnPay = document.getElementById('confirmSubmitBtnPay');

    const isPayRp = document.querySelector("#isPayRp").value;
    const email = document.querySelector("#email").value;
    const useragent = document.querySelector("#useragent").value;
    const storeType = '1';

    // console.log(isPayRp)
    // console.log(email)
    // console.log(useragent)


    openModal.addEventListener("click", (e) => {
        e.preventDefault();
        modal.style.display = "block";
        modal.offsetHeight;
        modal.classList.add("show");

        const formData = new FormData();
        formData.append('id', id);
        formData.append('approach_path', approachPath);
        formData.append('affiliates_code', affiliatesCode);
        formData.append('useragent', useragent);

        // for (const [key, value] of formData.entries()) {
        //     console.log(`${key}: ${value}`);
        // }

        fetch('/ttb/get_payment_list', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // console.log("성공:", data);
                const paymentList = document.querySelector('.tab-content.right');
                const payments = data?.apiResponse?.data?.pay_list || [];

                // console.log(payments)

                // 날짜 포맷팅 함수
                function formatDate(timestamp) {
                    const date = new Date(parseInt(timestamp));
                    return `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
                }

                function formatPrice(price) {
                    return parseInt(price).toLocaleString() + '원';
                }

                if (payments.length === 0) {
                    paymentList.innerHTML = `
                        <div style="padding: 20px; text-align: center; color: #666;">
                            결제 내역이 없습니다.
                        </div>
                    `;
                    return;
                }

                let html = `
                    <div class="payment-history">

                        <div class="payment-header">
                            <div class="header-name">상품명</div>
                            <div class="header-price">결제금액</div>
                            <div class="header-date">결제일시</div>
                            <div class="header-expiration">이용 종료일</div>
                        </div>
                `;

                payments.forEach(payment => {
                    html += `
                        <div class="payment-item">
                            <div class="payment-name">${payment.name}</div>
                            <div class="payment-price">${formatPrice(payment.price)}</div>
                            <div class="payment-date">${formatDate(payment.date)}</div>
                            <div class="payment-expiration">
                                ${payment.expiration_date ? formatDate(payment.expiration_date) : '-'}
                            </div>
                        </div>
                    `;
                });

                html += '</div>';
                paymentList.innerHTML = html;
            })
            .catch((error) => {
                console.error("Error:", error);
                const paymentList = document.querySelector('.tab-content.right');
                paymentList.innerHTML = `
                    <div style="padding: 20px; text-align: center; color: #666;">
                        결제 내역을 불러오는 중 오류가 발생했습니다.
                    </div>
                `;
            });
    });

    // 모달 닫기
    const closeModalFunction = () => {
        modal.classList.remove("show");
        setTimeout(() => {
            modal.style.display = "none";
        }, 300);
    };

    closeModal.addEventListener("click", closeModalFunction);

    // 배경 클릭 시 닫기
    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            closeModalFunction();
        }
    });

    // 첫 번째 탭을 기본으로 선택
    payTabs[0].classList.add("on");

    // 탭 클릭 이벤트 처리
    payTabs.forEach((tab) => {
        tab.addEventListener("click", (e) => {
            if (tab.classList.contains("on")) {
                return;
            }

            payTabs.forEach((t) => t.classList.remove("on"));
            tab.classList.add("on");

        });
    });

    const swiper = new Swiper(".ticketSwiper", {
        slidesPerView: "auto",
        spaceBetween: 10,
        freeMode: {
            enabled: true,
            sticky: false,
        },
        grabCursor: true,
    });

    // 첫 번째 탭을 기본으로 선택
    ticketContents[0].classList.add("on");

    // 탭 클릭 이벤트 처리
    ticketContents.forEach((tab) => {
        tab.addEventListener("click", (e) => {
            if (tab.classList.contains("on")) {
                return;
            }

            ticketContents.forEach((t) => t.classList.remove("on"));
            tab.classList.add("on");
        });
    });

    // 결제 버튼 클릭 이벤트 처리
    ticketPayBtn.addEventListener("click", () => {
        confirmModalPay.style.display = "block";
    });

    confirmCancelBtnPay.addEventListener("click", () => {
        confirmModalPay.style.display = "none";
    });

    confirmSubmitBtnPay.addEventListener("click", () => {



        const activeContent = Array.from(ticketContents).find((tab) =>
            tab.classList.contains("on")
        );


        if (activeContent) {
            const code = activeContent.querySelector("input")?.value || "텍스트 없음";
            console.log("활성화된 탭의 제목:", code);

            if (isPayRp == "Y") {
                switch (code) {
                    case "P0031":
                        alert("현재 정기결제 상품 사용중입니다. 정기결제 해지 후, 일반결제 구매가 가능하십니다.");
                        return;

                    case "P0009":
                    case "P0032":
                    case "P0045":
                        alert("유무선 정기결제 상품에서 무선 정기결제 상품 변경시, 고객센터에 문의 부탁 드립니다.");
                        return;

                    case "P0012":
                    case "P0033":
                    case "P0046":
                        alert("무선 정기결제 상품에서 유무선 정기결제 상품 변경시, 고객센터에 문의 부탁 드립니다.");
                        return;

                    default:
                        alert("결제 오류 고객센터에 문의 부탁 드립니다.");
                        return;
                }

            }

            const params = new URLSearchParams();

            params.append('name', name);
            params.append('id', id);
            params.append('affiliates_code', affiliatesCode);
            params.append('email', email);
            params.append('code', code);
            params.append('store_type', storeType);

            //6개월 이용권
            if (code == "P0030") {
                window.web.callPayTicket("wireless_six_months_ticket", code);
                //12개월 이용권
            } else if (code == "P0031") {
                window.web.callPayTicket("wireless_one_year_ticket", code);
            } else if (code === "P0032" || code === "P0046") {

                fetch(`/ttb/ini/mobile/tikets/tiketsRequestMonthlyPaymentInfo?${params.toString()}`, {
                    method: 'GET',
                    headers: {
                        'User-Agent': useragent
                    },
                    params: {
                        name: name,
                        id: id,
                        affiliates_code: affiliatesCode,
                        email: email,
                        code: code,
                        store_type: storeType
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
                        alert("정기 결제처리 중 오류가 발생했습니다.");
                        // window.location.reload();
                    });
            }

            return;

        } else {
            console.log("code Error");
        }

        confirmModalPay.style.display = "none";
    });

});