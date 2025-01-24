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
                        <title>서비스 이용약관</title>
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
                                padding: 10px;
                                border-radius: 8px;
                                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                            }

                            .header {
                                text-align: center;
                                margin-bottom: 20px;
                                padding-bottom: 10px;
                                border-bottom: 2px solid #e0e0e0;
                            }

                            h1 {
                                color: #1a1a1a;
                                margin: 0;
                            }

                            h2 {
                                color: #333;
                                border-bottom: 1px solid #e0e0e0;
                                padding-bottom: 5px;
                                margin-top: 25px;
                            }

                            p {
                                margin-bottom: 15px;
                            }

                            .notice {
                                background-color: #f9f9f9;
                                border-left: 4px solid #007bff;
                                padding: 10px;
                                margin: 15px 0;
                            }

                            .footer {
                                text-align: center;
                                margin-top: 30px;
                                color: #666;
                                font-size: 0.9em;
                            }
                        </style>
                    </head>

                    <body>
                        <div class="container">
                            <header class="header">
                                <h1>제이예스 주식회사 서비스 이용약관</h1>
                            </header>

                            <main>
                                <p class="notice">이용약관 제 1 장 총 칙</p>

                                <p>
                                    JYES Smart Auto Message 콜백메시지서비스(이하 “서비스”라함, 공식사이트 : https://www.sohomsg.kr/)는 사용자가
                                    보유한 휴대폰을 이용하여 이동 통신사 와 약정된 메시지 발송 조건에 따라 본 “서비스:를 이용하여 사용자가 쉽고 간편하게 자동 및 수동으로 콜백메시지를
                                    전송할 수 있도록 서비스를 제공합니다.
                                </p>

                                <h2>제 2 조 [용어의 정의]</h2>
                                <p>① 이 약관에서 사용하는 용어의 정의는 다음과 같습니다.</p>
                                <p>1. 서비스 : 사용자가 전송하고자 하는 내용ㆍ정보 등을 문자메시지(SMS), 멀티미디어메시지(MMS) 및 기타 음성, 팩스, 알림톡 등의 다양한 형태로
                                    가공·변환시켜 전송하여 제3자가 콜백 메시지의 형태로 수신할 수 있도록 하는 서비스입니다.</p>
                                <p>2. 계약자 : “회사”와 “서비스” 이용계약을 체결한 자.</p>
                                <p>2-1. 이용자 : “회사”와 “서비스” 이용계약을 체결하거나 그 이용을 “회사”가 동의한 자.</p>
                                <p>3. 이용신청 : “회사”가 정한 별도의 기준과 절차에 따라 “서비스” 이용을 신청하는 것.</p>
                                <p>4. 이용계약 : “서비스”를 제공 받기 위하여 “회사”와 이용자 간에 체결하는 계약.</p>
                                <p>5. 결재 : “회사”가 정한 결제시스템(PG 등)에 이용자의 금융정보를 입력 후 서비스 이용을 위하여 사용료를 지급하는 것.</p>
                                <p>6. 이용정지 : 본 약관에서 정한 일정한 요건에 따라 일정기간 이용자의 “서비스” 이용이 보류하는 것.</p>
                                <p>7. 이용해지 : 이용자가 “서비스”를 일시적으로 사용중지 하는 것.</p>
                                <p>8. 탈퇴 : “회사”와 이용자가 간 체결한 본 “서비스” 이용계약을 해지하는 것.</p>
                                <p>9. ID(아이디) : 이용자의 식별과 “서비스” 이용을 위하여 이용자에 의해 정해지고, 회사가 승인하는 문자와 숫자의 조합을 의미함.</p>
                                <p>10. 휴면계정 : 24개월 이상 계속해서 로그인을 포함한 서비스 이용이 없는 아이디 또는 전화번호.</p>
                                <p>11. 스팸메시지 : 수신자의 의사에 반하여 정보통신망을 통해 일방적으로 전송 또는 게시되는 영리 목적의 광고성 정보.</p>
                                <p>12. 문자 피싱 메시지 : 전자금융사기를 목적으로 전송 또는 게시되는 정보.</p>
                                <p>13. 모바일 폰 : 고객이 사용하고자 등록한 휴대폰 등을 통칭한다.</p>
                                <p>14. 어플리케이션(Application) : 휴대폰에서 콜백 메시지 서비스의 기능이 되도록 만든 프로그램.</p>
                                <p>15. 발신자 : 아래 항목 중 하나에 해당하는 자로서 본 약관상 이용자 또는 이용자의 고객(이용자로부터 서비스를 임대 또는 제공받아 이용하는 제3자)
                                </p>
                                <p>가. 본 서비스를 활용하여 다양한 콜백메시지를 발송하는 자</p>
                                <p>나. 자기 또는 자기가 보유한 브랜드와 동일한 명의로 콜백메시지를 발송하는 자.</p>
                                <p>16. 수신자 : 이용자의 고객 또는 이용자에게 전화하여 서비스를 이용하여 콜백메시지를 수신하는 자.</p>
                                <p>17. 사이버캐쉬 : “회사”가 “서비스” 이용을 위해 제공하는 결제시스템을 통하여 생성한 디지털 화폐를 의미함.</p>
                                <p>② 본 약관에 정의된 용어의 적용범위는 본 약관에 한하며, 정의되지 않은 용어는 일반 관례에 따른다.</p>

                                <h2>제 3 조 [약관의 게시와 개정]</h2>
                                <p>① “회사”는 이 약관의 내용을 이용자가 쉽게 알 수 있도록 가입 시 동의 또는 서비스 초기화면 및 사용 메뉴 등에 게시한다.</p>
                                <p>② “회사”는 “약관의 규제에 관한 법률”(이하“약관법”), "정보통신망 이용촉진 및 정보보호 등에 관한 법률”(이하 "정보통신망법”),
                                    "전자상거래등에서의 소비자보호에 관한법률”(이하“전자상거래법”) 등 관련법령에 위배하지 않는 범위에서 이 약관을 개정할 수 있다.</p>
                                <p>③ “회사”가 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현 행약관과 함께 제①항의 방식에 따라 그 개정약관의 적용일자 7일 전부터 적용일자
                                    전까지 공지한다. 다만, 이용자의 권리 또는 의무에 관한 중요한규정의 변경은 최소한 30일 전에 공지하고, 일정기간 서비스 내 공지사항, 전자우편,
                                    로그인시 동의창 등의 전자적 수단을 통해 따로 명확히 통지하도록 한다.</p>
                                <p>④ “회사”가 전항에 따라 이용자에게 “개정된 약관의 적용일까지 이에 대한 거부 의사를 표시하지 않으면 동의한 것으로 본다”는 뜻을 공지하거나 통지한
                                    경우, 공고 또는 통지한 날로부터 7일 이내에 명시적으로 거부의 의사표시를 하지 아니한 경우 이용자는 개정된 약관에 동의한 것으로 본다.</p>
                                <p>⑤ 이용자가 개정된 약관의 적용에 동의하지 아니한 경우 회사는 개정된 약관의 내용을 이용자에게 적용할 수 없으며, 이 경우 이용자는 이용계약을 해지할 수
                                    있다. 다만, 회사는 개정된 약관을 이용자에게 적용할 수 없는 특별한 사정이 있는 경우에는 이용자의 동의하에 그 이용계약을 해지할 수있다.</p>
                                <p>⑥ 이용자는 변경된 약관의 내용에 대하여 숙지할 주의의무를 다하여야 하며, 부주의로 숙지하지 못한 변경된 약관으로 인한 이용자의 피해는 회사가 책임지지
                                    않는다.</p>
                                <p>⑦ 이 약관의 적용기간은 이용자의 가입일부터 탈퇴일까지로 한다. 단, 채권 또는 채무관계가 있을 경우에는 채권, 채무의 완료일까지로 한다.</p>

                                <p class="notice">제 2 장 이용자의 이용계약 및 관리</p>

                                <h2>제 4 조 [이용계약 체결]</h2>
                                <p>① 이용계약은 이용자가 되고자 하는 자(이하 "가입신청자")가 본 약관의 내용에 대하여 동의를 한 다음 회원가입 신청을 하고, “회사”가 이러한 신청에
                                    대하여 승낙함으로써 체결된다.</p>
                                <p>② “회사”는 “가입신청자”의 신청에 대하여 서비스 이용을 승낙함을 원칙으로 한다. 다만, “회사”는 다음 각 호의 어느 하나에 해당하는 경우 그 승낙을
                                    거절할 수 있다.</p>
                                <ul>
                                    <li>1. “가입신청자”가 본 약관에 의하여 이전에 이용자 자격을 상실한 적이있는 경우, 단 회사가 이용자의 재가입을 승낙한 경우에는 예외로 한다.
                                    </li>
                                    <li>2. 실명이 아니거나 타인의 명의를 이용함이 확인된 경우.</li>
                                    <li>3. 허위의 정보를 기재하거나, 회사가 제시하는 내용을 기재하지 않은 경우.</li>
                                    <li>4. 만 14세 미만의 가입자인 경우.</li>
                                    <li>5. 회사가 제공하는 모든 서비스 중 어느 하나에 대하여 제18조[서비스 이용의 탈퇴·해지 및 환급] 제③항에 따라 회사로부터 계약 해지를 당한
                                        후 1년이 경과하지 않은 경우.</li>
                                    <li>6. 최초 서비스 이용에 대한 대가를 회사가 정한 일정기간(무료 이용기간 만료일 등)까지 결제하지 않을 경우.</li>
                                    <li>7. 이용자가 서비스의 정상적인 제공을 저해하거나 다른 이용자의 서비스 이용에 지장을 줄 것으로 예상되는 경우.</li>
                                    <li>8. 이용자의 서비스 이용이 관련법령 등에 따라 금지되거나, 기타 사회질서등 미풍양속에 반할 우려가 있다고 인정하는 경우.</li>
                                </ul>
                                <p>③ 제1항에 따른 신청에 대하여 회사는 그 승낙을 하지 전 이용자의 종류에 따라 전문기관을 통한 실명확인 및 본인인증을이용자에게 요청할 수 있다.</p>
                                <p>④ 회사는 서비스 관련 설비의 여유가 없거나 기술상 또는 업무상 문제가 있는 경우에는 승낙을 유보할 수 있다.</p>
                                <p>⑤ 이용계약의 성립한 시기는 회사가 가입완료를 신청절차 상에 표시한 시점으로 한다.</p>
                                <p>⑥ 회사는 이용자에 대해 회사정책에 따라 등급별로 구분하여 이용시간, 이용횟수, 서비스 메뉴 등을 세분하여 그 이용에 차등을 둘 수 있다.</p>
                                <p>⑦ 회사는 서비스의 대량이용 등 특별한 이용에 관한 계약은 별도의 계약을 통하여 제공한다.</p>

                                <h2>제 5 조 [개인정보 수집]</h2>
                                <p>① 회사는 적법하고 공정한 수단에 의하여 이용계약의 성립 및 이행에 필요한 최소한의 개인정보를 수집하며, 이용자는 최소한이 개인정보수집에 동의하여야
                                    한다.</p>
                                <p>② 회사는 개인정보의 수집 시 관련법규에 따라 개인정보취급방침에 그 수집범위 및 목적을 사전 고지한다.</p>
                                <p>③ 회사는 이용자가 서비스 화면에서 자신의 개인정보에 대한 수집, 이용 또는 제공에 대한 동의를 철회할 수 있도록 필요한 조치를 취해야 한다.</p>

                                <h2>제 6 조 [개인정보보호 의무]</h2>
                                <p>회사는 “개인정보보호법”, “정보통신망법” 등 관계법령이 정하는 바에따라 이용자의 개인정보를 보호하기 위해 노력하고, 개인정보의 보호 및 사용에 대해서는
                                    관련법령 및 회사의 개인정보처리방침이 적용된다. 다만, 회사의“공식 사이트” 이외의 “링크된 사이트”에서는 회사의 개인정보처리방침이 적용되지 않는다.
                                </p>

                                <h2>제 7 조 [개인정보의 변경]</h2>
                                <p>① 이용자는 개인정보설정 화면을 통하여 본인의 정보를 열람 및 수정할 수 있다. 다만, 법인 이용자는 홈페이지 및 어플리케이션(Application)에
                                    기재된 “회사”의 전화 및 전자메일로 법인 이용자의 정보 수정을 요청하여야 한다.</p>
                                <p>② 이용자는 서비스 이용신청 시 기재한 사항이나 서비스 이용과정에서 회사에 제공한 정보가 변경되었을 경우 전항의 방법으로 이용자 정보를 변경하여야 하며,
                                    변경하지 않음으로써 발생한 불이익에 대하여 회사는 책임을 지지 아니한다.</p>

                                <h2>제 8 조 [이용자에 대한 통지]</h2>
                                <p>① 회사가 이용자에 대한 통지를 하는 것은, 서비스 홈페이지 또는 공지사항 등에 공지하여 통지하며, 본 약관에 별도 규정이 없는 한 이용자의 등록된
                                    문자메시지 등으로 통지할 수 있다. 단, 이용자가 전화번호 등의 정보를 허위로 제출하거나 변경된 정보를 회사에 알림지 않은 경우 회사는 이용자가 제출한
                                    정보로 발송한 때에 이용자에게 도달한 것으로 반가할 수 있다.</p>
                                <p>② 회사는 전체 또는 불티정 다수 이용자에 대한 통지의 경우 7일 이상 서비스 홈페이지 또는 공지사항 등에 공지함으로써 제①항의 통지에 감을 수있다.
                                </p>
                                <p>③ 이용자는 회사에 실제로 연락이 가능한 전화번호 등의 정보를 제공하여 해당 정보들을 최신으로 유지해야 한다.</p>

                                <p class="notice">제 3 장 계약당사자의 권리 및 의무</p>

                                <h2>제 9 조 [회사의 의무]</h2>
                                <p>① 회사는 관련법령과 본 약관이 금지하거나 미풍양속에 반하는 행위를 하지 않으며, 계속적이고 안정적인 서비스를 제공하기 위해 최선을 다하여 노력하여야
                                    한다.</p>
                                <p>② 회사는 이용자가 안전하게 서비스를 이용할 수 있도록 개인정보(신용정보 포함) 보호를 위해 보안시스템을 갖추어야 하며, 개인정보처리방침을 공시하고
                                    준수한다.</p>
                                <p>③ 회사는 서비스 제공과 관련하여 알고 있는 이용자의 개인정보를 본인의 승낙 없이 제3자에게 누설, 배포하지 않는다. 다만, 관계법령에 의한
                                    관계기관으로부터의 요청 등 법률의 규정에 따른 적법한 절차에 의한 경우에는 그러하지 아니한다.</p>
                                <p>④ 회사는 서비스 제공목적에 맞는 서비스 이용 여부를 확인하기 위하여 상시적으로 모니터링을 실시하며, 서비스 제공을 위한 시스템에 장애가 발생하거나 또는
                                    그 시스템이 고장 발생 시에는 이를 최대한 신속히 수리 또는 복구한다. 다만, 천재지변, 비상사태 또는 그 밖에 부득이한 경우에는 그 서비스를 일시
                                    중단하거나 중지할 수 있다.</p>
                                <p>⑤ 회사는 서비스의 안정화 및 고도화 그리고 기능 개선 등의 사유로 공지사항 등을 통하여 해당 사실을 알리고, 서비스에 필요한
                                    어플리케이션(Application) 및 관련 소프트웨어의 업그레이드, 서비스 관련 시스템의 점검 등을 실시할 수 있다. 또한, 회사의 판단에 따라
                                    서비스의 운영에 긴급한 사안이 발생할 경우 사전 고지 없이 서비스의 업그레이드(Upgrade) 및 관련 시스템의 점검 등을 실시할 수 있다.</p>
                                <p>⑥ 서비스의 특성상 이용자와 이동통신사와의 약정에 의하여 정해진 메시지 발송 조건에 따라 서비스를 제공하는 것으로 전체 발송 건수 및 일일 발송 내용을
                                    이용자가 확인할 수 있도록 서비스에 표시해 줌으로써 이용자가 수시로 이를 관리할 수 있도록 지원한다.</p>
                                <p>⑦ 회사는 스팸 메시지 또는 스미싱(이하 통칭하여 “불법스팸”)의 발송을 방지하기 위하여 불법스팸으로 인지되는 메시지에 대해 필터링 또는 차단할 수
                                    있으며, “불법스팸”을 전송한 사실을 확인한 경우, 한국인터넷진흥원 불법스팸대응센터에 관련 자료를 첨부하여 신고할 수 있다.</p>
                                <p>⑧ 회사는 불법스팸 수신 거부처리 등 불법스팸 관련 민원을 자체적으로 처리하기 위한 고충처리창구를 운영한다. [※ 고충처리창구 처리상담: 고객센터,
                                    카카오톡 문의 및 이메일 문의]</p>
                                <p>⑨ 회사는 공공질서 또는 미풍양속을 해하는 내용에 대한 경찰서, 전파관리소, 방송통신위원회 등 수사·감독기관의 수사·감독 협조를 위해 이용고객으로부터
                                    전송된 메시지에 대한 전송 로그를 1년간 보관한다. 이 기간이 경과한 메시지 로그에 대해서는 자동 삭제되며, 삭제된 로그에 대해서는 어떠한 경우에도
                                    제공하지 아니한다.</p>
                                <p>⑩ 회사는 이용자로부터 제기되는 의견이나 불만이 정당하다고 인정되는 경우 이를 즉시 처리한다. 이때, 이용자가 이용신청 시 기입한 (휴대)전화번호나
                                    이메일로 처리 과정 및 결과를 통보할 수 있다.</p>
                                <p>⑪ 회사는 전기통신사업법 제84조의2 및 과학기술정보통신부고시 제2023-23호 “거짓으로 표시된 전화번호로 인한 피해 예방 등에 관한 고시”(이하
                                    “고시”)에 따라 발신번호 사전등록서비스를 운영할 수 있으며, 이용자가 전기통신사업법 제84조의2 제1항을 위반하여 발신번호를 변작하는 등 거짓으로
                                    표시하는 경우, 회사는 해당 메시지의 전송을 차단할 수 있다. 단, “고시” 제7조 제2항에 따라 회사는 전단의 메시지 전송을 지체 없이 차단한 후
                                    당해 차단 사실을 이용자에게 통지하여야 한다.</p>
                                <p>⑫ 전항의 경우 회사는 “고시” 제16조 제1항에 따라 차단된 메시지에 관한 자료(변작된 발신번호, 차단 시각, 전송자명 등)를 차단 후 1년간
                                    보관·관리하고, 이를 한국인터넷진흥원장에게 제출한다.</p>
                                <p>⑬ 회사는 이용자가 앱 내에서 제공되는 상권 분석 서비스를 사용할 수 있도록 최신 데이터를 기반으로 지역 정보를 업데이트하며, 상권 분석 결과에 따라
                                    발생할 수 있는 문제에 대해 법적으로 허용되는 범위 내에서 책임을 진다.</p>
                                <p>⑭ 회사는 상권 분석 서비스의 정확성을 유지하기 위해 외부 데이터 소스와 협력하며, 데이터 갱신 주기와 주요 변경 사항에 대해 이용자에게 공지한다.
                                </p>

                                <h2>제 10 조 [이용자의 의무]</h2>
                                <p>① 이용자는 다음 행위를 하여서는 아니 된다.</p>
                                <ul>
                                    <li>이용신청 또는 변경 시 허위사실을 기재하거나, 다른 회원의 ID와 비밀번호 및 전화번호 등을 도용, 부정하게 사용하는 행위.</li>
                                    <li>회사의 서비스 정보를 이용하여 얻은 정보를 회사의 사전 승낙 없이 복제 또는 유통시키거나 상업적으로 이용하는 행위.</li>
                                    <li>타인의 명예를 손상시키거나 불이익을 주는 행위.</li>
                                    <li>게시판 등에 음란물을 게재하거나 음란사이트를 연결(링크)하는 행위.</li>
                                    <li>회사의 저작권, 제3자의 저작권 등 기타 권리를 침해하는 행위.</li>
                                    <li>공공질서 및 미풍양속에 위반되는 내용의 정보, 문장, 도형, 음성 등을 타인에게 유포하는 행위.</li>
                                    <li>서비스와 관련된 설비의 오동작이나 정보 등의 파괴 및 혼란을 유발시키는 컴퓨터 바이러스 감염 자료를 등록 또는 유포하는 행위.</li>
                                    <li>서비스 운영을 고의로 방해하거나 서비스의 안정적 운영을 방해할 수 있는 정보 및 수신자의 명시적인 수신거부 의사에 반하여 광고성 정보를 전송하는
                                        행위.</li>
                                    <li>타인으로 가장하는 행위 및 타인과의 관계를 허위로 명시하거나 다른 회원의 개인정보를 수집, 저장, 공개하는 행위.</li>
                                    <li>자기 또는 타인에게 재산상의 이익을 주거나 타인에게 손해를 가할 목적으로 허위의 정보를 유통시키거나, 재물을 걸고 도박하거나 사행행위를 하는
                                        행위.</li>
                                    <li>윤락행위를 알선하거나 음행을 매개하는 내용의 정보를 유통시키는 행위.</li>
                                    <li>수치심이나 혐오감 또는 공포심을 일으키는 말이나 음향, 글이나 화상 또는 영상을 계속하여 상대방에게 도달하게 하여 상대방의 일상적 생활을
                                        방해하는 행위.</li>
                                    <li>관련 법령에 의하여 그 전송 또는 게시가 금지되는 정보(컴퓨터 프로그램 포함)의 전송 또는 회사의 직원이나 운영자를 가장하거나 사칭하여 게시하고
                                        또는 타인의 명의를 도용하여 메시지 발송하는 행위.</li>
                                    <li>컴퓨터 소프트웨어, 하드웨어, 전기통신 장비의 정상적인 가동을 방해, 파괴할 목적으로 고안된 소프트웨어 바이러스, 기타 다른 컴퓨터 코드,
                                        파일, 프로그램을 포함하고 있는 자료를 게시하거나 이메일 또는 서비스를 이용하여 발송하는 행위.</li>
                                    <li>서비스를 이용하여 특정 대상에 대하여 스토킹(stalking) 등을 통해 괴롭히는 행위.</li>
                                    <li>서비스 이용 및 사용에 있어 이용대금, 기타 서비스 이용과 관련하여 회원이 부담하는 채무를 기일에 지급하지 않는 행위.</li>
                                    <li>다른 사람의 서비스 이용을 방해하거나 그 정보를 도용하는 등 전자거래질서를 위협하는 행위.</li>
                                    <li>회사의 동의 없이 회사의 서비스를 이용하여 얻은 정보를 복사, 복제, 변경, 번역, 출판, 방송 및 기타의 방법으로 사용하거나 이를 타인에게
                                        제공하는 행위.</li>
                                    <li>전기통신사업법 제84조의2 제1항을 위반하여 메시지 전송 시 발신번호를 변작하는 등 거짓으로 표시하는 행위.</li>
                                    <li>기타 불법적이거나 부당한 행위.</li>
                                </ul>
                                <p>② 이용자는 다음과 같이 대한민국 법률에 위반되는 정보를 발송하면 아니 된다.</p>
                                <ul>
                                    <li>청소년보호법에 따른 청소년 유해매체물로서 상대방의 연령 확인, 표시 의무 등 법령에 따른 의무를 이행하지 아니하고 영리를 목적으로 제공하는
                                        내용의 정보.</li>
                                    <li>음란한 부호·문언·음향·화상 또는 영상을 배포·판매·임대하거나 공공연하게 전시하는 내용의 정보.</li>
                                    <li>사람을 비방할 목적으로 공공연하게 사실이나 거짓의 사실을 드러내어 타인의 명예를 훼손하는 내용의 정보.</li>
                                    <li>법령에 따라 분류된 비밀 등 국가기밀을 누설하는 내용의 정보 및 국가보안법에서 금지하는 행위를 수행하는 내용의 정보.</li>
                                    <li>그 밖에 범죄를 목적으로 하거나 교사(敎唆) 또는 방조하는 내용의 정보.</li>
                                    <li>기타 불법적이거나 부당한 정보(음란성 게시물, 악성코드 유포 게시물, 지적재산권 위반 게시물 등).</li>
                                </ul>
                                <p>③ 회사는 이용자가 제①항 및 제②항의 행위를 하는 경우 이용자의 서비스 이용을 즉시 정지하고, 일방적으로 본 계약에 대한 사전 고지 없이 해지할 수
                                    있다.</p>
                                <p>④ 이용자는 “정보통신망법”상의 광고성 정보 전송 시 의무사항 및 회사의 이용약관을 준수하여야 한다.</p>
                                <p>⑤ 회사는 이용자가 메시지를 발송하는 데 필요한 플랫폼과 서비스를 제공하며, 메시지 발송의 주체는 서비스를 사용하는 이용자로서 수신자의 동의 없이 광고성
                                    메시지 또는 스팸 메시지·문자피싱 메시지 전송 등 불법행위를 하여서는 아니 된다.</p>
                                <p>⑤-1. 이용자는 “정보통신망법” 제50조(영리목적의 광고성 정보 전송 제한)의 규정에 따라 그 수신인의 명시적인 사전 동의를 받아 메시지를 발송하여야
                                    하며, “전기통신사업법” 등 관련 법령 등의 미준수로 인하여 발생하는 모든 민·</p>

                                <p class="notice">제 4 장 이용자의 권리에 관한 조치</p>

                                <h2>제 12 조 [동의의 철회]</h2>
                                <p>회사는 이용자가 서비스 화면에서 자신의 개인정보에 대한 수집, 이용 또는 제공에 대한 동의를 철회할 수 있도록 필요한 조치를 취하여야 한다.</p>

                                <h2>제 13 조 [불만 처리]</h2>
                                <p>① 회사는 개인정보와 관련하여 이용자의 의견을 수렴하고, 불만을 처리하기 위한 절차를 마련하여야 한다.</p>
                                <p>② 회사는 전화, 전자우편, 고객센터 또는 서비스 화면의 상담창구를 통하여 이용고객의 불만사항을 접수, 처리하여야 한다.</p>

                                <p class="notice">제 5 장 서비스의 이용</p>

                                <h2>제 14 조 [서비스 제공 및 제한]</h2>
                                <p>① 회사는 이용자에게 아래와 같은 서비스를 제공한다.</p>
                                <ul>
                                    <li>사용자가 전송하고자 하는 내용〇정보 등을 문자메시지(SMS), 멀티미디어메시지(MMS) 및 기타 음성, 팩스, 알림톡 등의 다양한 형태로 가공
                                        변환시켜 제3자가 콜백 메시지의 형태로 수신할 수 있도록 하는 웹(web)과 어플리케이션(Application) 그리고 이와 관련한 제반 시스템
                                    </li>
                                    <li>기타 회사가 추가 개발하거나 다른 회사와의 제휴계약 등을 통해 이용자에게 제공하는 일체의 서비스</li>
                                </ul>

                                <p>② 회사는 서비스를 일정범위로 분할하여 각 범위별로 이용가능한 시간을 별도로 지정할 수 있다. 다만 이러한 경우에는 그 내용을 사전에 공지하여야 한다.
                                </p>

                                <p>③ 서비스는 연중무휴, 1일 24시간 제공함을 원칙으로 한다. 단, 회사는 서비스의 정기적인 또는 긴급한 사안으로 운영상의 이유가 있는 경우 서비스의
                                    제공을 일시적으로 중단 후 매월 8시간 이내에서 점검을 실시할 수 있다. 이 경우 회사는 제8조[이용자에 대한 통지]에 정한 방법으로 이용자에게
                                    통지하여야 한다. 다만, 회사가 긴급한 사안으로 사전에 통지할 수 없는 부득이한 사유가 있는 경우 사후에 통지할 수 있다.</p>

                                <p>④ 제①항의 각 서비스 및 제휴 서비스의 내용은 변경될 수 있으며, 이 경우 회사는 서비스 홈페이지에 공지하거나, 이용자의
                                    어플리케이션(Application) 또는 기타 문자 등을 통하여 통지할 수 있다.</p>

                                <p>⑤ 회사는 국가비상사태, 서비스 시스템의 장애 또는 서비스 이용의 폭주 등으로 서비스 이용에 지장이 있을 때에는 서비스의 전부 또는 일부를 제한하거나
                                    정지할 수 있다.</p>

                                <p>⑥ 회사는 이용자가 다음 각 호에 해당할 경우 1개월 동안의 기간을 정하여 이용자의 서비스 이용을 정지할 수 있다. 다만, 제4호의 사유가 발생하는 경우
                                    서비스 이용 정지기간은 정지사유가 해소되는 기간까지로 한다.</p>
                                <ul>
                                    <li>이용자가 서비스를 불법스팸 전송에 이용하는 경우.</li>
                                    <li>이용자가 전송하는 광고로 인하여 회사의 서비스에 장애를 야기하거나 야기할 우려가 있을 경우.</li>
                                    <li>이용자가 전송하는 광고의 수신자가 불법스팸으로 신고한 경우.</li>
                                    <li>이용자가 서비스 이용요금을 연체하는 경우.</li>
                                    <li>이용자가 불법스팸을 전송하는 경우.</li>
                                    <li>이용자가 전송한 메시지가 불법스팸으로 신고되는 경우, 회사의 수신거부요청 처리에 불성실하여 수신거부 요청 건수가 감소되지 않거나 발송금지를
                                        요청한 메시지 내용이 중복적으로 발송될 경우.</li>
                                    <li>방송통신위원회, 한국인터넷진흥원 등 관련 기관이 불법스팸의 전송 및 발신번호 변작 등의 사실을 확인하여 이용정지를 요청하는 경우.</li>
                                </ul>

                                <p>⑦ 회사는 이용자의 서비스 이용 내용이 다음 각 호에 해당할 경우 그 이용을 임의로 제한할 수 있다.</p>
                                <ul>
                                    <li>이용자가 본 약관상의 의무를 위반하는 경우.</li>
                                    <li>이용자가 회사의 서비스 운영을 고의 또는 과실로 방해하는 경우.</li>
                                    <li>서비스의 안정적 운영을 방해할 목적으로 다량의 정보를 전송하거나 정보통신설비의 오동작 또는 시스템 보유 정보 파괴를 유발시키는 컴퓨터 바이러스
                                        프로그램 등을 유포하는 경우.</li>
                                    <li>타인의 지적재산권을 침해하는 경우.</li>
                                    <li>방송통신심의위원회의 시정요구가 있거나, 불법선거운동과 관련하여 선거관리위원회의 유권 해석을 받은 경우.</li>
                                    <li>타인의 서비스 사용 권한을 부정하게 이용하는 경우.</li>
                                    <li>서비스 정보를 이용하여 얻은 회사의 시스템 관련 정보를 회사의 사전 승낙 없이 복제 또는 유통시키거나 상업적으로 이용하는 경우.</li>
                                    <li>타인의 명예를 손상시키거나 불이익을 주는 경우.</li>
                                    <li>서비스에 위해를 가하거나 서비스의 건전한 이용을 저해하는 경우.</li>
                                    <li>회사의 채권보전조치를 위해 제출을 요구한 증빙서류를 허위로 작성한 경우.</li>
                                    <li>국익 또는 사회적 이익에 반하는 목적으로 이용하는 경우.</li>
                                </ul>

                                <p>⑧ 회사는 서비스 이용자에게 도움과 혜택이 될 수 있다고 판단이 되면, 일시적 또는 주기적으로 홍보, 공지사항, 이벤트 등 광고성 메시지를 발송할 수
                                    있다. 단, 이용자가 메시지 수신에 대한 거부의사를 게시판 또는 고객센터 등을 통하여 밝힐 경우에는 그러하지 아니한다.</p>

                                <h2>제 15 조 [서비스의 변경]</h2>
                                <p>① 회사는 상당한 이유가 있는 경우에 운영상, 기술상의 필요에 따라 제공하고 있는 전부 또는 일부 서비스를 변경할 수 있다.</p>

                                <p>② 회사는 서비스의 내용, 이용방법, 이용시간에 대하여 변경이 있는 경우에는 변경사유, 변경될 서비스의 내용 및 제공일자 등을 변경 전에 해당 서비스
                                    홈페이지 또는 공지사항 등에 게시한다.</p>

                                <p>③ 회사는 무료로 제공되는 서비스에 대하여 일부 또는 전부를 회사의 정책 및 운영상의 필요로 수정·중단·변경할 수 있으며, 이에 대하여 관련법령 등에
                                    특별한 규정이 없는 한 이용자에게 별도의 보상을 하지 않는다.</p>

                                <h2>제 16 조 [서비스 이용의 제한 및 정지]</h2>
                                <p>① 회사는 이용자가 본 약관의 의무를 위반하거나 서비스의 정상적인 운영을 방해한 경우 서비스 이용을 제한하거나 정지할 수 있다.</p>

                                <p>② 회사는 불법스팸 발송을 방지하기 위하여 해당 ID 또는 전화번호가 6개월간 서비스 이용 내역이 없는 경우 해당 ID 또는 전화번호의 서비스 이용을
                                    정지할 수 있다. 단, 서비스 이용 정지기간은 이용자가 해당 ID 또는 전화번호의 이용정지 해제를 요청하는 기간까지로 한다.</p>

                                <p>③ 회사는 이용자의 개인정보 보호를 위하여 12개월 이상 계속해서 서비스 접속을 포함한 서비스 이용이 없는 이용자는 휴면이용자로 분류하여 서비스 이용을
                                    정지하고 별도 관리한다. 이 경우 이용자는 휴면기간 내 휴면 해제 또는 재접속을 통해 휴면계정 상태를 해제할 수 있다.</p>

                                <p>④ 회사의 귀책사유가 없는 서비스 이용 제한 및 중지 그리고 장애로 인하여 서비스 이용에 따른 손해가 발생한 경우 회사는 이에 대해 책임을 지지
                                    않습니다.</p>

                                <p>⑤ 회사는 주민등록법을 위반한 명의도용 및 결제도용, 저작권법을 위반한 불법프로그램의 제공 및 운영방해, “정보통신망법”을 위반한 스팸메시지 및
                                    불법통신, 해킹, 악성프로그램의 배포, 접속권한 초과행위 등과 같이 관련법을 위반한 경우에는 즉시 그 이용을 영구적으로 정지할 수 있다. 본 항에 따라
                                    서비스 이용정지 시 서비스 내의 금액, 포인트, 혜택 및 권리 등이 모두 소멸되며 회사는 이에 대해 별도로 보상하지 않는다.</p>

                                <p>⑥ 회사는 스팸메시지·문자피싱메시지 전송을 방지하기 위하여 상황에 따라서 일일 발송 건수를 제한할 수 있으며, 자체 모니터링을 하고 이를 강화할 수
                                    있다.</p>

                                <p>⑦ 사업종목의 전환, 사업의 포기, 업체 간의 통합 등의 이유로 서비스를 제공할 수 없게 되는 경우에는 회사는 서비스 홈페이지에 공지하거나 이용자가
                                    이용신청 시 등록한 전화번호 또는 이메일 등을 통하여 이용자에게 그 내용을 통지한 후 서비스를 종료할 수 있다.</p>

                                <h2>제 17 조 [서비스 이용의 연장 및 변경]</h2>
                                <p>① 이용자는 초기 서비스에 가입을 완료하고 정기 월단위 또는 회사가 정한 기간에 대하여 서비스 이용료를 결재하면 서비스를 이용할 수 있는 권리를
                                    획득한다.</p>

                                <p>② 이용자는 서비스 이용에 있어 정기 월단위 또는 회사가 정한 기간 만료 후 1일 이내에 추가 결재를 할 경우에는 서비스 이용 권리가 자동으로 연장된다.
                                </p>

                                <p>③ 이용자는 서비스의 명의 이전, 양도, 상속 그리고 이용자의 명의변경에 따라 서비스 이용에 대한 변경사항이 발생할 경우 해당 변경 등의 내용에 대하여
                                    회사에 통지하고, 회사로부터 별도의 동의를 얻어야 한다.</p>

                                <p>④ 이용자가 변경사항에 대하여 회사에 신고하지 아니함으로 인하여 이용자에게 발생하는 불이익은 모두 이용자가 부담한다.</p>

                                <p>⑤ 정기이용시 만료일로부터 5일 전까지 새로운 이용계약의 체결 또는 이용계약의 해지에 대하여 이용자와 회사의 특별한 의사표시가 없을 경우 이전의
                                    이용계약과 동일한 조건으로 자동 결재가 이루어지고 서비스는 자동 연장된다.</p>

                                <h2>제 18 조 [서비스 이용의 탈퇴 해지 및 환급]</h2>
                                <p>① 이용자는 서비스 이용을 해지하고자 할 때 본인이 직접 서비스를 통하여 신청할 수 있으며, 요금의 미납 및 연체 시 회사는 정해진 관련 절차에 따라
                                    서비스 탈퇴 및 해지를 처리할 수 있다.</p>

                                <p>② 이용자는 다음 각호의 사유가 있는 경우에는 서비스 탈퇴 및 해제 또는 해지할 수 있으며, 이용자가 회사에 의사를 표시한 때에 그 효력이 발생한다.
                                </p>
                                <ul>
                                    <li>이용자가 서비스 이용을 원하지 않는 경우</li>
                                    <li>서비스의 하자를 회사가 보완, 수정할 수 없는 경우</li>
                                    <li>이용자가 약관의 변경에 동의하지 않는 경우</li>
                                </ul>

                                <p>③ 회사는 이용자가 다음 각 호에 해당할 경우에는 이용자의 동의 없이 이용계약을 해지할 수 있으며, 그 사실을 이용자에게 지체 없이 통지하여야 한다.
                                    다만 회사가 긴급하게 해지할 필요가 있다고 인정하는 경우 또는 이용자의 귀책사유로 인하여 통지할 수 없는 경우에는 그러하지 아니한다.</p>
                                <ul>
                                    <li>이용자가 이 약관을 위반하고 일정 기간 이내에 위반 내용을 해소하지 않는 경우.</li>
                                    <li>회사의 서비스 제공목적 외의 용도로 서비스를 이용하거나 제3자에게 임의로 서비스를 재 임대한 경우.</li>
                                    <li>방송통신위원회·한국인터넷진흥원 등 관계기관이 스팸메시지·문자피싱 메시지 등 불법행위의 전송사실을 확인하여 계약해지를 요청하는 경우.</li>
                                    <li>기타 저작권 위반 등 관계 법령의 위반으로 제재를 받은 경우.</li>
                                </ul>

                                <p class="notice">제 6 장 이용 요금</p>

                                <h2>제 19 조 [요금의 청구 및 납입 결제]</h2>
                                <p>① 요금 산정을 위한 과금 주기는 월 단위 정산을 기준으로 하며, 서비스 이용 요금은 회사가 제공하는 결제 시스템을 이용하여 선결제를 원칙으로 한다.
                                    단, 사안에 따라 회사와 협의하여 계좌이체, 무통장 입금 등 기타 방법으로 선결제 또는 후결제를 할 수 있다.</p>

                                <p>② 월 단위 이외에 필요에 따라 6개월/1년 단위의 결제는 가능하며, 이 경우 서비스 이용 회선의 수량, 단위, 기간 등에 따라 회사가 정한 별도의
                                    할인율 및 결제 방식에 따라 결제 후 서비스를 이용할 수 있다.</p>

                                <h2>제 20 조 [요금의 계산 및 청구 납입]</h2>
                                <p>① 서비스별 요금의 기준 등은 "별첨 1"에 별도로 정한 바에 따른다.</p>

                                <p>② 본 서비스는 해당 서비스 이용 계약 체결과 동시에 이루어지는 선결제를 통해 해당 서비스 이용이 개시되며, 고객의 해지 등의 의사를 밝히지 않을 경우
                                    자동으로 청구·납입되며, 이용자와 회사 간의 서비스 이용 계약이 연장되고 서비스의 이용 권한 또한 자동 연장된다.</p>

                                <p>③ 회사는 서비스 이용이 정지된 기간 중에는 월정액의 요금을 청구하지 않으며, 서비스 이용에 관하여 본 약관에서 정한 바에 따라 이용자의 책임 없는
                                    사유로 해당 서비스를 이용할 수 없었던 기간 등 이용 제한 기간 동안의 요금은 청구하지 아니한다.</p>

                                <p>④ 회사는 기존 개별 단위로 분산되어 청구되는 납입 청구 방식을 이용자와 협의하여 하나의 통합된 방식으로 청구할 수 있다. 단, 협의한 내용에 따라 통합
                                    청구한 경우라 하더라도 요금 등의 이의 제기로 이용자가 신청이 있는 경우 회사는 해당 서비스 요금을 분리하여 재청구할 수 있다.</p>

                                <h2>제 21 조 [요금에 대한 이의신청 및 요금 청구권 소멸시효]</h2>
                                <p>① 이용자는 청구 납입된 요금에 이의가 있는 경우 납입일로부터 3개월 이내에 이의 신청을 할 수 있다. 다만, 회사의 귀책사유로 과·오납이 발생한
                                    경우에는 본문과 상관없이 이용자 등은 과·오납된 요금에 대하여 언제든지 이의를 신청할 수 있다.</p>

                                <p>② 회사는 전항의 이의 신청을 받은 날로부터 14일 이내에 그 이의 신청에 대한 처리 결과를 통지하여야 하며, 다만, 부득이한 사유로 14일 이내에
                                    통지할 수 없는 경우에는 그 기간의 만료일 다음 날부터 10일의 범위에서 한 차례 연장할 수 있으며, 그 연장 사유를 이용자에게 통지하여야 한다.
                                </p>

                                <p>③ 이용자가 서비스 이용 중 회사의 귀책사유로 인하여 서비스가 중단되었을 경우 회사는 장애 시간이 3시간 이상일 경우 장애가 발생된 1일 평균 이용
                                    요금의 2배에 해당하는 금액만큼 보상한다. 단, 보상 금액은 장애가 발생된 서비스 별 최고 1개월(30일) 이용 요금을 초과할 수 없으며, 이 보상
                                    금액은 당사자 협의 하에 서비스 기간 연장으로 갈음할 수 있다.</p>

                                <p>④ 회사의 이용자에 대한 서비스 이용 요금 청구권에 관한 소멸시효는 "민법 또는 상법"에 따른다.</p>

                                <h2>제 22 조 [사용료의 지불방법 및 납부자]</h2>
                                <p>① 이용자는 계약에 따라 본 약관에서 정한 사용료에 대하여 회사가 제공하는 결제 시스템을 이용하여 사용료를 지급하여야 하며, 특별한 사정이 없는 한
                                    회사가 지정한 수납 기관에 납기일까지 납부하여야 한다.</p>

                                <p>② 해당 서비스의 사용료는 회사와 해당 서비스를 이용하기로 계약을 체결한 계약자(또는 이용자)가 그 명의로 납부하는 것을 원칙으로 하며, 계약자와
                                    이용자가 서로 다를 경우에는 이 두 당사자는 이러한 사실을 회사에 즉시 통보하여 해당 서비스 이용에 문제가 없도록 회사의 동의를 얻어야 한다. 만약 이
                                    경우 납부 명의자로부터 요금 결제에 대한 이의 제기가 있을 경우 회사는 이에 대하여 책임을 지지 아니하고, 위 이용자 등이 이를 입증 및 해결하여야
                                    한다.</p>

                                <p>③ 서비스 이용자가 계약자의 동의 없이 그의 명의를 도용하여 해당 서비스 이용 계약을 체결한 사실이 밝혀진 경우에는 피명의 도용자(계약자)는 그 해당
                                    서비스 이용 요금에 대한 납입 책임이 면제되며, 이 경우 서비스를 실제 이용한 이용자가 해당 서비스 이용 요금 지불 및 이에 대한 부수적인 모든 법적
                                    책임을 부담한다.</p>

                                <h2>제 23 조 [요금의 납기일 및 미납 요금의 징수]</h2>
                                <p>① 요금 납기일은 이용자가 서비스 이용 시작일을 기준으로 정한다. (서비스 이용 신청 및 납입 완료 기준)</p>

                                <p>② 회사는 이용자가 요금을 1개월 이상 미납할 경우 최종 납부(독촉) 기한 및 이 기한 내 미납할 경우 서비스 이용이 중지된다는 내용을 통지하여야 한다.
                                    만약 이 통지에서 요구한 기한이 경과 후 14일 이내에도 요금을 납부하지 아니할 경우 회사는 별도의 통지 없이 해당 서비스 계약을 해지하고, 미납한
                                    요금에 대하여는 민사상의 채권 추심 절차를 진행한다. 다만, 그 미납 사유가 천재지변 등 불가항력에 의한 경우에는 그러하지 아니한다.</p>

                                <p>③ 회사는 이용자가 요금을 미납한 경우 최초 납기월의 말일 다음날을 기준으로 하여 요금 미납액의 100분의 2에 상당하는 금액을 미납된 요금에 가산하여
                                    징수한다.</p>

                                <p>④ 회사는 이용자가 요금을 과·오납한 경우 그 요금을 반환하거나 차기 요금에서 정산할 수 있다.</p>

                                <p class="notice">제 7 장 문자 발송량 및 면책 등 기타 사항</p>

                                <h2>제 24 조 [문자 발송량 제한]</h2>
                                <p>회사는 서비스 이용에 있어 별도의 발송량 제한 정책이 없으므로, 이용자는 가입한 이동통신사와의 약정에 의하여 정해진 이동통신사별 메시지 발송 조건 및
                                    발송량 제한 정책에 따른다.</p>

                                <h2>제 25 조 [각종 자료 등의 저장기간]</h2>
                                <p>회사는 서비스 이용자가 필요에 의해 저장하고 있는 자료 등에 대하여 일정한 저장기간을 정할 수 있으며, 필요에 따라 그 기간을 변경할 수도 있다. 단,
                                    개인정보 보호법 등 관련 법령에 따라 저장 및 관리 기간이 달라질 수 있으며, 법정 보존 기간을 초과하여 데이터를 보관하지 않는다.</p>

                                <h2>제 26 조 [게시물 등의 저작권]</h2>
                                <p>① 이용자가 본 서비스 내에서 게시하거나 등록한 자료 및 콘텐츠 등에 대하여는 전적으로 이용자의 책임하에 있으며, 위 게시하거나 등록한 자료 및 콘텐츠와
                                    관련한 법적 권리(저작권 등) 및 책임 또한 이용자에게 있다. 따라서, 회사는 이용자가 게시하거나 등록한 자료 및 콘텐츠에 대해서 어떠한 법적 책임도
                                    부담하지 아니한다. 다만, 회사는 본 서비스 운영 권한자로서 이용자가 게시 및 등록한 자료와 콘텐츠 등을 이용자의 동의 및 책임하에 그 편집 등을 위해
                                    활용할 수 있다.</p>

                                <p>② 본 서비스에서 회사가 제공하는 콘텐츠 등에 대한 저작권 등 지적 재산권은 회사가 가지며, 이에 대한 책임도 회사가 부담한다.</p>

                                <p>③ 이용자는 제공받은 일정 부분의 서비스 및 플랫폼에 대하여 사용권을 부여받으며, 이를 무단으로 양도, 증여, 질권의 목적으로 사용할 수 없다.</p>

                                <p>④ 회사 또는 이용자가 각각 그 상대방의 지적 재산권 등을 침해하여 손해가 발생한 경우 그 손해를 배상하여야 한다.</p>

                                <p>⑤ 이용자는 서비스를 이용하여 얻은 정보를 가공, 판매하는 행위 등 게재된 자료를 상업적으로 이용할 수 없으며, 이를 위반하여 발생하는 제반 문제에 대한
                                    책임은 이용자에게 있다.</p>

                                <h2>제 27 조 [손해 배상 및 면책]</h2>
                                <p>① 회사와 이용자는 고의 또는 과실로 상대방에게 손해를 발생시킨 경우에는 귀책사유가 있는 당사자가 일체의 손해를 배상하여야 한다. 단, 천재지변, 정부
                                    및 법령의 규제 등 불가항력적 사유로 인한 경우에는 그러하지 아니한다.</p>

                                <p>② 이용자가 관련 법령, 본 계약, 회사의 운영 정책에서 정한 의무를 이행하지 못하여 제3자로부터 소송, 청구, 이의 제기 등을 당한 경우에는 이용자는
                                    이로부터 회사를 방어하고, 관련하여 발생한 총 비용(손해배상 포함)을 부담한다.</p>

                                <p>③ 이용자가 본 계약 및 관련 법령상 이용자의 의무를 위반 또는 이행하지 않아 서비스의 품질 하락 또는 신뢰도 저하 등을 초래한 경우, 이용자는 위 품질
                                    하락 또는 신뢰도 저하 등으로 회사가 입게 되는 손해를 배상하여야 한다. 특히 제10조[이용자의 의무] 제5항에 의하여 메시지 발송의 주체인 서비스
                                    이용자는 그 수신인의 동의 없이 발송한 광고성 메시지 또는 스팸 메시지·문자 피싱 메시지 전송 등으로 그 수신인에게 손해를 발생하게 한 경우에는
                                    이용자는 이 손해로부터 회사를 면책시키고, 일체의 손해를 배상하여야 한다.</p>

                                <p>④ 회사의 귀책사유로 인한 이용자의 손해배상의 청구는 회사에 청구 사유, 청구 금액 및 산출 근거를 기재하여 서면으로 하여야 하며, 그 손해배상의 금액은
                                    1개월(30일) 이용 요금을 초과할 수 없다.</p>

                                <p>⑤ 회사는 다음 각 호의 어느 하나로 서비스를 제공할 수 없는 경우 서비스 제공에 대한 책임이 면제된다.</p>
                                <ul>
                                    <li>천재지변 또는 이에 준하는 불가항력의 상태가 있는 경우.</li>
                                    <li>서비스의 효율적인 제공을 위한 시스템 개선, 장비 증설 등 계획된 서비스 중지 일정을 사전에 공지한 경우.</li>
                                    <li>이용자의 전산망 장애로 발생하는 서비스의 지연 및 불능의 경우.</li>
                                    <li>회사의 서비스 시스템의 정기 점검 또는 업데이트로 인한 서비스의 제한.</li>
                                    <li>회사에게 회선, 통신망, 전용선망을 제공하고 있는 이동통신사 또는 부가통신사업자 측의 장애·귀책사유.</li>
                                    <li>서비스 제공을 위하여 회사와 서비스 제휴 계약을 체결한 제3자의 고의적인 서비스 방해가 있는 경우.</li>
                                    <li>이용자의 귀책사유로 서비스 이용에 장애가 있는 경우.</li>
                                    <li>회사의 고의·과실이 없는 사유로 인한 경우.</li>
                                    <li>문자 미발송으로 인한 피해.</li>
                                </ul>

                                <p>⑥ 회사는 이용자가 서비스를 통해 얻은 정보 또는 자료 등으로 인하여 발생한 손해와 서비스를 이용하거나 이용할 것으로부터 기대되는 손익(기대손익) 등에
                                    대하여는 책임지지 않는다.</p>

                                <p>⑦ 회사는 이용자가 게시 또는 전송한 자료의 출처 및 내용에 대한 적법성뿐만 아니라 이용자가 서비스를 통하여 전송한 메시지의 내용에 대해서는 그 정확성,
                                    신뢰성, 시기 적절성 등을 보장하지 않으며, 해당 내용이 관련 법령을 위반하거나 제3자의 권리를 침해하는 경우 이에 대한 책임을 지지 아니한다.</p>

                                <p>⑧ 회사는 “이용자 상호간” 또는 “이용자와 제3자 상호간”에 본 서비스를 매개로 발생한 분쟁에 대해서는 개입할 의무가 없으며, 이로 인한 손해를 배상할
                                    책임도 없다.</p>

                                <p>⑨ 회사는 “무료 제공 서비스”에 대하여 회사의 귀책사유로 이용자에게 서비스를 제공하지 못하는 경우에는 이로 인한 손해를 배상할 책임이 없다.</p>

                                <p>⑩ 회사는 부득이한 사유로 서비스 이용상의 일시적인 장애로 인하여 발생한 이용자 및 그 수신자의 부가적, 영업적인 간접손해 등에 대한 손해를 배상할
                                    책임이 없다.</p>

                                <p>⑪ 회사는 회사의 고의 또는 과실이 없는 한 서비스를 활용하여 발송된 메시지 관련 발신자와 수신자 간 분쟁에 대하여 개입할 의무가 없으며 이로 인한
                                    손해를 배상할 책임이 없다.</p>

                                <h2>제 28 조 [분쟁 조정 및 관례]</h2>
                                <p>① 본 약관은 대한민국 법령에 의하여 규정되고 이행된다. 또한 본 약관에서 정하지 않은 사항과 본 약관의 해석에 관하여는 정부가 제정한 “전기통신
                                    기본법”, “전기통신사업법”, “정보통신망법”, “전자상거래 등에서의 소비자보호에 관한 법률”, “약관의 규제에 관한 법률”, “전자거래기본법”,
                                    “전자서명법”, “방문판매 등에 관한 법률”, “소비자기본법” 및 기타 관계 법령 또는 상관례에 따른다.</p>

                                <p>② 서비스 이용과 관련하여 회사와 이용자 간에 발생한 분쟁에 대해서는 민사소송법상의 회사의 주소지를 관할하는 법원을 합의 관할로 한다.</p>






                                <div class="footer">
                                    <p>공고일자: 2025년 01월 01일<br>
                                        시행일자: 2025년 01월 20일</p>
                                    <p>© 제이예스 주식회사. All rights reserved.</p>
                                </div>
                            </main>
                        </div>
                    </body>

                    </html>