
document.addEventListener("DOMContentLoaded", () => {
    const dataInfo = document.querySelector("#dataInfo");
    const confirmModal = document.getElementById('confirmModalDataInfo');
    const confirmSubmitBtn = document.getElementById('confirmSubmitBtnDataInfo');



    dataInfo.addEventListener("click", (e) => {
        console.log("!!!!!!")
        confirmModal.style.display = "block";
    })

    // 모달 바깥 영역 클릭 시 닫기
    confirmModal.addEventListener('click', (e) => {
        if (e.target === confirmModal) {
            confirmModal.style.display = "none";
        }
    });
    
    // 확인버튼
    confirmSubmitBtn.addEventListener('click', (e) => {
        if (e.target === confirmSubmitBtn) {
            confirmModal.style.display = "none";
        }
    });


});