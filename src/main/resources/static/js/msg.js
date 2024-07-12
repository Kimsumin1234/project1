// 기본 템플릿 사용
// function shareMessage() {
//   Kakao.Share.sendDefault({
//     objectType: "feed",
//     content: {
//       title: "SavingPaws",
//       description: "SavingPaws 입양게시판",
//       imageUrl: "https://image.dongascience.com/Photo/2022/06/6982fdc1054c503af88bdefeeb7c8fa8.jpg",
//       url: window.location.href,
//       link: {
//         mobileWebUrl: "http://localhost:8080/",
//         webUrl: "http://localhost:8080/",
//       },
//     },
//     buttons: [
//       {
//         title: "웹으로 보기",
//         link: {
//           mobileWebUrl: "http://localhost:8080/adopt/list",
//           webUrl: "http://localhost:8080/adopt/list",
//         },
//       },
//     ],
//   });
// }

// 내가 만든 템플릿 사용
// function shareMessage() {
//   const url = window.location.href;

//   Kakao.Share.sendCustom({
//     templateId: 109952,
//     templateArgs: {
//       title: "SavingPaws",
//       description: "SavingPaws 홈페이지",
//       url: url,
//     },
//   });
// }

Kakao.init("640f36c2cc3f107ac975b399758b0d28");

// 현재위치링크공유
function shareMessage() {
  Kakao.Link.sendDefault({
    objectType: "feed", // 객체 타입은 '피드'로 설정합니다.
    content: {
      title: "SavingPaws", // 공유할 컨텐츠의 제목을 설정합니다.
      description: "SavingPaws", // 공유할 컨텐츠의 설명을 설정합니다.
      imageUrl:
        "https://postfiles.pstatic.net/MjAyNDA3MTFfMjk2/MDAxNzIwNjg4OTYwMDEz.QSt01728hMxY2_Kc-BSJq50GYLU1zprboSQnb3vADJQg.NRmCI4joxqF6SXbG0yKu2aJXpQn-B4YH4wx7kH_U5vcg.PNG/logo_(1).png?type=w773", // 공유할 이미지 URL을 설정합니다.
      imageWidth: 800,
      imageHeight: 400,
      link: {
        mobileWebUrl: window.location.href, // 모바일 웹 URL을 현재 페이지의 URL로 설정합니다.
        webUrl: window.location.href, // 웹 URL을 현재 페이지의 URL로 설정합니다.
      },
    },
    buttons: [
      {
        title: "웹으로 보기", // 버튼의 텍스트를 설정합니다.
        link: {
          mobileWebUrl: window.location.href, // 모바일 웹 URL을 현재 페이지의 URL로 설정합니다.
          webUrl: window.location.href, // 웹 URL을 현재 페이지의 URL로 설정합니다.
        },
      },
    ],
  });
}

// Twitter
function shareTwitter() {
  const text = "SavingPaws";
  const url = encodeURI(window.location.href);
  window.open("https://twitter.com/intent/tweet?text=" + url + text);
}

// NAVER
function shareNaver() {
  const title = "SavingPaws";
  const url = encodeURI(window.location.href);
  const shareURL = window.open("https://share.naver.com/web/shareView?url=" + url + "&title=" + title);
  document.location = shareURL;
}
