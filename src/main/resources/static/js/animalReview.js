// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// /reviews/mno/all 요청 처리
const reviewsLoaded = () => {
  fetch(`/animalReviews/${sId}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 리뷰 총 개수 변경
      document.querySelector(".review-cnt").innerHTML = `${data.length}개의 댓글`;
      if (data.length > 0) reviewList.classList.remove("hidden");

      let result = "";
      data.forEach((review) => {
        result += `<ul class="comment-list"> <li class="comment" data-rno="${review.rno}"> <div class="vcard bio">`;
        result += `<img src="/assets/images/replyperson_1.jpg" alt="Image placeholder" /></div>`;
        result += `<div class="comment-body" ><h3>${review.nickname}</h3>`;
        result += `<div class="meta">${formatDate(review.createdDate)}</div>`;
        result += `<p>${review.text}</p>`;
        if (`${review.email}` == user) {
          result += `<div class ="delMod"><div class="mb-2 delete"><button class="btn btn-outline-danger btn-sm animalReviewdel">삭제</button></div>`;
          result += `<div><button class="btn btn-outline-success btn-sm animalReviewMod">수정</button></div>`;
        }
        result += `</div></div></li></ul>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded();

// 새 댓글 등록
// 새 댓글 등록 폼 submit 시
// submit 기능 중지 / 작성자 / 댓글 가져오기 → 스크립트 객체로 변경

const replyForm = document.querySelector("#replyForm");

replyForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const sid = replyForm.querySelector("#sId");
  const text = replyForm.querySelector("#text");
  const email = replyForm.querySelector("#email");
  const mid = replyForm.querySelector("#mid");
  // 수정인 경우에 값이 들어옴 (등록일 땐 x - 구분을 위해 rno 받기)
  const rno = replyForm.querySelector("#rno");

  const reply = {
    sid: sid.value,
    text: text.value,
    mid: mid.value,
    email: email.value,
    rno: rno.value,
  };

  console.log("reply", JSON.stringify(reply));

  if (!rno.value) {
    // 새 댓글 등록
    fetch(`/animalReviews/new`, {
      method: "post",
      headers: {
        "content-type": "application/json",
        // csrf 값을 담아주기
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(reply),
    })
      .then((response) => response.text())
      .then((data) => {
        if (data) {
          // replyForm 내용 제거
          text.value = "";

          reviewsLoaded();
        }
      });
  } else {
    // 댓글 수정
    fetch(`/animalReviews/${rno.value}`, {
      method: "put",
      headers: {
        "content-type": " application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(reply),
    })
      .then((response) => response.text())
      .then((data) => {
        if (data) {
          // replyForm 내용 제거
          text.value = "";
          rno.value = "";

          reviewsLoaded();
        }
      });
  }
});

// 이벤트 전파 : 자식 요소에 일어난 이벤트는 상위 요소로 전달 됨
// 댓글 삭제/수정 버튼 클릭 시 이벤트 전파로 찾아오기
// rno 가져오기
reviewList.addEventListener("click", (e) => {
  // 실제 이벤트가 일어난 대상은 누구인가? e.target
  const btn = e.target;

  // closest("요소") : 제일 가까운 상위요소 찾기
  // rno 클래스명 - data- 로 들어있는 건 dataset 으로 가져옴
  const rno = btn.closest(".comment").dataset.rno;
  console.log("rno", rno);

  const email = replyForm.querySelector("#email");

  const form = new FormData();
  form.append("email", email.value);

  // 삭제 or 수정 버튼이 눌러졌을 때만 동작 (댓글 전체 선택해도 동작 - 제한 필요)
  // 삭제 or 수정 버튼이 클릭이 되었는지 구분하기
  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;
    fetch(`/animalReviews/${rno}`, {
      method: "delete",
      headers: {
        "X-CSRF-TOKEN": csrfValue,
      },
      body: form,
    })
      .then((response) => response.text())
      .then((data) => {
        if (data == "success") {
          // 새로고침
          reviewsLoaded();
        }
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    // rno에 해당하는 댓글 가져온 후 댓글 등록 폼에 가져온 내용 보여주기
    fetch(`/animalReviews/${rno}`, {
      method: "get", // get 은 생략 가능
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("데이터 가져오기");
        console.log(data);

        replyForm.querySelector("#rno").value = data.rno;
        replyForm.querySelector("#email").value = data.email;
        replyForm.querySelector("#text").value = data.text;
      });
  }
});
