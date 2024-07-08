const actionForm = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  e.preventDefault();
  if (!confirm("삭제하시겠습니까?")) {
    return;
  }
  actionForm.submit();
});

// 영화 등록
// - 이미지 등록 시 x 클륵
//  => 서버 폴더에서 이미지 제거
// - 사진만 첨부한 후 글을 작성 안하는 경우
//  => 서버에 사진만 남게됨
// 영화수정
// - 영화 등록 로직과 동일
// - 이미지 등록 시 x 클륵
//  => 이미지 li만 제거
// - 수정 클릭
// => 기존 이미지 제거
// => 다시 수집된 이미지 정보 삽입.
// x 를 누르면 파일 삭제 요청
// a 태그 기능 중지 => href 값 가져와서 화면 출력

document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();
  // li 태그 가져오기
  const currentLi = e.target.closest("li");
  if (confirm("정말로 삭제하시겠습니다?")) {
    currentLi.remove();
  }
});

// 이걸 upload에서 register와 같이 씀
// 수정 클릭시 기능 중지
// li 정보 수집해서 hidden 태그 작성 후 form 삽입
// const attachInfos = document.querySelectorAll(".uploadResult ul li");
// document.querySelector(".btn-success").addEventListener("click", (e) => {
//   let result = "";
//   attachInfos.forEach((obj, idx) => {
//     // hidden 3개 => MovieImageDto 객체 하나로 변경(spring에서 대입해줌 알려주기만 하면 됨) list이기 때문에 []까지 입력
//     result += `<input type='hidden' value='${obj.dataset.path}' name='movieImageDtos[${idx}].path' >`;
//     result += `<input type='hidden' value='${obj.dataset.uuid}' name='movieImageDtos[${idx}].uuid' >`;
//     result += `<input type='hidden' value='${obj.dataset.name}' name='movieImageDtos[${idx}].imgname' >`;
//   });
//   form.insertAdjacentHTML("beforeend", result);
// });

document.addEventListener("DOMContentLoaded", (event) => {
  const titleInput = document.getElementById("title");
  const charCount1 = document.getElementById("charCount1");

  titleInput.addEventListener("input", () => {
    charCount1.textContent = `${titleInput.value.length}`;
  });
});

document.addEventListener("DOMContentLoaded", (event) => {
  const textInput = document.getElementById("text");
  const charCount2 = document.getElementById("charCount2");

  textInput.addEventListener("input", () => {
    charCount2.textContent = `${textInput.value.length}`;
  });
});
