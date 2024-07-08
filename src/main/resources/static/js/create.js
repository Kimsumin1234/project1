// x 를 누르면 파일 삭제 요청
// a 태그 기능 중지 => href 값 가져와서 화면 출력
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();

  console.log("x 클릭" + e.target); // <i class="fa-solid fa-xmark"></i>
  console.log(e.currentTarget); // output에 있는 애들 전부를 다 가져옴
  const currentLi = e.target.closest("li");
  // data-file 에 있는 값 가져오기
  const filePath = e.target.closest("a").dataset.file; // data안에 들어있는거 가져올때 무조건 set
  console.log("filePath", filePath);

  const formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/imageupload/remove", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        // 해당 div 제거
        currentLi.remove();
      }
    });
});
