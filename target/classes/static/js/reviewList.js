console.log(mid);
document.querySelector("#heartList").addEventListener("click", (e) => {
  fetch(`/heart/${mid}/heartList`, {
    method: "get",
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      let tags = "";
      data.forEach((reviewDto) => {
        tags += `<a href="/review/read?rno=${reviewDto.rno}"><li class="list-group-item">${reviewDto.title}</li></a>`;
      });

      document.querySelector(".list-group").insertAdjacentHTML("beforeend", tags);
    });
});
