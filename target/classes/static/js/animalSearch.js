document.querySelector("#searchForm").addEventListener("submit", (e) => {
  e.preventDefault();

  const type = document.querySelector("#type");
  const keyword = document.querySelector("#keyword");

  if (type.value == "") {
    alert("검색 타입을 확인해 주세요");
    type.focus();
    return;
  } else if (keyword.value == "") {
    alert("검색어를 확인해 주세요");
    keyword.focus();
    return;
  }

  e.target.submit();
});

// 통계 모달창 띄우기
document.addEventListener("DOMContentLoaded", function () {
  const modal = document.querySelector(".modal");
  const modalOpenButtons = document.querySelectorAll(".modal_btn");
  const modalClose = document.querySelector(".close_btn");

  modalOpenButtons.forEach((button) => {
    button.addEventListener("click", function () {
      modal.style.display = "block";
    });
  });

  modalClose.addEventListener("click", function () {
    modal.style.display = "none";
  });

  // 모달 외부 클릭 시 닫기
  window.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.style.display = "none";
    }
  });
});
