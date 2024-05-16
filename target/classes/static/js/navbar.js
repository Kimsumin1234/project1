const navItem = document.querySelectorAll(".nav-item");
console.log(navItem);

navItem.forEach((li) => {
  console.log(li);
  li.addEventListener("click", (e) => {
    console.log(e.target);
  });
});
