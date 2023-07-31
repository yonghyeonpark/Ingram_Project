<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필셋팅 메인-->
<main class="main">
    <!--프로필셋팅 섹션-->
    <section class="setting-container">
        <!--프로필셋팅 아티클-->
        <article class="setting__content">

            <!--프로필셋팅 아이디영역-->
            <div class="content-item__01">
                <div class="item__img">
                    <img src="#" onerror="this.src='/images/person.jpg'" />
                </div>
                <div class="item__username">
                    <h2>${principal.user.username}</h2>
                </div>
            </div>
            <!--프로필셋팅 아이디영역end-->

            <!--비밀번호 수정-->
            <form id="passwordUpdate" onsubmit="passwordUpdate(${principal.user.id}, event)">
                <div class="content-item__01">
                    <div class="item__title">현재 비밀번호 확인</div>
                    <div class="item__input">
                        <input type="password" name="nowPasswordCheck" placeholder="현재 비밀번호 확인"
                               required ="required" />
                    </div>
                </div>
                <div class="content-item__02">
                    <div class="item__title">새 비밀번호 입력</div>
                    <div class="item__input">
                        <input type="password" name="newPassword" placeholder="새 비밀번호"
                               required ="required" />
                    </div>
                </div>
                <div class="content-item__03">
                    <div class="item__title">새 비밀번호 확인</div>
                    <div class="item__input">
                        <input type="password" name="newPasswordCheck" placeholder="새 비밀번호 확인"
                               required = "required" />
                    </div>
                </div>

                <div class="content-item__04">
                    <div class="item__title"></div>
                    <div class="item__input">
                        <span>비즈니스나 반려동물 등에 사용된 계정인 경우에도 회원님의 개인 정보를 입력하세요. 공개 프로필에는 포함되지 않습니다.</span>
                    </div>
                </div>

                <!--제출버튼-->
                <div class="content-item__11">
                    <div class="item__title"></div>
                    <div class="item__input">
                        <button>제출</button>
                    </div>
                </div>
                <!--제출버튼end-->

            </form>
            <!--프로필수정 form end-->
        </article>
    </section>
</main>

<script src="/js/passwordUpdate.js"></script>

<%@ include file="../layout/footer.jsp"%>