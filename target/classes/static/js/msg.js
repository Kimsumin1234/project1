Kakao.init("640f36c2cc3f107ac975b399758b0d28");

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
function shareMessage() {
  Kakao.Share.sendCustom({
    templateId: 109952,
    templateArgs: {
      title: "SavingPaws",
      description: "SavingPaws 입양게시판",
      url: window.location.href,
    },
  });
}
