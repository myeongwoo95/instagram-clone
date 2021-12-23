/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 유저 프로필 사진 변경
  (4) 사용자 정보 메뉴 열기 닫기
  (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (7) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {
		
		$.ajax({
			type: "delete",
			url: "/api/subscribe/"+toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독성공실패", error);
		});
		
	} else {
		
		$.ajax({
			type: "post",
			url: "/api/subscribe/"+toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독취소실패", error);
		});

	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	
	$.ajax({
		type: "get",
		url: `/api/user/${pageUserId}/subscribe`,
		dataType: "json"
	}).done(res => {
		console.log(res.data);
		
		res.data.forEach(user =>{
			let item = getSubscribeModalItem(user);
			$("#subscribeModalList").append(item);
		});
		
	}).fail(error => {
		console.log("구독정보 불러오기 실패", error);
	});
	
	$(".modal-subscribe").css("display", "flex");
}

function subscribeInfoModalOpenFollower(pageUserId) {
	
	$.ajax({
		type: "get",
		url: `/api/user/${pageUserId}/followers`,
		dataType: "json"
	}).done(res => {
		console.log(res.data);
		
		res.data.forEach(user =>{
			let item = getSubscribeModalItem(user);
			$("#subscribeModalList").append(item);
		});
		
	}).fail(error => {
		console.log("구독정보 불러오기 실패", error);
	});
	
	$(".modal-subscribe").css("display", "flex");
}

function getSubscribeModalItem(user) {
	let item = `<div class="subscribe__item" id="subscribeModalItem-${user.id}">
						<div class="subscribe__img">
							<img src="/upload/${user.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
						</div>
						<div class="subscribe__text">
							<h2>${user.username}</h2>
						</div>
						<div class="subscribe__btn">`;
						
	if(!user.equalUserState){ // 동일 유저가 아닐 때 버튼이 만들어져야함
		if(user.subscribeState){ // 구독한 상태
			item += `<button class="cta blue" onclick="toggleSubscribe(${user.id},this)">구독취소</button>`;
		} else{ // 구독안한 상태
			item += `<button class="cta" onclick="toggleSubscribe(${user.id},this)">구독하기</button>`;
		}
	}		
						
	item +=`</div>
			</div>`;
			
	return item;
}


// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {
	
	// 페이지 유저와 로그인 유저가 다를 경우
	if(pageUserId != principalId){
		alert("프로필 사진을 수정할 수 없는 유저입니다.");
		return;
	}
	
	// input type="file" 클릭 이벤트
	$("#userProfileImageInput").click();
	
	// 유저가 프로필 이미지를 변경했을때 감지해서 동작
	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];
		
		// 이미지 파일이 아닐 경우
		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		
		//서버에 이미지를 전송
		let profileImageForm = $("#userProfileImageForm")[0]; // foam 태그의 id인데 배열로 받아와야 한다.
		
		// formData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
		let formData = new FormData(profileImageForm); // form 태그 안의 데이터가 담긴다. 여기선 이미지가 담김
		
		$.ajax({
			
			type: "put",
			url: `/api/user/${principalId}/profileImageUrl`,
			data: formData,
			contentType: false,  // ajax로 사진 전송시 필수, x-www-form-urlencoded로 파싱되는 것을 방지
			processData: false, // ajax로 사진 전송시 필수, contentType을 false로 줬을 때 QueryString 자동 설정됨, 그것을 해제
			enctype: "multipart-form-data", // form 태그에 entype 타입 기입시 안적어줘도 되는데 가독성 때문에 둘다 해주는게 좋을듯
			dataType: "json"
			
		}).done(res => {
			
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
			
		}).fail(error => {
			console.log("사진 변경 오류", error);
			alert("사진 변경이 실패되었습니다");
		});
	
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






