
document.addEventListener("DOMContentLoaded", () => {
    const modal = document.querySelector("#updateUserInfoModal");
    const openModal = document.querySelector("#updateUserInfoBtn");
    const closeModal = document.querySelector("#updateUserInfoModal #closeModal");
    const cancelBtn = document.querySelector("#updateUserInfoModal #cancelBtn");
    const updateUserInfoForm = document.querySelector("#updateUserInfoModal #updateUserInfoForm");
    const id = document.querySelector("#updateUserInfoForm #id").value;
    const affiliatesCode = document.querySelector("#updateUserInfoForm #affiliatesCode").value;
    const approachPath = document.querySelector("#updateUserInfoForm #approachPath").value;
    let userName = document.querySelector("#updateUserInfoForm #userName").value;
    let company = document.querySelector("#updateUserInfoForm #company").value;

    // 모달 열기
    openModal.addEventListener("click", (e) => {
        e.preventDefault();
        modal.style.display = "block";
        // 강제 리플로우
        modal.offsetHeight;
        modal.classList.add("show");
    });

    // 모달 닫기
    const closeModalFunction = () => {
        modal.classList.remove("show");
        // transition이 끝난 후 display none 설정
        setTimeout(() => {
            modal.style.display = "none";
        }, 300); // transition 시간과 동일하게 설정
    };

    closeModal.addEventListener("click", closeModalFunction);
    cancelBtn.addEventListener("click", closeModalFunction);

    // 배경 클릭 시 닫기
    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            closeModalFunction();
        }
    });

    // // 수정 버튼
    updateUserInfoForm.addEventListener("submit", (e) => {
        e.preventDefault();

        userName = document.querySelector("#updateUserInfoForm #userName").value;
        company = document.querySelector("#updateUserInfoForm #company").value;

        const formData = new FormData();
        formData.append('id', id);
        formData.append('approach_path', approachPath);
        formData.append('affiliates_code', affiliatesCode);
        formData.append('name', userName);
        formData.append('company', company);

        console.log("서버로 보내는 데이터:");
        for (const [key, value] of formData.entries()) {
            console.log(`${key}: ${value}`);
        }

        fetch('/ttb/set_user_info', {
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
                console.log("성공:", data);
                window.location.reload();
            })
            .catch((error) => {
                console.error("Error:", error);
                alert("정보 수정 중 오류가 발생했습니다.");
                window.location.reload();
            });
    });



});