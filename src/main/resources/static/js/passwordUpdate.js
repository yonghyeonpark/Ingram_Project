// (1) 회원정보 수정
function passwordUpdate(userId, event) {

    // 폼태그 액션을 멈춤
    event.preventDefault();

    // header에 제이쿼리 설정이 돼있음
    let data = $("#passwordUpdate").serialize(); // key = value 형태

    //ajax로 요청하면 응답을 페이지나 파일이 아닌 데이터로 응답 => ApiController
    $.ajax({
        type:"put",
        url:`/api/user/${userId}/password`,
        data:data,
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json" // json으로 응답을 받음
    }).done(res=>{ // res에 json data를 javascript로 파싱해서 응답을 받음 => res는 javascript object가 됨 / Http상태코드 200번대
        console.log("비밀번호 변경 성공", res);
        location.href=`/user/${userId}`;
    }).fail(error=>{ // Http상태코드 200번대x
        if(error.data == null) {
            alert(error.responseJSON.message);
        }else {
            alert(JSON.stringify(error.responseJSON.data));
        }
        //console.log("update 실패", error);
    });
}