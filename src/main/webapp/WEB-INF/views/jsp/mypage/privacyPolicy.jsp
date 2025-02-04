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
                        <title>개인정보처리방침</title>
                        <link href="<%=path%>/css/reset.css" rel="stylesheet" />
                        <style>
                            main {
                                line-height: 16px;
                                font-size: 14px;
                                font-weight: 500;
                                color: #1D1B20;
                                max-width: 100%;
                                margin: 0;
                            }

                            .container {
                                background-color: #F6F6F6;
                                border-radius: 8px;
                                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                            }

                            .inner-container {
                                padding: 20px;
                            }

                            h1 {
                                color: #1a1a1a;
                                margin: 0;
                            }

                            h2 {
                                color: #333;
                            }

                            p {
                                font-size: 14ox;
                                font-weight: 500;
                                line-height: 17px;
                                color: #1D1B20;
                            }

                            .footer {
                                text-align: center;
                                padding: 20px 0;
                            }
                        </style>
                    </head>

                    <body>
                        <div class="container">
                            <div class="inner-container">
                                <main>
                                    <p class="notice">본 방침은 2025년 1월 1일부터 시행됩니다.</p>

                                    <p>회사는 개인정보를 다음과 같은 목적으로 처리합니다. 처리한 개인정보는 본 목적 외의 용도로는 사용되지 않으며, 이용 목적이 변경될 시에는 사전
                                        동의를
                                        구할 예정입니다.</p>

                                    <h2>1. 개인정보의 처리 목적</h2>
                                    <p>회원 가입 및 관리</p>
                                    <p>회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원 자격 유지·관리, 서비스 부정 이용 방지, 고지·통지, 분쟁 조정 기록
                                        보존 등을
                                        위해 개인정보를 처리합니다.</p>
                                    <p>민원사무 처리</p>
                                    <p>민원 처리, 사실 조사 및 통지, 처리결과 통보 등을 위해 개인정보를 처리합니다.</p>
                                    <p>재화 또는 서비스 제공</p>
                                    <p>서비스 제공, 콘텐츠 제공, 맞춤 서비스 제공 등을 위해 개인정보를 처리합니다.</p>
                                    <p>마케팅 및 광고 활용</p>
                                    <p>이벤트 및 광고성 정보 제공, 서비스 유효성 확인, 고객의 서비스 선호도 파악 등을 위해 개인정보를 처리합니다.</p>

                                    <h2>2. 개인정보 파일 현황</h2>
                                    <p>개인정보 파일명 : 고객 개인정보 처리 현황</p>
                                    <p>개인정보 항목 : 이메일, 휴대전화번호, 이름, 서비스 이용 기록, 접속 로그, 결제기록</p>
                                    <p>수집방법 : 어플리케이션, 서면양식, 전화/팩스, 제휴사 제공</p>
                                    <p>보유근거 : 서비스 제공 관련 보관</p>
                                    <p>보유기간 : 서비스 이용 기간, 해지 후 1년</p>
                                    <p>관련법령 : </p>
                                    <p>신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년</p>
                                    <p>소비자의 불만 또는 분쟁처리에 관한 기록 : 3년</p>
                                    <p>대금결제 및 재화 등의 공급에 관한 기록 : 5년</p>
                                    <p>계약 또는 청약철회 등에 관한 기록 : 5년</p>
                                    <p>표시/광고에 관한 기록 : 6개월</p>

                                    <h2>3. 개인정보의 처리 및 보유 기간</h2>
                                    <p>① 회사는 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보 수집 시 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리하고
                                        보유합니다.</p>
                                    <p>② 개인정보 처리 및 보유 기간</p>
                                    <p>모바일 어플리케이션 회원가입 및 관리</p>
                                    <p>개인정보는 수집 및 이용에 관한 동의일로부터 서비스 이용 기간, 해지 후 5년까지 보유·이용됩니다.</p>
                                    <p>보유 근거 : 서비스 제공</p>
                                    <p>관련법령 : </p>
                                    <p>신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년</p>
                                    <p>소비자의 불만 또는 분쟁처리에 관한 기록 : 3년</p>
                                    <p>대금결제 및 재화 등의 공급에 관한 기록 : 5년</p>
                                    <p>계약 또는 청약철회 등에 관한 기록 : 5년</p>
                                    <p>표시/광고에 관한 기록 : 6개월</p>
                                    <p>예외사유 : 없음</p>

                                    <h2>4. 개인정보의 제3자 제공에 관한 사항</h2>
                                    <p>① 회사는 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.
                                    </p>
                                    <p>② 수집 목적과 합리적으로 관련된 범위에서는 법령에 따라 이용자의 동의 없이 개인정보를 이용하거나 제3자에게 제공할 수 있습니다. 이때 ‘당초
                                        수집
                                        목적과 관련성이 있는지, 수집한 정황 또는 처리 관행에 비추어 볼 때 개인정보의 추가적인 이용 또는 제공에 대한 예측 가능성이 있는지, 이용자의
                                        이익을
                                        부당하게 침해하는지, 가명처리 또는 암호화 등 안전성 확보에 필요한 조치를 하였는지’를 종합적으로 고려합니다.</p>
                                    <p>제이예스는 수집한 개인정보를 특정 개인을 알아볼 수 없도록 가명처리하여 통계작성, 과학적 연구, 공익적 기록보존 등을 위하여 처리할 수 있습니다.
                                        이 때
                                        가명정보는 재식별되지 않도록 추가정보와 분리하여 별도 저장・관리하고 필요한 기술적・관리적 보호조치를 취합니다.</p>
                                    <p>먼저, 가명정보에 접근할 수 있는 권한은 최소한의 인원으로 제한하며, 접근 권한을 관리하고 있습니다. 가명정보를 보호하기 위해 보안 시스템을
                                        운영하며,
                                        정기적인 내부 감사를 통해 가명처리 및 보호조치가 적절하게 이행되고 있는지 확인하고 개선 사항을 지속적으로 반영합니다. 또한, 가명정보를
                                        취급하는
                                        직원들에게 정기적으로 교육을 실시하고 있습니다</p>

                                    <h2>5. 개인정보 처리 위탁</h2>
                                    <p>① 회사는 원활한 개인정보 업무처리를 위해 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.</p>
                                    <p>서비스 이용요금 결제</p>
                                    <p>위탁받는 자 (수탁자) : KG 이니시스</p>
                                    <p>위탁 업무의 내용 : 구매 및 요금 결제, 본인 인증(금융 거래, 금융 서비스)</p>
                                    <p>위탁기간 : 서비스 이용 기간, 해지 후 5년</p>
                                    <p>② 회사는 위탁계약 체결 시 개인정보 보호법 제25조에 따라 위탁업무 수행 목적 외 개인정보 처리 금지, 기술적·관리적 보호조치, 재위탁 제한,
                                        수탁자에
                                        대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독합니다.</p>
                                    <p>③ 위탁업무의 내용이나 수탁자가 변경될 경우, 지체 없이 본 개인정보 처리방침을 통해 공개하겠습니다.</p>

                                    <h2>6. 정보주체와 법정대리인의 권리·의무 및 그 행사방법</h2>
                                    <p>이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.</p>
                                    <p>① 정보주체는 제이예스 주식회사에 대해 언제든지 개인정보 열람, 정정, 삭제, 처리정지 요구 등의 권리를 행사할 수 있습니다.</p>
                                    <p>② 제1항에 따른 권리 행사는 제이예스 주식회사에 대해 개인정보 보호법 시행령 제41조 제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을
                                        통하여
                                        하실 수 있으며 회사는 이에 대해 지체 없이 조치하겠습니다.</p>
                                    <p>③ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙
                                        별지
                                        제11호 서식에 따른 위임장을 제출하셔야 합니다.</p>
                                    <p>④ 개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제5항, 제37조 제2항에 의하여 정보주체의 권리가 제한될 수 있습니다.</p>
                                    <p>⑤ 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</p>
                                    <p>⑥ 회사는 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를
                                        확인합니다.
                                    </p>

                                    <h2>7. 처리하는 개인정보의 항목</h2>
                                    <p>① 회사는 다음의 개인정보 항목을 처리하고 있습니다.</p>
                                    <p>모바일 어플리케이션 회원가입 및 관리</p>
                                    <p>필수 항목 : 이메일, 휴대전화번호, 이름, 상호(회사)명</p>
                                    <p>선택 항목 : 없음</p>

                                    <h2>8. 개인정보의 파기</h2>
                                    <p>회사는 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체 없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.
                                    </p>
                                    <p>파기 절차</p>
                                    <p>이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정 기간 저장된 후
                                        혹은
                                        즉시 파기됩니다. 이때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.</p>
                                    <p>파기 기한</p>
                                    <p>이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지,
                                        사업의
                                        종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.
                                    </p>
                                    <p>파기 방법</p>
                                    <p>전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 파기합니다.</p>
                                    <p> 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.</p>

                                    <h2>9. 개인정보 보호책임자</h2>
                                    <p>① 회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이
                                        개인정보
                                        보호책임자를 지정하고 있습니다.</p>

                                    <p>개인정보 보호책임자</p>
                                    <p>성명 : 정광재</p>
                                    <p>직책 : 서비스기획팀</p>
                                    <p>직급 : 실장</p>
                                    <p>연락처 : slaggy@jyes.co.kr, 02-2655-9603</p>

                                    <p>개인정보 보호 담당부서</p>
                                    <p>부서명 : 서비스기획팀</p>
                                    <p>담당자 : 정광재</p>
                                    <p>연락처 : slaggy@jyes.co.kr, 02-2655-9603</p>
                                    <p>② 정보주체께서는 회사의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보
                                        보호책임자 및 담당부서로 문의하실 수 있습니다. 회사는 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.</p>

                                    <h2>10. 개인정보 처리방침 변경</h2>
                                    <p>① 해당 개인정보 처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경 내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일
                                        전부터
                                        공지사항을 통하여 고지할 것입니다.</p>

                                    <h2>11. 개인정보의 안전성 확보 조치</h2>
                                    <p>정기적인 자체 감사 실시</p>
                                    <p>개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.</p>
                                    <p>개인정보 취급 직원의 최소화 및 교육</p>
                                    <p>개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화하여 개인정보를 관리하는 대책을 시행하고 있습니다.</p>
                                    <p>내부관리계획의 수립 및 시행</p>
                                    <p>개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</p>
                                    <p>해킹 등에 대비한 기술적 대책</p>
                                    <p>회사는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위해 보안 프로그램을 설치하고 주기적인 갱신·점검을 하며, 외부로부터
                                        접근이
                                        통제된 구역에 시스템을 설치하고 기술적/물리적으로 감시 및 차단하고 있습니다.</p>
                                    <p>개인정보의 암호화</p>
                                    <p>이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있으며, 본인만이 알 수 있습니다. 중요한 데이터는 파일 및 전송 데이터를
                                        암호화하거나 파일
                                        잠금 기능을 사용하는 등의 별도 보안 기능을 사용하고 있습니다.</p>
                                    <p>접속 기록의 보관 및 위변조 방지</p>
                                    <p>개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관·관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 보안 기능을 사용하고
                                        있습니다.
                                    </p>
                                    <p>개인정보에 대한 접근 제한</p>
                                    <p>개인정보를 처리하는 데이터베이스 시스템에 대한 접근권한의 부여, 변경, 말소를 통하여 개인정보에 대한 접근 통제를 위하여 필요한 조치를 하고
                                        있으며,
                                        침입 차단 시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</p>
                                    <p>문서 보안을 위한 잠금 장치 사용</p>
                                    <p>개인정보가 포함된 서류, 보조 저장 매체 등을 잠금 장치가 있는 안전한 장소에 보관하고 있습니다.</p>
                                    <p>비인가자에 대한 출입 통제</p>
                                    <p>개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고, 이에 대해 출입 통제 절차를 수립, 운영하고 있습니다.</p>

                                    <h2>12. 개정 전 공지의무</h2>
                                    <p>앱스토어 정책변경, 법령 변경, 서비스 정책 및 기능 변경사항을 반영하기 위한 목적 등으로 개인정보 처리방침을 수정할 수 있습니다. 개인정보
                                        처리방침이
                                        변경되는 경우 최소 7일 전 변경 사항을 사전에 안내 하겠습니다.</p>
                                    <p>다만, 이용자 권리의 중대한 변경이 발생할 때에는 최소 30일 전에 미리 알려드리겠습니다.</p>
                                    <p>제이예스 주식회사는 이용자 여러분의 정보를 소중히 생각하며, 이용자가 더욱 안심하고 서비스를 이용할 수 있도록 최선의 노력을 다할것을
                                        약속드립니다.
                                    </p>

                                    <div class="footer">
                                        <p>공고일자: 2025년 01월 01일<br>
                                            시행일자: 2025년 01월 20일</p>
                                        <p>© 제이예스 주식회사. All rights reserved.</p>
                                    </div>
                                </main>
                            </div>
                        </div>
                    </body>

                    </html>