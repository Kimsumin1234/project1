const formatDate = (data) => {
  const date = new Date(data);
  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};
// 화면이 로드되면 바로 리뷰 보여주기
// /review/{}/all
let result = "";
const reviewsLoaded = () => {
  fetch(`/reply/${rno}/all`, { method: "get" })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // if (data.length > 0) {
      //   replyList.classList.remove("hidden");
      // }

      // 리뷰 총 개수 변경
      document.querySelector(".review-cnt").innerHTML = data.length;

      data.forEach((reply) => {
        console.log(reply.replyNo);
        let replyNo = reply.replyNo;
        result += `<li class="comment" data-replyNo="${reply.replyNo}">
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
        result += `</div>`;
        // 여기에서 패치함수를 한번 더 받아서 대댓글을 받아와보자

        result += `</li>`;
        reReList(replyNo);
      });
    });
};

reviewsLoaded(); // 나중에 댓글을 수정했을때 바로바로 업데이트해주기위해 함수로 만들어둠

function reReList(replyNo) {
  fetch(`/comment/${replyNo}/all`, { method: "get" })
    .then((response) => response.json())
    .then((d) => {
      console.log(d);

      if (d.length > 0) {
        d.forEach((comment) => {
          console.log(comment);

          result += `<ul class="children">
          <li class="comment" data-commentNo="${comment.commentNo}">
          <div class="vcard bio">
          <img src="images/person_1.jpg" alt="Image placeholder"/>
          </div>
          <div class="comment-body">
          <h3>${comment.nickname}</h3>
          <div class="meta">${formatDate(comment.lastModifiedDate)}</div>
          <p>${comment.text}
          </p>
          <p><a href="#" class="reply">Reply</a></p>`;
          if (`${comment.email}` == user) {
            result += '<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>';
            result += '<div><button class="btn btn-outline-success btn-sm">수정</button></div>';
          }
          result += `</li>
          </ul>`;
        });
      }
      document.querySelector("#replyList").innerHTML = result;
    });
}

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
      headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
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
          reviewForm.querySelector("button").innerHTML = "댓글 등록";
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
  console.log(e.target.closest(".comment"));
  // 리뷰 댓글 번호 가져오기
  const replyNo = e.target.closest(".comment").dataset.replyno;
  // 컨트롤러에서 작성자와 로그인 유저가 같은지 다시 한번 비교하기 위함
  const email = reviewForm.querySelector("#email");

  if (e.target.classList.contains("btn-outline-danger")) {
    if (!confirm("리뷰를 삭제하시겠습니까?")) return;

    const form = new FormData();
    form.append("email", email.value);

    fetch(`/reply/${rno}/${replyNo}`, {
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

const actionForm = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  e.preventDefault();
  if (!confirm("삭제하시겠습니까?")) {
    return;
  }
  actionForm.submit();
});
