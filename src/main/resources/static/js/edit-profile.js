const changeForm = document.querySelector("#changeForm");
changeForm.addEventListener("submit", (e) => {
  e.preventDefault();

  Swal.fire({
    text: "비밀번호를 변경 하시겠습니까?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "변경",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      e.target.submit();
    } else {
      return;
    }
  });
});
