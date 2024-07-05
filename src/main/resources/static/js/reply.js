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
        result += `<li class="comment p-3 mb-0" data-replyNo="${reply.replyNo}">
                              <div class="vcard bio">
                                  <img src="data:image/jpeg;base64,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" alt="Image placeholder" />
                              </div>
                              <div class="comment-body">
                                  <h3>${reply.nickname}</h3>
                                  <div class="meta">${formatDate(reply.lastModifiedDate)}</div>
                                  <p class="text-break">${reply.text}
                                  </p>
                                  <p>
                                    <div class="bottom">
                                      <a href="/member/login" class="btn btn-secondary mr-2 addcomment">답글</a>`;
        if (`${reply.email}` == user) {
          result += '<button class="btn btn-outline-danger btn-sm">삭제</button>';
          result += '<button class="btn btn-outline-success btn-sm">수정</button>';
        }
        result += `</div></p>`;

        // console.log("댓글");
        // console.log(result);

        result += `<form action="/comment/add" class="comment-form hidden" method="post">

            <input type="hidden" name="commentNo"/>
            <input type="hidden" name="replyNo" value="${replyNo}"/>
            <input type="hidden" name="mid" value="${mid}"/>
            <input type="hidden" class="form-control" name="nickname" value="${nickname}"/>
            <input type="hidden" class="form-control" name="email" value="${user}"/>
           
            <div class="form-group">
              <div class="input-group mb-3">
              <span class="user-tag">@${reply.nickname} </span>
              <input name="text" id="commentText" class="form-control"></input>
                <button type="submit" class="btn  btn-primary" >답글 등록</button>
              </div>
            </div>
          </form>`;
        result += `</div></li>`;

        // reReList(replyNo);
        if (reply.comments.length > 0) {
          reply.comments.forEach((comment, idx) => {
            console.log(comment);

            result += `
            <li class="replyComment p-3 pl-5 mb-0" data-commentNo="${comment.commentNo}">
            <i class="fa fa-arrow-right"></i>
            <input type="hidden" class="comment" value="${replyNo}" />
            <div class="vcard bio">
            <img src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkLDRYPDQwMDRsUFRAWIB0iIiAdHx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys/RD84QzQ5OjcBCgoKDQwNGg8PGjclHyU3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3N//AABEIAMAAzAMBIgACEQEDEQH/xAAbAAEAAQUBAAAAAAAAAAAAAAAABgECAwQFB//EADsQAAIBAwEGAggEAwkBAAAAAAABAgMEEQUGEiExQVETYSIjMlJxgaGxFEKRwRUlcyQzQ1NicoLR4Rb/xAAWAQEBAQAAAAAAAAAAAAAAAAAAAQL/xAAXEQEBAQEAAAAAAAAAAAAAAAAAAREh/9oADAMBAAIRAxEAPwD04AGmwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABQAyurLVVpt4U457ZAuASzyAAAZWcAAAAAAUAKSlGPtSS+LCKgtVSEvZnF/BlwUAAQABAAAAAAAAADWQOvnyAgeualWu7upDfkqUJOMIp8Dl/mznj3yZruO7dVo9qkl9WYTStije3Nu80a9SL8pHVs9qLujhXEI149c8GcIBU3t9pbCpSbqOdJpey1k1K21lFNqjbzl2cngieAExIv/rbjj/ZaXl6TL6W1tXe9bax3f8ATIjQBibW201hUWarnSfZxz9jUvNqoRyrOhvPpKb/AGIp8VkIDo3Wt6hc53rhwT6Q4GjKpUn7c5P4tssAVfTqTpyThNxa5NMm+zeoVL6wfi8alJ7rl3RBSW7Fwxa3M+9RL6BEjABEAAQAAAAAAAAB1yAB59rtLwtWuY9HNtGgdza+luarvpcJx+xwzTQAAAAAAAAAAAAAE32Sp7mlKXvybIQeh6LS8HS7eD57uWErdABEAAQAAAAAAAAAAUR3bKhvUKNx7st1/MiJ6HrNt+K02vTXPdyvkeeFaAAAAAAAAAAAAAGa0pOvc0qS5zmkekwioQjBcorBCtk7bxtT8R8qUc/9E2IlAAEAAQAAAAAAAAAAAxnPmsMgGvWbs9Sqx4bk/ThjomT/ACQ7bFfzGk1/llixwAAVQAAAAAAAAdGB3AnGy9l+F05VJcZ1vSb8uh2DV0l/yu2/pR+xtEZAAQAAAAAAAAAAAAAFCK7aU/X2tTHDdaZKzj7UWruNNcor06T3l8CxUIZQAoAAKAAAAAAXGSj3YN/RLV3epUqe7mMfSYE9toeHb0oe7BL6GQBEZAAQAAAAAAAAAAAAAApOKnBwksxksMqCiB65pU9PuW0m6MuMJLp5HLfU9Mr0KVxRlSrQU4S5pkbv9lW256fUWH/hVOnzCouDer6Vf2/97a1Eu6WV9DVlSqReHTmv+LKrGC9wl1i18im5P3JfoBaDPTtLiphQoVJN8kos6Frs5qFZrfhGjHq6j/YDkxjKclGCzKXBLz7E32e0r+H0HOqvX1FxfZdjJpWh2unvxONWr78v2XQ6iIgAAgACAAAAAAAAAAAAAAAAAAPjwArl9foWuMW8uKfxRUx1K9Gn7danH/dJIoudKk+dOP6Dw4e5H5RRrfxOwzj8bb5/qIvhf2c3iF1Rk+yqIL1nSwsIqE4y9lp/BjOQgCpQgAAAAAAAAAAAAAAAAAAAOmWPNtJIjms7RxouVGwalPk6j5L4Fiu3eXtvZQcrmrGHDgupHr7aptuNlRWOk59fkR2vWqV579abnJ9WzEUbtzqd7dOXi3E8P8sXhfQ036XGXF+ZQBVWUwuqAAyUq1Sk06VScGvdk0dS22kv6GFUqKtHP5+f6nHAE4sNo7S6e7V9TU7SfD9TrpqSUovK7rkzzA6el6zdWElFSdSl1gyYmJ6DV07UKGoUlUoSTl+aDfFM2gAAIgAAAAAAAAAABSTUYuTeEuLyVfIi+1GrvfdnQl09ZJfYqsG0GuSrzna2c3GjynJc5f8AhHioZVUAAAAAAAAAAAAAZrS5q2deNahPcn38vMnWkapS1KhvLhVj7cM8jz8z2N1UsrmNag8NdO/kEekg1tPvKV7bQrU/zc12ZsmQABUAAQAAAADaSy3jzA52u6hHT7JyTXiz9GCf3+RApzlUqSnNtyk8tvqzoa9qH8Qv5Si34UPRgvLuc0qgAKoAAAAAAAAAAAAAAADsbNak7K8VKrJeBV4Nt+y+j/YnC4Lm8M8v5E82cvvxunx3n6yn6Ml9gjqAAiAAIP/Z" alt="Image placeholder"/>
            </div>
            <div class="comment-body">
            <h3>${comment.nickname}</h3>
            <div class="meta">${formatDate(comment.lastModifiedDate)}</div>
            <div class="commentinline">
            <span class="user-tag">@${reply.nickname}</span><p class="text-break ml-2 comment-text-inline">${comment.text}</p>
            </div>
            <p>
              <div class="bottom">
                <a href="/member/login" class="btn btn-secondary mr-2 addcomment">답글</a>
              `;
            if (`${comment.email}` == user) {
              result += '<button class="btn btn-danger btn-sm">삭제</button>';
              result += '<button class="btn btn-success btn-sm reply2">수정</button>';
            }
            result += `</div></p>`;

            result += `<form action="/comment/add" class="comment-form hidden" method="post">
              <input type="hidden" name="commentNo"/>
              <input type="hidden" name="replyNo" value="${replyNo}"/>
              <input type="hidden" name="mid" value="${mid}"/>
              <input type="hidden" class="form-control" name="nickname" value="${nickname} "/>
              <input type="hidden" class="form-control" name="email" value="${user}"/>

              <div class="form-group">
              <span class="user-tag">@${reply.nickname}</span>
                <div class="input-group mb-3">
                  <input name="text" id="commentText" class="form-control"></input>
                  <button type="submit" class="btn btn-primary">답글 등록</button>
                </div>
              </div>
            </form>`;
            result += `</div></li>`;
          });
          // } else if (reply.comments.length == 0 && user != "anonymousUser") {
        }
      });
      console.log("댓글");
      // console.log(result);
      document.querySelector("#replyList").innerHTML = result;

      document.querySelectorAll(".btn-outline-success").forEach((button) => {
        button.addEventListener("click", (event) => {
          event.preventDefault();
          document.querySelector(".review-form").scrollIntoView({ behavior: "smooth" });
        });
      });
    });
};

