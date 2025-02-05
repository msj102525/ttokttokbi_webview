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
                        <title>공지사항</title>
                        <link href="<%=path%>/css/reset.css" rel="stylesheet" />

                        <style>
                            .faq-item {
                                border-bottom: 1px solid #F6F6F6;
                                border-radius: 4px;
                            }

                            .faq-header {
                                padding: 20px;
                                cursor: pointer;
                                display: flex;
                                justify-content: space-between;
                                align-items: center;
                            }

                            .faq-header .faq-title {
                                font-size: 16px;
                                font-weight: 500;
                                line-height: 20px;
                                color: #1D1B20;
                            }

                            .faq-header .faq-date {
                                padding-top: 3px;
                                font-size: 10px;
                                font-weight: 400;
                                line-height: 12px;
                                color: #A2A2A2;
                            }

                            .faq-arrow {
                                transition: transform 0.3s ease;
                                width: 20px;
                                height: 20px;
                            }

                            .faq-arrow.active {
                                transform: rotate(-180deg);
                            }

                            .faq-content {
                                background-color: #F6F6F6;
                                padding: 0;
                                height: 0;
                                overflow: hidden;
                            }

                            .faq-content.active {
                                padding: 15px;
                                height: 300px;
                            }

                            .faq-content .faq-text {
                                font-size: 14px;
                                font-weight: 500;
                                line-height: 17px;
                                color: #1D1B20;
                                overflow-wrap: break-word;
                            }
                        </style>
                    </head>

                    <body>
                        <div class="faq-container">
                            <c:forEach items="${al_tbBoardFaqCategoryContentsList}" var="faq">
                                <div class="faq-item">
                                    <div class="faq-header" onclick="toggleAccordion(this)">
                                        <div class="">
                                            <h3 class="faq-title">${faq.subject}</h3>
                                            <p class="faq-date">${faq.date}</p>
                                        </div>
                                        <div class="faq-arrow">
                                            <img class="faq-arrow" src="<%=path%>/img/chevron-down.png" alt="아래화살표이미지">
                                        </div>
                                    </div>
                                    <div class="faq-content">
                                        <p class="faq-text">${faq.text}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <script>
                            function toggleAccordion(header) {
                                const content = header.nextElementSibling;
                                const arrow = header.querySelector('.faq-arrow');

                                content.classList.toggle('active');
                                arrow.classList.toggle('active');
                            }
                        </script>
                    </body>

                    </html>