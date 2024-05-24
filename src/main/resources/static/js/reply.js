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
      result = "";
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
                                  <p>`;
        if (`${reply.email}` == user) {
          result += '<button class="btn btn-outline-danger btn-sm">삭제</button>';
          result += '<button class="btn btn-outline-success btn-sm">수정</button>';
        }
        result += `</p></div>`;
        result += `</li>`;

        console.log("댓글");
        console.log(result);

        // reReList(replyNo);
        if (reply.comments.length > 0) {
          reply.comments.forEach((comment, idx) => {
            console.log(comment);

            result += `<ul class="children">
            <li class="replyComment" data-commentNo="${comment.commentNo}">
            <input type="hidden" class="comment" value="${replyNo}" />
            <div class="vcard bio">
            <img src="images/person_1.jpg" alt="Image placeholder"/>
            </div>
            <div class="comment-body">
            <h3>${comment.nickname}</h3>
            <div class="meta">${formatDate(comment.lastModifiedDate)}</div>
            <p>${comment.text}
            </p>
            <p>`;
            if (`${comment.email}` == user) {
              result += '<button class="btn btn-danger btn-sm">삭제</button>';
              result += '<a class="reply btn btn-success btn-sm">수정</a>';
            }
            result += `</p></li>`;
            if (idx == reply.comments.length - 1 && user != "anonymousUser") {
              result += `<form action="/comment/add" class="comment-form" method="post">
              
              <input type="hidden" name="commentNo"/>
              <input type="hidden" name="replyNo" value="${replyNo}"/>
              <input type="hidden" name="mid" value="${mid}"/>
              <input type="hidden" class="form-control" name="nickname" value="${nickname}"/>
              <input type="hidden" class="form-control" name="email" value="${user}"/>
              
              <div class="form-group">
                <input name="text" id="commentText" class="form-control"></input>
              </div>
              <div class="form-group">
                <button type="submit" class="btn py-3 px-4 btn-primary" id="commentBtn">댓글 등록</button>
              </div>
            </form>`;
            }
            result += `</ul>`;
          });
        } else if (reply.comments.length == 0 && user != "anonymousUser") {
          result += `<form action="/comment/add" class="comment-form" method="post" >
            
            <input type="hidden" name="commentNo"/>
            <input type="hidden" name="replyNo" value="${replyNo}"/>
            <input type="hidden" name="mid" value="${mid}"/>
            <input type="hidden" class="form-control" name="nickname" value="${nickname}"/>
            <input type="hidden" class="form-control" name="email" value="${user}"/>
           
            <div class="form-group">
              <input name="text" id="commentText" class="form-control"></input>
            </div>
            <div class="form-group">
              <button type="submit" class="btn py-3 px-4 btn-primary" id="commentBtn">댓글 등록</button>
            </div>
          </form>`;
        }
      });
      console.log("댓글");
      console.log(result);
      document.querySelector("#replyList").innerHTML = result;
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
  const commentNo = reviewForm.querySelector("#replyComment");
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

// 리뷰
// 클릭 시 replyNo 가져오기
replyList.addEventListener("click", (e) => {
  // 부모 요소가 이벤트를 감지하는 형태로 작성 => 실제 이벤트 대상 요소가 무엇인지 확인필요
  console.log("이벤트 대상", e.target);
  console.log(e.target.closest(".comment"));
  // 리뷰 댓글 번호 가져오기

  // 컨트롤러에서 작성자와 로그인 유저가 같은지 다시 한번 비교하기 위함
  const email = reviewForm.querySelector("#email");
  // 대댓글 번호
  // const commentNo = e.target.closest(".replyComment").dataset.commentno;

  if (e.target.classList.contains("btn-outline-danger")) {
    const replyNo = e.target.closest(".comment").dataset.replyno;
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
    const replyNo = e.target.closest(".comment").dataset.replyno;
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
  if (e.target.classList.contains("reply")) {
    const commentNo = e.target.closest(".replyComment").dataset.commentno;
    const replyNo = e.target.closest(".replyComment").querySelector(".comment").value;

    console.log("reply 버튼 클릭 {}", replyNo);

    fetch(`/comment/${replyNo}/${commentNo}`, {
      method: "get",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        // reply 버튼 클릭
        const commentBody = e.target.closest(".comment-body");
        console.log(commentBody);
        const replyFormHTML = `
          <form action="/comment/modify" class="comment-form" method="post">
            <input type="hidden" name="commentNo" value="${data.commentNo}"/>
            <input type="hidden" name="replyNo" value="${data.replyNo}"/>
            <input type="hidden" name="mid" value="${mid}"/>
            <input type="hidden" class="form-control" name="nickname" value="${nickname}"/>
            <input type="hidden" class="form-control" name="email" value="${user}"/>
            <div class="form-group">
              <input name="text" id="commentText" class="form-control" value="${data.text}"></input>
            </div>
            <div class="form-group">
              <button type="submit" class="btn py-3 px-4 btn-primary" id="commentBtn">댓글 수정</button>
            </div>
          </form>
        `;
        commentBody.innerHTML = replyFormHTML;
      });
  } else if (e.target.classList.contains("btn-danger")) {
    const commentNo = e.target.closest(".replyComment").dataset.commentno;
    const replyNo = e.target.closest(".replyComment").querySelector(".comment").value;
    console.log(commentNo);
    if (!confirm("리뷰를 삭제하시겠습니까?")) return;

    const form = new FormData();
    form.append("email", email.value);

    fetch(`/comment/${replyNo}/${commentNo}`, {
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
  }
});

// 대댓글 추가 form
replyList.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log("대댓글 submit 일시중지");
  const form = e.target.closest("form");

  const commentNo = form.querySelector('input[name="commentNo"]');
  const text = form.querySelector('input[name="text"]');
  const mid = form.querySelector('input[name="mid"]');
  const nickname = form.querySelector('input[name="nickname"]');
  const email = form.querySelector('input[name="email"]');
  const replyNo = form.querySelector('input[name="replyNo"]');

  if (text == "") {
    alert("내용 확인");
    return;
  }
  console.log(mid);
  console.log(nickname);
  console.log(email);
  console.log(replyNo);
  // 값 존재 하는지 없는지

  const body = {
    text: text.value,
    email: email.value,
    mid: mid.value,
    nickname: nickname.value,
    replyNo: replyNo.value,
    commentNo: commentNo.value,
  };
  if (!commentNo.value) {
    fetch(`/comment/add`, {
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
    fetch(`/comment/${replyNo.value}/${commentNo.value}`, {
      method: "put",
      headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          text.value = "";

          alert(data + "번 리뷰 수정 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  }
  // e.target.submit();
});

const actionForm = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  e.preventDefault();
  if (!confirm("삭제하시겠습니까?")) {
    return;
  }
  actionForm.submit();
});
