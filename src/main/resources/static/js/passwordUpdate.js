// 회원 비밀번호 수정
function passwordUpdate(userId, event) {

    event.preventDefault();

    let data = $("#passwordUpdate").serialize();

    $.ajax({
        type:"put",
        url:`/api/user/${userId}/password`,
        data:data,
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json"
    }).done(res=>{
        console.log("비밀번호 변경 성공", res);
        location.href=`/user/${userId}`;
    }).fail(error=>{
        if(error.data == null) {
            alert(error.responseJSON.message);
        }else {
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}