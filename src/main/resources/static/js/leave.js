const leaveForm = document.querySelector("#leaveForm");
leaveForm.addEventListener("submit", (e) => {
  e.preventDefault();

  Swal.fire({
    text: "회원탈퇴를 하시겠습니까?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "탈퇴",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      e.target.submit();
    } else {
      return;
    }
  });
});
