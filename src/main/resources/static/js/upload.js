// fileInput 찾기
const fileInput = document.querySelector("#fileInput");

// 최대 파일 크기 (1MB) ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ2025/01/07 추가
const MAX_FILE_SIZE = 1 * 1024 * 1024;

// function checkExtension(fileName) {
//   // 정규식 사용
//   const regex = /(.*?).(png|gif|jpg)$/;

//   // txt=>, 이미지 =>
//   console.log(regex.test(fileName));
//   return regex.test(fileName);
// }

// 파일 확장자 검사 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ2025/01/07 추가
function checkExtension(fileName) {
  const regex = /\.(png|gif|jpg|jpeg)$/i; // 허용되는 확장자
  console.log(`파일 ${fileName} 확장자 검사:`, regex.test(fileName));
  return regex.test(fileName);
}

// 파일 크기 검사 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ2025/01/07 추가
function checkFileSize(file) {
  const isValidSize = file.size <= MAX_FILE_SIZE;
  console.log(`파일 ${file.name} 크기 검사:`, isValidSize);
  return isValidSize;
}

// 업로드 파일 보여주기 찾기
function showUploadImages(arr) {
  console.log("showUploadIamges", arr);

  const uploadResult = document.querySelector(".uploadResult ul");

  let tags = "";

  arr.forEach((obj, idx) => {
    tags += `<li data-name="${obj.fileName}" data-path="${obj.folderPath}" data-uuid="${obj.uuid}" ><div>`;
    tags += `<a href="" ><img src="/upload/display?fileName=${obj.thumbImageURL}" class=""></a>`;
    tags += `<span class="text-sm d-inline-block mx-1"></span>`;
    tags += `<a href="#" data-file="${obj.imageURL}"><i class="fa fa-times" data-file=""></i></a>`;
    tags += `</div></li>`;
  });
  uploadResult.insertAdjacentHTML("beforeend", tags);
}

// 파일 선택 시 처리 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ2025/01/07 추가
fileInput.addEventListener("change", (e) => {
  const files = e.target.files;
  const formData = new FormData();
  let validFiles = true; // 모든 파일이 유효한지 여부

  for (let file of files) {
    // 확장자와 크기 검사
    if (!checkExtension(file.name)) {
      alert("png, gif, jpg, jpeg 파일만 업로드 가능합니다.");
      validFiles = false;
      break;
    }
    if (!checkFileSize(file)) {
      alert("이미지 1개당 크기 제한은 1MB 미만 입니다.");
      validFiles = false;
      break;
    }
    formData.append("uploadFiles", file);
  }

  if (!validFiles) {
    e.target.value = ""; // 입력 초기화
    return;
  }

  console.log("유효한 파일만 업로드 처리");
  fetch("/imageupload/uploadAjax", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      showUploadImages(data);
    });
});
// document.querySelector("#fileInput").addEventListener("change", (e) => {
//   // fileInput change 이벤트
//   // checkExtension() 호출
//   // 이미지 파일이라면 FormData() 객체 생성 후
//   // append
//   const files = e.target.files; // input 안에 파일찾기

//   const formData = new FormData();

//   for (let index = 0; index < files.length; index++) {
//     if (checkExtension(files[index].name)) {
//       formData.append("uploadFiles", files[index]);
//     }
//   }

//   for (const value of formData.values()) {
//     console.log(value);
//   }

//   console.log(formData.innerHTML);

//   fetch("/upload/uploadAjax", {
//     method: "post",
//     headers: {
//       "X-CSRF-TOKEN": csrfValue,
//     },
//     body: formData,
//   })
//     .then((response) => response.json())
//     .then((data) => {
//       console.log(data);

//       showUploadImages(data);
//     });
// });

// form submit 기능 중지
// uploadResult ul li 태그 요소 가져오기
document.querySelector("#register-form").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  // 첨부파일 정보 수집
  const attachInfos = document.querySelectorAll(".uploadResult ul li");
  console.log(attachInfos);

  // 수집된 정보를 폼 태그 자식으로 붙여넣기
  let result = "";
  if (attachInfos.length == 0) {
    result += `<input type='hidden' value='' name='reviewImageDtos[0].path' >`;
    result += `<input type='hidden' value='' name='reviewImageDtos[0].uuid' >`;
    result += `<input type='hidden' value='' name='reviewImageDtos[0].imagename' >`;
  } else {
    attachInfos.forEach((obj, idx) => {
      // hidden 3개 => MovieImageDto 객체 하나로 변경(spring에서 대입해줌 알려주기만 하면 됨) list이기 때문에 []까지 입력
      result += `<input type='hidden' value='${obj.dataset.path}' name='reviewImageDtos[${idx}].path' >`;
      result += `<input type='hidden' value='${obj.dataset.uuid}' name='reviewImageDtos[${idx}].uuid' >`;
      result += `<input type='hidden' value='${obj.dataset.name}' name='reviewImageDtos[${idx}].imagename' >`;
    });
  }
  form.insertAdjacentHTML("beforeend", result);

  console.log("폼 제출", form);

  form.submit();
});
