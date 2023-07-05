// (1) 회원정보 수정
function update(userId) {

    // header에 제이쿼리 설정이 돼있음
    let data = $("#profileUpdate").serialize();

    //ajax로 요청하면 응답을 페이지나 파일이 아닌 데이터로 응답 => ApiController
    $.ajax({
        type:"put",
        url:`/api/user/${userId}`,
        data:data,
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json" // json으로 응답을 받음
    }).done(res=>{ // res에 json data를 javascript로 파싱해서 응답을 받음 => res는 javascript object가 됨
        console.log("update 성공");
        location.href=`/user/${userId}`;
    }).fail(error=>{
        console.log("update 실패");
    });
}