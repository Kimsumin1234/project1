// parseFloat → parseDouble(없는건가?)
// Flaot 소수점 7번째까지 가능
const latitude = parseFloat(document.querySelector("#latitude").value);
const longitude = parseFloat(document.querySelector("#longitude").value);

console.log(parseFloat(latitude));
console.log(parseFloat(longitude));

// 기본 마커 형태 코드
// var map = new naver.maps.Map("map", {
//   center: new naver.maps.LatLng(latitude, longitude),
//   zoomControl: true, //줌 컨트롤의 표시 여부
//   zoom: 18,
// });

// var marker = new naver.maps.Marker({
//   position: new naver.maps.LatLng(latitude, longitude),
//   map: map,
// });

// 이미지 마커 넣는 코드
var position = new naver.maps.LatLng(latitude, longitude);

var map = new naver.maps.Map("map", {
  center: position,
  zoomControl: true, //줌 컨트롤의 표시 여부
  zoom: 18,
});

var markerOptions = {
  position: position,
  map: map,
  icon: {
    url: "/images/dog50.png",
    size: new naver.maps.Size(50, 50),
    origin: new naver.maps.Point(0, 0),
    anchor: new naver.maps.Point(16, 32),
  },
};

var marker = new naver.maps.Marker(markerOptions);

var marker = new naver.maps.Marker(markerOptions);

// min/max 줌 레벨
$("#min-max-zoom").on("click", function (e) {
  e.preventDefault();

  if (map.getOptions("minZoom") === 10) {
    map.setOptions({
      minZoom: 7,
      maxZoom: 21,
    });
    $(this).val(this.name + ": 7 ~ 21");
  } else {
    map.setOptions({
      minZoom: 10,
      maxZoom: 21,
    });
    $(this).val(this.name + ": 10 ~ 21");
  }
});

// 관성 드래깅 옵션
$("#kinetic").on("click", function (e) {
  e.preventDefault();

  if (map.getOptions("disableKineticPan")) {
    map.setOptions("disableKineticPan", false); //관성 드래깅 켜기
    $(this).addClass("control-on");
  } else {
    map.setOptions("disableKineticPan", true); //관성 드래깅 끄기
    $(this).removeClass("control-on");
  }
});
