/**
  1. 유저 프로필 페이지
  (1) 유저 프로필 페이지 팔로우하기, 팔로우취소
  (2) 팔로우자 정보 모달 보기
  (3) 팔로우자 정보 모달에서 팔로우하기, 팔로우취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로필 이미지 메뉴(사진업로드, 취소) 모달
  (8) 팔로우자 정보 모달 닫기
 */

// (1) 유저 프로필 페이지 팔로우하기, 팔로우취소
function toggleFollow(toUserId, obj) {
	if ($(obj).text() === "팔로우 취소") {

		$.ajax({
			type:"delete",
			url:"/api/follow/"+toUserId,
			dataType:"json"
		}).done(res=> {

			let followerCountStr = $(`#follower-count`).text();
			let followerCount = Number(followerCountStr) - 1;
			$(`#follower-count`).text(followerCount);

			$(obj).text("팔로우");
			$(obj).toggleClass("blue");
		}).fail(error=> {
			console.log("팔로우 취소 실패", error);
		});

	}else {

		$.ajax({
			type:"post",
			url:"/api/follow/"+toUserId,
			dataType:"json"
		}).done(res=> {

			let followerCountStr = $(`#follower-count`).text();
			let followerCount = Number(followerCountStr) + 1;
			$(`#follower-count`).text(followerCount);

			$(obj).text("팔로우 취소");
			$(obj).toggleClass("blue");
		}).fail(error=> {
			console.log("팔로우 실패", error);
		});

	}
}

// (2) 팔로잉 정보 모달 보기
function followingInfoModalOpen(pageUserId) {
	$(".modal-follow").css("display", "flex"); // jsp의 클래스

	$.ajax({
		url:`/api/user/${pageUserId}/following`,
		dataType:"json"
	}).done(res=> {
		console.log(res.data);

		res.data.forEach((u)=>{
			let item = getFollowingModalItem(u);
			$("#followModalList").append(item);
		});

	}).fail(error=> {
		console.log("팔로잉 리스트 불러오기 실패", error);
	});
}

// (3) 팔로잉 정보 모달에서 팔로우하기, 팔로우취소
function getFollowingModalItem(u) {

	let item=`<div class="follow__item" id="followModalItem-${u.userId}">
	<div class="follow__img">
			<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpg'"/>
		</div>
		<div class="follow__text">
			<h2>${u.username}</h2>
		</div>
		<div class="follow__btn">`;

	if(u.mirrorState == 0) {
		if(u.followState == 1) {
			item+=`<button class="cta" onClick="toggleFollow(${u.userId}, this)">팔로우 취소</button>`;
		}else {
			item+=`<button class="cta blue" onClick="toggleFollow(${u.userId}, this)">팔로우</button>`;
		}
	}

	item+=` 
		</div>
	</div>`;

	return item;
}

// * 팔로워 정보 모달 보기
function followerInfoModalOpen(pageUserId) {
	$(".modal-follow").css("display", "flex");

	$.ajax({
		url:`/api/user/${pageUserId}/follower`,
		dataType:"json"
	}).done(res=> {
		console.log(res.data)

		res.data.forEach((u)=>{
			let item = getFollowerModalItem(u);
			$("#followModalList").append(item);
		});

	}).fail(error=> {
		console.log("팔로워 리스트 불러오기 실패", error);
	});

}

function getFollowerModalItem(u) {

	let item=`<div class="follow__item" id="followModalItem-${u.userId}">
	<div class="follow__img">
			<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpg'"/>
		</div>
		<div class="follow__text">
			<h2>${u.username}</h2>
		</div>
		<div class="follow__btn">`;

	item+=` 
		</div>
	</div>`;

	return item;
}



// (4) 유저 프로필 사진 변경
function profileImageUpload(principalId) {

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버에 이미지를 전송
		let userProfileImageForm = $("#userProfileImageForm")[0];

		// formData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있음
		let formData = new FormData(userProfileImageForm);

		// form 태그에 있는 사진Data를 던질 때
		$.ajax({
			type:"put",
			url:`/api/user/${principalId}/profileImageUrl`,
			data:formData,
			contentType:false, // 필수 : x-www-form-urlencoded로 파싱되는 것을 방지
			processData:false, // 필수 : contentType을 false로 줬을 때 QueryString으로 자동 설정되는데, 이를 해제하기 위함
			enctype:"multipart/form-data",
			dataType:"json"
		}).done(res=>{

			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.

		}).error(error=>{
			console.log("실패", error);
		});



	});
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (7) 사용자 프로필 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (8) 팔로잉 정보 모달 닫기
function modalClose() {
	$(".modal-follow").css("display", "none");
	location.reload();
}






