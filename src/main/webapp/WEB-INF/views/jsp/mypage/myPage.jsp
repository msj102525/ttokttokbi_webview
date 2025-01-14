<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <% String path=request.getContextPath(); %>
                    <!DOCTYPE html>
                    <html lang="ko">

                    <head>
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                        <meta name="viewport"
                            content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
                        <title>마이페이지</title>
                        <link href="<%=path%>/css/reset.css" rel="stylesheet" />
                        <link href="<%=path%>/css/my_page.css" rel="stylesheet" />
                        <script src="<%=path%>/js/modal/updateUserInfo.js" defer></script>
                        <script src="<%=path%>/js/modal/pay.js" defer></script>
                        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
                        <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
                    </head>

                    <body>
                        <header class="header">
                            <div class="inner-header">
                                <h1>마이페이지</h1>
                            </div>
                        </header>
                        <main class="">
                            <section class="sec1">
                                <div class="inner-section">
                                    <div class="sec1-right">
                                        <p class="userName">${userInfoOutputVo.userName}</p>
                                        <p class="company">${userInfoOutputVo.company}</p>
                                        <p class="userId">${userInfoOutputVo.id}</p>
                                    </div>
                                    <div class="sec1-left">
                                        <div id="updateUserInfoBtn" class="setUserInfoImgBox">
                                            <img src="<%=path%>/img/setUserInfo.png" alt="유저수정이미지">
                                        </div>
                                    </div>
                                </div>
                            </section>

                            <div class="updateUserInfo-modal" id="updateUserInfoModal">
                                <div class="modal-content">
                                    <div class="inner-modal">
                                        <div class="modal-up">
                                            <span class="close-btn" id="closeModal"></span>
                                        </div>
                                        <div class="modal-down">
                                            <p>내 정보 수정</p>
                                            <div class="updateUserInfo-form">
                                                <form id="updateUserInfoForm">
                                                    <ul>
                                                        <li>
                                                            <input type="hidden" id="id" value="${userInfoOutputVo.id}">
                                                        </li>
                                                        <li>
                                                            <input type="hidden" id="affiliatesCode"
                                                                value="${userInfoOutputVo.affiliatesCode}">
                                                        </li>
                                                        <li>
                                                            <input type="hidden" id="approachPath" value="00">
                                                        </li>
                                                        <li>
                                                            <input type="text" id="userName"
                                                                value="${userInfoOutputVo.userName}">
                                                        </li>
                                                        <li>
                                                            <input type="text" id="company"
                                                                value="${userInfoOutputVo.company}">
                                                        </li>
                                                    </ul>
                                                    <div class="button-group">
                                                        <button type="button" class="cancel-btn"
                                                            id="cancelBtn">취소</button>
                                                        <button type="submit" class="submit-btn"
                                                            id="submitBtn">수정</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <section class="sec2">
                                <div class="inner-section">
                                    <div class="sec-up">
                                        <p>이용권 정보</p>
                                    </div>
                                    <div class="sec-down">
                                        <div class="sec-down-left">
                                            <p>${userInfoOutputVo.payTypeName}</p>
                                            <p>이용 기간이</p>
                                            <p><span>${userInfoOutputVo.remainingDays}</span>일 남았어요! 🙂</p>
                                        </div>
                                        <div class="sec-down-right">
                                            <div class="btn-ticket" id="payBtn">
                                                <p>이용권 관리</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>

                            <div class="pay-modal" id="payModal">
                                <div class="modal-content">
                                    <div class="inner-modal">
                                        <div class="modal-up">
                                            <span class="close-btn" id="closeModal"></span>
                                        </div>
                                        <div class="modal-down">
                                            <p>이용권 관리</p>
                                            <ul>
                                                <li id="payTab">
                                                    <p>이용권</p>
                                                    <div class="tab-line"></div>
                                                    <div class="tab-content left">
                                                        <div class="inner-tab-content">
                                                            <h3>일반 이용권</h3>
                                                            <div class="text-box">
                                                                <p>◌ 정기결제의 경우, 이용기간 30일 기준으로 자동으로 결제됩니다.</p>
                                                                <p>◌ 통신사 정책에 따라 일일 무료 문자 초과 시 별도 과금이 청구될 수 있습니다.</p>
                                                                <p>◌ 중도 해지 시 할인 받은 금액 만큼 차감 후 환불 처리 됩니다.</p>
                                                            </div>
                                                            <div class="ticket-box">
                                                                <div class="swiper ticketSwiper">
                                                                    <div class="swiper-wrapper">
                                                                        <div class="swiper-slide" id="ticketContent">
                                                                            <div class="ticket-divider"></div>
                                                                            <div class="ticket-content">
                                                                                <div class="ticket-header">
                                                                                    <h4>무선 12개월 이용권</h4>
                                                                                    <img src="" alt="">
                                                                                </div>
                                                                                <div class="ticket-body">
                                                                                    <p>196,000원</p>
                                                                                    <p>88,000원 <span>(VAT 포함)</span></p>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="swiper-slide" id="ticketContent">
                                                                            <div class="ticket-divider"></div>
                                                                            <div class="ticket-content">
                                                                                <div class="ticket-header">
                                                                                    <h4>무선 정기 이용권</h4>
                                                                                    <img src="" alt="">
                                                                                </div>
                                                                                <div class="ticket-body">
                                                                                    <p>19,800원</p>
                                                                                    <p>9,900원 <span>(VAT 포함)</span></p>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="swiper-slide" id="ticketContent">
                                                                            <div class="ticket-divider"></div>
                                                                            <div class="ticket-content">
                                                                                <div class="ticket-header">
                                                                                    <h4>유무선 정기 이용권</h4>
                                                                                    <img src="" alt="">
                                                                                </div>
                                                                                <div class="ticket-body">
                                                                                    <p>25,400원</p>
                                                                                    <p>14,300원 <span>(VAT 포함)</span></p>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="business-info">
                                                                <p>제이예스(주) 사업자 정보 보기</p>
                                                                <img src="<%=path%>/img/arrow-right.png"
                                                                    alt="오른쪽화살표이미지">
                                                            </div>

                                                            <div class="pay-btn" id="ticketPayBtn">
                                                                <div class="">
                                                                    구매하기
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </li>
                                                <li id="payTab">
                                                    <p>결제 내역</p>
                                                    <div class="tab-line"></div>
                                                    <div class="tab-content right">결제 내역 내용</div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <section class="sec3">
                                <div class="inner-section">
                                    <div class="sec-up">
                                        <p>고객 센터</p>
                                    </div>
                                    <div class="sec-down">
                                        <ul>
                                            <li>
                                                <a href="notice.jsp">공지사항</a>
                                                <div class="arrow-img-box">
                                                    <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                </div>
                                            </li>
                                            <li>
                                                <a href="emailInquiry.jsp">이메일 문의</a>
                                                <div class="arrow-img-box">
                                                    <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                </div>
                                            </li>
                                            <li>
                                                <a href="kakaoInquiry.jsp">카카오톡 문의</a>
                                                <div class="arrow-img-box">
                                                    <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                </div>
                                            </li>
                                            <li>
                                                <a href="faq.jsp">자주 묻는 질문</a>
                                                <div class="arrow-img-box">
                                                    <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                </div>
                                            </li>
                                            <li>
                                                <a href="https://yourdomain.com">똑똑비 홈페이지</a>
                                                <div class="arrow-img-box">
                                                    <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </section>

                            <section class="sec4">
                                <div class="inner-section">
                                    <div class="inner-section">
                                        <div class="sec-up">
                                            <p>똑똑비</p>
                                        </div>
                                        <div class="sec-down">
                                            <ul>
                                                <li>
                                                    <a href="#">환경설정</a>
                                                    <div class="arrow-img-box">
                                                        <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                    </div>
                                                </li>
                                                <li>
                                                    <a href="#">공지사항</a>
                                                    <div class="arrow-img-box">
                                                        <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                    </div>
                                                </li>
                                                <li>
                                                    <a href="#">서비스 이용약관</a>
                                                    <div class="arrow-img-box">
                                                        <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                    </div>
                                                </li>
                                                <li>
                                                    <a href="#">개인정보처리방침</a>
                                                    <div class="arrow-img-box">
                                                        <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                    </div>
                                                </li>
                                                <li>
                                                    <a href="#">명시적 공개 데이터 안내</a>
                                                    <div class="arrow-img-box">
                                                        <img src="<%=path%>/img/arrow-right.png" alt="오른쪽화살표이미지">
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </section>


                        </main>
                    </body>

                    </html>