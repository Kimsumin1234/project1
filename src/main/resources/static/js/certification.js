const addon1 = document.querySelector("#button-addon1");
const certification = document.querySelector("#certification");
const timeMin = document.querySelector("#timeMin");
const timeSec = document.querySelector("#timeSec");
const phone = document.querySelector("#phone");
const danger = document.querySelector(".text-danger");
console.log(phone);

function checkPhone(pNum) {
  const regex = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$"/;
  console.log(regex.test(pNum));
  return regex.test(pNum);
}

addon1.addEventListener("click", () => {
  const formData = new FormData();
  formData.append("phone", phone.value);
  //   console.log(phone.value);
  //   for (const key of formData.keys()) {
  //     console.log(key);
  //   }
  //   for (const value of formData.values()) {
  //     console.log(value);
  //   }
  fetch(`/send-one`, {
    method: "post",
    body: formData,
  })
    .then((reponse) => reponse.json())
    .then((data) => {
      console.log(data);
      if (data.phone) {
        danger.innerHTML = data.phone;
      } else {
        danger.innerHTML = "";
        certification.style.visibility = "visible";
      }
    });

  let time = 180;

  setInterval(function () {
    if (time > 0) {
      // >= 0 으로하면 -1까지 출력된다.
      time = time - 1; // 여기서 빼줘야 3분에서 3분 또 출력되지 않고, 바로 2분 59초로 넘어간다.
      let min = Math.floor(time / 60);
      let sec = String(time % 60).padStart(2, "0");
      timeMin.innerText = min;
      timeSec.innerText = sec;
      // time = time - 1
    } else {
      certification.style.visibility = "hidden";
    }
  }, 1000);
});
