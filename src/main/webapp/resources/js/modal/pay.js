document.addEventListener("DOMContentLoaded", () => {
    const modal = document.querySelector("#payModal");
    const openModal = document.querySelector("#payBtn");
    const closeModal = document.querySelector("#payModal #closeModal");
    const payTabs = document.querySelectorAll("#payTab");
    const ticketContents = document.querySelectorAll("#ticketContent");
    const ticketPayBtn = document.querySelector("#ticketPayBtn");


    // 모달 열기
    openModal.addEventListener("click", (e) => {
        e.preventDefault();
        modal.style.display = "block";
        modal.offsetHeight;
        modal.classList.add("show");
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

    ticketPayBtn.addEventListener("click", (e) => {
        const activeContent = Array.from(ticketContents).find((tab) =>
            tab.classList.contains("on")
        );

        if (activeContent) {
            const h4Text = activeContent.querySelector("h4")?.textContent || "텍스트 없음";
            console.log("활성화된 탭의 제목:", h4Text);
        } else {
            console.log("활성화된 탭이 없습니다.");
        }
    });



});