reviewsLoaded(); // 나중에 댓글을 수정했을때 바로바로 업데이트해주기위해 함수로 만들어둠

// 리뷰 등록 or 수정
const reviewForm = document.querySelector(".review-form");
// if 로그인 했을때
reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();
  // text, grade, mid, mno
  const text = reviewForm.querySelector("#text");
  const mid = reviewForm.querySelector("#mid");
  const nickname = reviewForm.querySelector("#nickname");
  const email = reviewForm.querySelector("#email");
  const replyNo = reviewForm.querySelector("#replyNo");
  const commentNo = reviewForm.querySelector("#replyComment");
  console.log(text.value.length);
  if (text.value.length == 0) {
    alert("내용 확인");
    return;
  }
  if (text.value.length > 501) {
    alert("500 자 이상 불가능");
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
      headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          text.value = "";

          // alert(data + "번 리뷰 작성 성공");

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
      headers: { "X-CSRF-TOKEN": csrfValue }, // json으로 보내던걸 다른걸로 보냄
      body: form,
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          // alert(data + "번 리뷰 삭제 성공");

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

        const commentBody = e.target.closest(".comment-body");
        console.log(commentBody);
        const textBreak = commentBody.querySelector(".text-break");
        const bottoms = commentBody.querySelector(".bottom");
        bottoms.innerHTML = ``;
        const replyFormHTML = `
          <input type="hidden" class="editreplyNo" name="replyNo" value="${data.replyNo}"/>
          <input type="hidden" class="editmid" name="mid" value="${data.mid}"/>
          <input type="hidden" class="editnickname" name="nickname" value="${data.nickname}"/>
          <input type="hidden" class="editemail" name="email" value="${data.email}"/>
          <div class="form-group">
            <input name="text" class="edittext" value="${data.text}"></input>
          </div>
          <div class="form-group">
            <button class="btn btn-primary edit">댓글 수정</button>
            <a type="buttom" class="btn btn-warning cancel" >수정 취소</a>
          </div>
        `;
        textBreak.innerHTML = replyFormHTML;
      });
  }
  if (e.target.classList.contains("edit")) {
    const commentBody = e.target.closest(".comment-body");
    const body = {
      text: commentBody.querySelector(".edittext").value,
      email: commentBody.querySelector(".editemail").value,
      mid: commentBody.querySelector(".editmid").value,
      nickname: commentBody.querySelector(".editnickname").value,
      rno: rno,
      replyNo: commentBody.querySelector(".editreplyNo").value,
    };
    if (body.text.trim().length == 0) {
      alert("내용 이 비어있습니다.");
      return;
    }
    if (body.text.trim().length > 500) {
      alert("500 자 이상 불가능");
      return;
    }
    console.log(body);

    fetch(`/reply/${rno}/${body.replyNo}`, {
      method: "put",
      headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          // alert(data + "번 리뷰 수정 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  }
  if (e.target.classList.contains("reply2")) {
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
        const textBreak = commentBody.querySelector(".text-break");
        const bottoms = commentBody.querySelector(".bottom");
        bottoms.innerHTML = ``;
        const replyFormHTML = `
          <form action="/comment/modify" class="comment-form modify-form" method="post">
            <input type="hidden" name="commentNo" value="${data.commentNo}"/>
            <input type="hidden" name="replyNo" value="${data.replyNo}"/>
            <input type="hidden" name="mid" value="${mid}"/>
            <input type="hidden" class="form-control" name="nickname" value="${nickname}"/>
            <input type="hidden" class="form-control" name="email" value="${user}"/>
            <div class="form-group">
              <input name="text" id="commentText" class="form-control" value="${data.text}"></input>
            </div>
            <div class="form-group">
              <button type="submit" class="btn btn-primary" >댓글 수정</button>
              <a type="buttom" class="btn btn-warning cancel" >수정 취소</a>
            </div>
          </form>
        `;
        textBreak.innerHTML = replyFormHTML;
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
      headers: { "X-CSRF-TOKEN": csrfValue }, // json으로 보내던걸 다른걸로 보냄
      body: form,
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          // alert(data + "번 리뷰 삭제 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  }
  if (e.target.classList.contains("cancel")) {
    reviewsLoaded();
  }
  if (e.target.classList.contains("addcomment")) {
    e.preventDefault();
    console.log(e.target);
    const commentBody = e.target.closest(".comment-body");
    console.log(commentBody);
    const commentform = commentBody.querySelector("form");
    console.log(commentform);
    if (commentform.classList.contains("hidden")) {
      commentform.classList.remove("hidden");
    } else {
      commentform.classList.add("hidden");
    }
  }
});

// 대댓글 추가 form
replyList.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log("대댓글 submit 일시중지");
  const form = e.target;
  console.log(e.target);

  const commentNo = form.querySelector('input[name="commentNo"]');
  const text = form.querySelector('input[name="text"]');
  const mid = form.querySelector('input[name="mid"]');
  const nickname = form.querySelector('input[name="nickname"]');
  const email = form.querySelector('input[name="email"]');
  const replyNo = form.querySelector('input[name="replyNo"]');

  if (text.value.trim().length == 0) {
    alert("내용 이 비어있습니다.");
    return;
  }
  if (text.value.trim().length > 500) {
    alert("500 자 이상 불가능");
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
      headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          text.value = "";

          // alert(data + "번 리뷰 작성 성공");

          reviewsLoaded(); // 리뷰 리스트 다시 가져오기
        }
      });
  } else if (commentNo.value) {
    fetch(`/comment/${replyNo.value}/${commentNo.value}`, {
      method: "put",
      headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        if (data) {
          text.value = "";

          // alert(data + "번 리뷰 수정 성공");

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
