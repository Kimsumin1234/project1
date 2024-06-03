document.querySelector("#animalHeart").addEventListener("click", (e) => {
  console.log(e.target);
  if (e.target.classList.contains("full")) {
    document.querySelector("#animalHeart").className = "fa fa-heart-o";
    deleteHeart();
  } else {
    document.querySelector("#animalHeart").className = "fa fa-heart full";
    addHeart();
  }
});

console.log(mid);
console.log(sId);

const body = {
  mid: mid,
  sid: sId,
};

const getHeart = () => {
  fetch(`/animalHeart/${mid}/${sId}`, {
    method: "get",
    headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);

      loadHeartCount();

      if (data == "") {
        document.querySelector("#animalHeart").className = "fa fa-heart-o";
      } else {
        document.querySelector("#animalHeart").className = "fa fa-heart full";
      }
    });
};

getHeart();

function addHeart() {
  fetch(`/animalHeart/add`, {
    method: "post",
    headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
    body: JSON.stringify(body),
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);

      if (data) {
        alert(data + "add 좋아요 성공");
        // 리뷰 리스트 다시 가져오기
      }
      loadHeartCount();
    });
}

function deleteHeart() {
  fetch(`/animalHeart/delete`, {
    method: "delete",
    headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
    body: JSON.stringify(body),
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        alert(data + "delete 좋아요 성공");
      }
      loadHeartCount();
    });
}
function loadHeartCount() {
  fetch(`/animalHeart/${sId}`, {
    method: "get",
    headers: { "content-type": "application/json" }, // , "X-CSRF-TOKEN": csrfValue
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);

      document.querySelector(".animalHeartCount").innerHTML = `좋아요 개수 : ${data}`;

      // if (data) {
      //   document.querySelector(".aniamlHeartCount").textContent = data;
      // }
    });
}
