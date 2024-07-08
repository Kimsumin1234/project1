console.log(mid);
document.querySelector("#heartList").addEventListener("click", (e) => {
  fetch(`/heart/${mid}/heartList`, {
    method: "get",
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      let tags = "";

      // tags += `<p class="row justify-content-end close" ><i class="fa fa-times close" aria-hidden="true"></i></p>`;

      // tags += `<button type="button" class="close" data-dismiss="modal">&times;</button>`;

      if (data.length == 0) {
        tags += `<span class="row justify-content-center">현재 좋아요한 목록이 없습니다.</span>`;
      } else {
        tags += `<span class="row justify-content-center pt-2">좋아요한 목록</span>`;
      }

      tags += `<ul class="list-group list-group-flush">`;
      data.forEach((reviewDto) => {
        tags += `<li class="heartlist list-group-item"><a href="/review/read?rno=${reviewDto.rno}" class="row col-sm-12">${reviewDto.title}</a></li>`;
      });
      tags += `</ul>`;

      document.querySelector(".list-group").innerHTML = tags;
    });
});
