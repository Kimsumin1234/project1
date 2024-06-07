console.log(mid);
document.querySelector("#heartList").addEventListener("click", (e) => {
  fetch(`/heart/${mid}/heartList`, {
    method: "get",
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      let tags = "";
      tags += `<p class="row justify-content close" >닫기 <i class="fa fa-times close" aria-hidden="true"></i></p>`;
      if (data.length == 0) {
        tags += `<span class="row justify-content">현재 좋아요한 목록이 없습니다.</span>`;
      }
      data.forEach((reviewDto) => {
        tags += `<a href="/review/read?rno=${reviewDto.rno}" class="row justify-content col-sm-12"><li class="list-group-item">${reviewDto.title}</li></a>`;
      });

      document.querySelector(".list-group").innerHTML = tags;
    });
});

document.querySelector(".list-group").addEventListener("click", (e) => {
  console.log(e.target);
  if (e.target.classList.contains("close")) {
    let tags = "";
    document.querySelector(".list-group").innerHTML = tags;
  }
});
