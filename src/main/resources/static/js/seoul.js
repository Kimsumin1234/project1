const careAddr = document.querySelector("#careAddr");
const totalNumber = document.querySelector("#totalNumber");
const startDate = document.querySelector("#startDate");
const endDate = document.querySelector("#endDate");
const chartForm = document.querySelector("#chartForm");

// 차트 띄우는 함수
function makeCharts(keyword, start, end, y1, y2, y3, y4, y5, y6, y7) {
  if (keyword == "서울") {
    keyword = "전체";
  }

  var chart1 = new CanvasJS.Chart("chartContainer1", {
    animationEnabled: true,
    animationDuration: 500,
    theme: "dark2", //"light1"
    title: {
      text: "서울특별시 " + keyword + " 유기동물현황",
      fontFamily: "돋음",
      fontWeight: "bold",
      // borderThickness: 2,
      padding: 1,
      // backgroundColor: "#fc9e7b",
      // borderColor: "white",
      // cornerRadius: 10,
      fontColor: "#fc9e7b",
      fontSize: 25,
    },
    subtitles: [
      {
        text: "(2024.07.01 기준)",
        fontColor: "#fc9e7b",
        //Uncomment properties below to see how they behave
        //fontColor: "red",
        //fontSize: 30
      },
    ],
    data: [
      {
        type: "doughnut",
        startAngle: 0,
        innerRadius: 100,
        indexLabelFontSize: 15,
        indexLabel: "{label} #percent%",
        // toolTipContent: "<b>{label}:</b> {y} (#percent%)",
        toolTipContent: "<b>{label}:</b> {y} 마리",
        connectNullData: true,
        dataPoints: [
          { y: y7, label: "방사" },
          { y: y5, label: "기증" },
          { y: y1, label: "보호중" },
          { y: y6, label: "안락사" },
          { y: y2, label: "자연사" },
          { y: y3, label: "반환" },
          { y: y4, label: "입양", exploded: true },
        ],
      },
    ],
  });

  // null 값이면 라벨 안뜨게 하는 함수
  function hideIndexLabel() {
    var length = chart1.options.data[0].dataPoints.length;
    for (var i = 0; i < length; i++) {
      if (chart1.options.data[0].dataPoints[i].y === 0 || chart1.options.data[0].dataPoints[i].y === null) {
        chart1.options.data[0].dataPoints[i].indexLabel = " ";
        chart1.options.data[0].dataPoints[i].indexLabelLineThickness = 0;
      } else chart1.options.data[0].dataPoints[i].indexLabel = chart1.options.data[0].dataPoints[i].indexLabel;
    }
  }

  hideIndexLabel();
  chart1.render();

  var chart2 = new CanvasJS.Chart("chartContainer2", {
    animationEnabled: true,
    theme: "dark2",
    title: {
      text: start + " ~ " + end,
      padding: 10,
      fontColor: "#fc9e7b",
      fontSize: 25,
      // fontWeight: "bold",
    },
    axisY: {
      gridThickness: 0,
      tickLength: 0,
    },
    axisX: {
      labelFontFamily: "돋음",
      labelFontWeight: "bolder",
      interval: 1,
    },
    // axisY2: {
    //   interlacedColor: "rgba(1,77,101,.2)",
    //   gridColor: "rgba(1,77,101,.1)",
    //   title: "Number of Companies",
    // },
    data: [
      {
        bevelEnabled: true,
        type: "bar",
        // name: "companies",
        // color: "#014D65",
        // axisYType: "secondary",
        indexLabelFontSize: 15,
        indexLabelFontFamily: "돋음",
        indexLabelFontWeight: "bolder",
        indexLabel: "{y} 마리",
        toolTipContent: "<b>{label}:</b> {y} 마리",
        dataPoints: [
          { y: y7, label: "방사" },
          { y: y5, label: "기증" },
          { y: y1, label: "보호중" },
          { y: y6, label: "안락사" },
          { y: y2, label: "자연사" },
          { y: y3, label: "반환" },
          { y: y4, label: "입양" },
        ],
      },
    ],
  });
  chart2.render();
}

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

// 리스트 가져오는 함수
let result = "";
let arr1 = [];
const chartLoaded = (keyword, start, end) => {
  fetch(`/chart/list/${keyword}/${start}/${end}`, { method: "get" })
    .then((response) => response.json())
    .then((data) => {
      // console.log(data);
      // 전체마리수 카운트
      const totalNumber = $("#totalNumber");
      numberAnimate(data.length, totalNumber);

      // 개, 고양이, 기타 카운트 숫자
      var dnumber = 0;
      var cnumber = 0;
      var enumber = 0;
      // 동물 상태(processState) 숫자
      var bohoNum = 0;
      var jayunNum = 0;
      var banhNum = 0;
      var ipyanNum = 0;
      var gizunNum = 0;
      var anrakNum = 0;
      var bangNum = 0;
      // 리스트 출력
      result = "";
      data.forEach((chart) => {
        // 개, 고양이, 기타 카운트 숫자
        if (chart.kindCd.indexOf("[개]") != -1) {
          dnumber += 1;
        }
        if (chart.kindCd.indexOf("[고양이]") != -1) {
          cnumber += 1;
        }
        if (chart.kindCd.indexOf("[기타축종]") != -1) {
          enumber += 1;
        }
        // 동물 상태 숫자
        if (chart.processState.indexOf("보호중") != -1) {
          bohoNum += 1;
        }
        if (chart.processState.indexOf("자연사") != -1) {
          jayunNum += 1;
        }
        if (chart.processState.indexOf("반환") != -1) {
          banhNum += 1;
        }
        if (chart.processState.indexOf("입양") != -1) {
          ipyanNum += 1;
        }
        if (chart.processState.indexOf("기증") != -1) {
          gizunNum += 1;
        }
        if (chart.processState.indexOf("안락사") != -1) {
          anrakNum += 1;
        }
        if (chart.processState.indexOf("방사") != -1) {
          bangNum += 1;
        }
        // 리스트 출력(보호소 리스트 중복제거)
        if (!arr1.includes(chart.careAddr)) {
          arr1.push(chart.careAddr);
          result += `<tr>
            <th scope="row">${chart.careNm}</th>
            <td>${chart.careAddr}</td>
            <td>${chart.careTel}</td>
          </tr>
          `;
        }
      });

      // 개 마리수 카운트
      const dogsNumber = $("#dogsNumber");
      numberAnimate(dnumber, dogsNumber);
      // 고양이 마리수 카운트
      const catsNumber = $("#catsNumber");
      numberAnimate(cnumber, catsNumber);
      // 기타 마리수 카운트
      const etcNumber = $("#etcNumber");
      numberAnimate(enumber, etcNumber);
      // 동물 상태 숫자
      makeCharts(keyword, start, end, bohoNum, jayunNum, banhNum, ipyanNum, gizunNum, anrakNum, bangNum);
      // 리스트 영역
      arr1 = [];
      document.querySelector("#chartList").innerHTML = result;
    });
};

// 기본 리스트
chartLoaded("서울", "2023-01-01", "2024-07-01");

// 검색후 리스트
chartForm.addEventListener("submit", (e) => {
  e.preventDefault();
  // console.log(startDate.value);
  // console.log(endDate.value);
  // console.log(careAddr.value);
  if (!startDate.value) {
    startDate.value = "2023-01-01";
  }
  if (!endDate.value) {
    endDate.value = "2024-07-01";
  }
  chartLoaded(careAddr.value, startDate.value, endDate.value);
});
