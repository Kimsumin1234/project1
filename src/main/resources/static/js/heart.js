document.querySelector("#heart").addEventListener("click", (e) => {
  console.log(e.target);
  if (e.target.classList.contains("full")) {
    document.querySelector("#heart").className = "fa fa-heart-o";
    deleteHeart();
  } else {
    document.querySelector("#heart").className = "fa fa-heart full";
    addHeart();
  }
});
const body = {
  memberId: mid,
  reviewRno: rno,
};

const getHeart = () => {
  fetch(`/heart/${mid}/${rno}`, {
    method: "get",
    headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data == "") {
        document.querySelector("#heart").className = "fa fa-heart-o";
      } else {
        document.querySelector("#heart").className = "fa fa-heart full";
      }
    });
};

getHeart();

function addHeart() {
  fetch(`/heart/add`, {
    method: "post",
    headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
    body: JSON.stringify(body),
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        alert(data + "add 좋아요 성공");
        // 다시 가져오기
      }
      loadHeartCount();
    });
}

function deleteHeart() {
  fetch(`/heart/delete`, {
    method: "delete",
    headers: {
      "content-type": "application/json",
      "X-CSRF-TOKEN": csrfValue,
    },
    body: JSON.stringify(body),
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        alert(data + "delete 좋아요 성공");
        // 다시 가져오기
      }
      loadHeartCount();
    });
}
function loadHeartCount() {
  fetch(`/heart/${rno}`, {
    method: "get",
    headers: { "content-type": "application/json", "X-CSRF-TOKEN": csrfValue },
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        document.querySelector(".heartCount").textContent = data;
      }
    });
}
