const careAddr = document.querySelector("#careAddr");
const totalNumber = document.querySelector("#totalNumber");
const startDate = document.querySelector("#startDate");
const endDate = document.querySelector("#endDate");
const chartForm = document.querySelector("#chartForm");

chartForm.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log(startDate.value);
  console.log(endDate.value);
});

// 세자리마다 콤마 찍는 함수
function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 숫자 카운트 에니메이션 함수
function numberAnimate(end, document) {
  $({ val: 0 }).animate(
    { val: end },
    {
      duration: end,
      step: function () {
        var num = numberWithCommas(Math.floor(this.val));
        document.text(num);
      },
      complete: function () {
        var num = numberWithCommas(Math.floor(this.val));
        document.text(num);
      },
    }
  );
}

let result = "";
const chartLoaded = () => {
  fetch(`/chart/list/${careAddr.value}`, { method: "get" })
    .then((response) => response.json())
    .then((data) => {
      // console.log(data);
      // 전체마리수
      const totalNumber = $("#totalNumber");
      numberAnimate(data.length, totalNumber);

      var dnumber = 0;
      var cnumber = 0;
      var enumber = 0;
      data.forEach((addr) => {
        if (addr.kindCd.indexOf("[개]") != -1) {
          dnumber += 1;
        }
        if (addr.kindCd.indexOf("[고양이]") != -1) {
          cnumber += 1;
        }
        if (addr.kindCd.indexOf("[기타축종]") != -1) {
          enumber += 1;
        }
      });

      // 개 마리수
      const dogsNumber = $("#dogsNumber");
      numberAnimate(dnumber, dogsNumber);
      // 고양이 마리수
      const catsNumber = $("#catsNumber");
      numberAnimate(cnumber, catsNumber);
      // 기타 마리수
      const etcNumber = $("#etcNumber");
      numberAnimate(enumber, etcNumber);

      // 리스트 영역
      result = "";
      data.forEach((chart) => {
        result += `<tr>
          <th scope="row">${chart.chId}</th>
          <td>${chart.happenDt}</td>
          <td>${chart.processState}</td>
          <td>${chart.kindCd}</td>
          <td>${chart.careNm}</td>
          <td>${chart.careAddr}</td>
          <td>${chart.careTel}</td>
        </tr>
        `;
      });

      document.querySelector("#chartList").innerHTML = result;
    });
};

chartLoaded();

careAddr.addEventListener("change", () => {
  chartLoaded();
});
