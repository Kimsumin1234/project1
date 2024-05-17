const formatDate = (data) => {
  const date = new Date(data);
  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};
// 화면이 로드되면 바로 리뷰 보여주기
// /review/{}/all
const reviewsLoaded = () => {
  fetch(`/reply/${rno}/all`, { method: "get" })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      if (data.length > 0) {
        replyList.classList.remove("hidden");
      }
      // 리뷰 총 개수 변경
      document.querySelector(".review-cnt").innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        result += `<li class="comment">
                        <div class="vcard bio">
                            <img src="images/person_1.jpg" alt="Image placeholder" />
                        </div>
                        <div class="comment-body">
                            <h3>${reply.nickname}</h3>
                            <div class="meta">${formatDate(reply.lastModifiedDate)}</div>
                            <p>${reply.text}
                            </p>
                            <p><a href="#" class="reply">Reply</a></p>`;
        if (`${reply.email}` == user) {
          result += '<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>';
          result += '<div><button class="btn btn-outline-success btn-sm">수정</button></div>';
        }
        result += `</div>
                  </li>`;
      });
      replyList.innerHTML = result;
    });
};

reviewsLoaded(); // 나중에 댓글을 수정했을때 바로바로 업데이트해주기위해 함수로 만들어둠

// 리뷰 등록 or 수정
const reviewForm = document.querySelector(".review-form");
reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();
  // text, grade, mid, mno
  const text = reviewForm.querySelector("#text");
  const mid = reviewForm.querySelector("#mid");
  const nickname = reviewForm.querySelector("#nickname");
  const email = reviewForm.querySelector("#email");
  const replyNo = reviewForm.querySelector("#replyNo");
  if (text == "") {
    alert("내용 확인");
    return;
  }

  // replyNo 값 존재 하는지 없는지

  const body = {
    text: text.value,
    email: email.value,
    mid: mid.value,
    nickname: nickname.value,
    rno: rno,
    replyNo: replyNo.value,
  };
  if (!replyNo.value) {
    fetch(`/reply/${rno}`, {
      method: "post",
      headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          text.value = "";

          alert(data + "번 리뷰 작성 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  } else {
    fetch(`/reply/${rno}/${replyNo.value}`, {
      method: "put",
      headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          text.value = "";
          // nickname.value = "";
          replyNo.value = "";
          alert(data + "번 리뷰 수정 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  }
});

// 리뷰 삭제
// 삭제 클릭 시 replyNo 가져오기
replyList.addEventListener("click", (e) => {
  // 부모 요소가 이벤트를 감지하는 형태로 작성 => 실제 이벤트 대상 요소가 무엇인지 확인필요
  console.log("이벤트 대상", e.target);

  // 리뷰 댓글 번호 가져오기
  const replyNo = e.target.closest(".comment").dataset.replyNo;
  // 컨트롤러에서 작성자와 로그인 유저가 같은지 다시 한번 비교하기 위함
  const email = reviewForm.querySelector("#email");

  if (e.target.classList.contains("btn-outline-danger")) {
    if (!confirm("리뷰를 삭제하시겠습니까?")) return;

    const form = new FormData();
    form.append("email", email.value);

    fetch(`/reply/${mno}/${replyNo}`, {
      method: "delete",
      // headers: { "X-CSRF-TOKEN": csrfValue }, // json으로 보내던걸 다른걸로 보냄
      body: form,
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          alert(data + "번 리뷰 삭제 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  } else if (e.target.classList.contains("btn-outline-success")) {
    // 가져온 데이터 reviewForm에 보여주기
    fetch(`/reply/${rno}/${replyNo}`, {
      method: "get",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        reviewForm.querySelector("#replyNo").value = data.replyNo;
        reviewForm.querySelector("#nickname").value = data.nickname;
        reviewForm.querySelector("#text").value = data.text;
        reviewForm.querySelector("#mid").value = data.mid;
        reviewForm.querySelector("#email").value = data.email;
        reviewForm.querySelector("button").innerHTML = "리뷰 수정";
      });
  }
});
