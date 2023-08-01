<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필 섹션-->
<section class="profile">
	<!--유저정보 컨테이너-->
	<div class="profileContainer">

		<!--유저이미지-->
		<div class="profile-left">
			<div class="profile-img-wrap story-border"
				onclick="popup('.modal-image')">
				<form id="userProfileImageForm">
					<input type="file" name="profileImageFile" style="display: none;"
						id="userProfileImageInput"/>
				</form>

				<img class="profile-image" src="/upload/${dto.user.profileImageUrl}"
					onerror="this.src='/images/person.jpg'" id="userProfileImage" />
			</div>
		</div>
		<!--유저이미지end-->

		<!--유저정보 및 사진등록 팔로우하기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${dto.user.name}</h2>

				<c:choose>
					<c:when test="${dto.pageOwnerState}">
						<button class="cta" onclick="location.href='/image/upload'">사진등록</button>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${dto.followState}">
								<button class="cta" onclick="toggleFollow(${dto.user.id}, this)">팔로우 취소</button>
							</c:when>
							<c:otherwise>
								<button class="cta blue" onclick="toggleFollow(${dto.user.id}, this)">팔로우</button>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

				<button class="modi" onclick="popup('.modal-info')">
					<i class="fas fa-cog"></i>
				</button>
			</div>

			<div class="follow">
				<ul>
					<li><a>게시물<span>${dto.imageCount}</span>
					</a></li>
					<li><a href="javascript:followerInfoModalOpen(${dto.user.id});">팔로워<span id="follower-count">${dto.followerCount}</span>
					</a></li>
					<li><a href="javascript:followingInfoModalOpen(${dto.user.id});">팔로잉<span>${dto.followingCount}</span>
					</a></li>
				</ul>
			</div>
			<div class="state">
				<h4>${dto.user.bio}</h4>
				<h4>${dto.user.website}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 팔로우하기-->

	</div>
</section>

<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner">

				<!--아이템들-->
				<!--JSTL 문법-->
				<c:forEach var="image" items="${dto.user.images}"> <!--EL표현식에서 변수명을 적으면 get함수가 자동 호출됨-->
					<div class="img-box">
						<a href=""> <img src="/upload/${image.postImageUrl}"/>
						</a>
						<div class="comment">
							<!--따라서 ImageService와 마찬가지로 UserService에서 likeCount에 값을 집어 넣은 후에 사용-->
							<a href="#" class=""> <i class="fas fa-heart"></i><span>${image.likeCount}</span>
							</a>
						</div>
					</div>
				</c:forEach>
				<!--아이템들end-->
			</div>
		</div>
	</div>
</section>

<!--로그아웃, 회원정보, 비밀번호 변경 모달-->
<div class="modal-info" onclick="modalInfo()">
	<div class="modal">

		<c:choose>
			<c:when test="${dto.userState}">
				<button onclick="location.href='/user/${dto.user.id}/update'">회원정보 변경</button>
				<button onclick="location.href='/user/${dto.user.id}/passwordUpdate'">비밀번호 변경</button>
				<button onclick="location.href='/logout'">로그아웃</button>
				<button onclick="closePopup('.modal-info')">취소</button>
			</c:when>
			<c:otherwise>
				<button onclick="location.href='/user/${dto.user.id}/update'">회원정보 변경</button>
				<button onclick="location.href='/logout'">로그아웃</button>
				<button onclick="closePopup('.modal-info')">취소</button>
			</c:otherwise>
		</c:choose>

	</div>
</div>
<!--로그아웃, 회원정보, 비밀번호 변경 모달 end-->

<!--프로필사진 바꾸기 모달-->
<div class="modal-image" onclick="modalImage()">
	<div class="modal">
		<!--페이지의 주인이 아닐 땐 프로필변경 화면 대신 프로필 사진 나오게 설정해보기-->
		<c:choose>
			<c:when test="${dto.user.id == principal.user.id}">
				<p>프로필 사진 바꾸기</p>
				<button onclick="profileImageUpload(${principal.user.id})">사진 업로드</button>
				<button onclick="closePopup('.modal-image')">취소</button>
			</c:when>
			<c:otherwise>
				<img class="profile-image" src="/upload/${dto.user.profileImageUrl}"
						   onerror="this.src='/images/person.jpg'" id="userProfileImage2" />
			</c:otherwise>
		</c:choose>

	</div>
</div>
<!--프로필사진 바꾸기 모달 end-->

<!-- 팔로워, 팔로잉 버튼 모달-->
<div class="modal-follow">
	<div class="follow">
		<div class="follow-header">
			<span>리스트</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="follow-list" id="followModalList">

		</div>
	</div>
</div>
<!-- 팔로워, 팔로잉 버튼 모달 end-->


<script src="/js/profile.js"></script>

<%@ include file="../layout/footer.jsp"%>