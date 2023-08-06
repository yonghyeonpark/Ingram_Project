<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<!--북마크된 게시물-->
<main class="popular">
    <div class="exploreContainer">

        <!--북마크 갤러리-->
        <div class="popular-gallery">

            <c:forEach var="image" items="${images}">

                <div class="p-img-box">
                    <a href="/user/${image.user.id}"> <img src="/upload/${image.postImageUrl}"/>
                    </a>
                </div>

            </c:forEach>

        </div>
    </div>
</main>

<%@ include file="../layout/footer.jsp"%>