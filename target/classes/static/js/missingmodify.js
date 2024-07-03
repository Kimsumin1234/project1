const actionForm = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  e.preventDefault();
  if (!confirm("삭제하시겠습니까?")) {
    return;
  }
  actionForm.submit();
});

// 이미지 삭제
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();
  // li 태그 가져오기
  const currentLi = e.target.closest("li");
  if (confirm("정말로 삭제하시겠습니까?")) {
    currentLi.remove();
  }
});
