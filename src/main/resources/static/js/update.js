// (1) 회원정보 수정
function update(userId) {
	//alert("test");
	//console.log(userId);
	//console.log(event);
	
	let data = $("#profileUpdate").serialize(); // form의 모든 input 값을 가져온다
	console.log(data);
	
	$.ajax({
		type: "put",
		url: `/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", //name=%EC%8C%80&username=ssar 이렇게 생겨먹은게 x-www-form 임
		dataType: "json"
	}).done(res =>{ // res에는 json데이터를 js데이터로 파싱한 데이터가 받아진다.
		console.log("update 성공");
		location.href=`/user/${userId}/`;
	}).fail(error =>{
		console.log("update 실패");
	})
	
}