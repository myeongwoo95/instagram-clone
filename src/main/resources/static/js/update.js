// (1) 회원정보 수정
function update(userId) {
	event.preventDefault();
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
	}).done(res =>{ // HttpStatus 200번대, res에는 json데이터를 js데이터로 파싱한 데이터가 받아진다.
			alert("회원정보가 성공적으로 이루어졌습니다.");
		location.href=`/user/${userId}/`;
	}).fail(error =>{ // HttpStatus 200번대가 아닐때
			console.log("update 실패", error);
			if(error.responseJSON.data == null){  // DB문제라면 HashMap type의 데이터(ErrorMessages)는 없어서 message만 보냇다. 그에 따라 분기를 나눠준다. 
				alert(error.responseJSON.message);
			}else{
				alert(JSON.stringify(error.responseJSON.data)); // error가 여러개 일 수도 있으니, 문자열에서 {}는 빼주면 좋겟네
			}
	})
	
}