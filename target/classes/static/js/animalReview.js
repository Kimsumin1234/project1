// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// /reviews/mno/all 요청 처리
const reviewsLoaded = () => {
  fetch(`/animalReviews/${sId}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 리뷰 총 개수 변경
      document.querySelector(".review-cnt").innerHTML = data.length;
      if (data.length > 0) reviewList.classList.remove("hidden");

      let result = "";
      data.forEach((review) => {
        result += `<div class="pt-5 mt-5"> <h3 class="mb-5"> </h3>`;
        result += `<ul class="comment-list"> <li class="comment"> <div class="vcard bio">`;
        result += `<img th:src="@{/assets/images/person_01.png}" alt="Image placeholder" /></div>`;
        result += `<div class="comment-body" ><h3>${review.nickname}</h3>`;
        result += `<div class="meta"></div>`;
        result += `<p>${review.text}</p>`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></li></ul>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded();
