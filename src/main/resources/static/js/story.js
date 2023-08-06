/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// 현재 로그인한 사용자의 id
let principalId = $("#principalId").val();
console.log("현재 로그인 유저 :", principalId);

// (1) 스토리 로드하기

let page = 1;

function storyLoad() {

	$.ajax({
		url:`/api/image?page=${page}&size=2`,
		dataType:"json"
	}).done(res=>{
		console.log(res);
		res.data.forEach((image)=>{
			let storyItem= getStoryItem(image);
			$("#storyList").append(storyItem);
		})
	}).fail(error=>{
		console.log("에러",error);
	});
}

storyLoad();

function getStoryItem(image) {

	let item=`<div class="story-list__item">
	<div class="sl__item__header">
		<div><a href="/user/${image.user.id}">
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpg'" /> <!--사진이 없으면 설정한 사진을 뿌림--></a>
		</div>
		<div><a href="/user/${image.user.id}">${image.user.username}</a>
		</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">
			<button>`;

			if(image.likeState) {
				item +=`<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i> <!--id값을 다르게 해줘야 함-->`;
			}else {
				item +=`<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
			}

		item +=`
			</button>
		</div>
		
		<div class="sl__item__contents__icon2">
			<button>`;

			if(image.bookmarkState) {
				item +=`<i class="fas fa-bookmark active" id="storyBookmarkIcon-${image.id}" onclick="toggleBookmark(${image.id})"></i>`;
			}else {
				item +=`<i class="far fa-bookmark" id="storyBookmarkIcon-${image.id}" onclick="toggleBookmark(${image.id})"></i>`;
			}

		item +=`
			</button>
		</div>
		
		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>

		<div id="storyCommentList-${image.id}">`;

			image.comments.forEach((comment)=>{
				item +=`
					<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
						<p> 
							<b>${comment.user.username} :</b> ${comment.content}
						</p>`;

						if(principalId == comment.user.id) {
							item +=`
								<button onclick="deleteComment(${comment.id})">
									<i class="fas fa-times"></i>
								</button>`;
						}
				item +=`
					</div>`;
			});

		item +=`
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 작성..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`;
	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {

	let check = $(window).scrollTop() - ($(document).height() - $(window).height());

	if(check < 1 && check >-1) {
		page++;
		storyLoad();
	}

});


// (3-1) 좋아요, 좋아요 취소
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`); // #을 사용할 땐 쌍따옴표(")가 아닌 백틱(`)으로 처리
	if (likeIcon.hasClass("far")) { // 좋아요 누르기 전

		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{

			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); // 해당 id에 접근하여 내부의 text를 가져옴
			let likeCount = Number(likeCountStr) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("에러", error);
		});

	}else {

		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{

			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); // 해당 id에 접근하여 내부의 text를 가져옴
			let likeCount = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("에러", error);
		});

	}
}

// (3-2) 북마크, 북마크 취소
function toggleBookmark(imageId) {
	let bookmarkIcon = $(`#storyBookmarkIcon-${imageId}`); // #을 사용할 땐 쌍따옴표(")가 아닌 백틱(`)으로 처리
	if (bookmarkIcon.hasClass("far")) { // 좋아요 누르기 전

		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/bookmark`,
			dataType:"json"
		}).done(res=>{

			bookmarkIcon.addClass("fas");
			bookmarkIcon.addClass("active");
			bookmarkIcon.removeClass("far");
		}).fail(error=>{
			console.log("에러", error);
		});

	}else {

		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/bookmark`,
			dataType:"json"
		}).done(res=>{

			bookmarkIcon.removeClass("fas");
			bookmarkIcon.removeClass("active");
			bookmarkIcon.addClass("far");
		}).fail(error=>{
			console.log("에러", error);
		});

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type:"post",
		url:"/api/comment",
		data:JSON.stringify(data),
		contentType:"application/json; charset=utf-8",
		dataType:"json"
	}).done(res=>{
		console.log("성공", res);

		let comment = res.data;

		let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			<p>
			  <b>${comment.user.username} :</b>
			   ${comment.content}
			</p>

			<button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>

		  </div>
		`;
		commentList.prepend(content); // append는 뒤에 추가

	}).fail(error=>{
		console.log("에러", error.responseJSON.data.content);
		alert(error.responseJSON.data.content);
	});

	commentInput.val(""); // input 필드를 비워줌(에러가 나도 비울거기 때문에 따로 빼놓음)
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res=>{
		console.log("댓글 삭제 성공", res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("댓글 삭제 실패", error);
	})

}







