document.addEventListener("DOMContentLoaded", function () {
  var mapDataElement = document.getElementById("map-data");
  var careNm = mapDataElement.getAttribute("data-care-nm");
  var careAddr = mapDataElement.getAttribute("data-care-addr");

  var contentString = [
    '<div class="desc">',
    "<h5>" + careNm + "</h5>",
    "<p>주소 : " + careAddr + "</p>",
    "<p>★ 입양 문의는 해당보호소로 문의해주시길 바랍니다.</p>",
    "</div>",
  ].join("");

  var latitude = parseFloat(document.querySelector("#latitude").value);
  var longitude = parseFloat(document.querySelector("#longitude").value);

  console.log(parseFloat(latitude));
  console.log(parseFloat(longitude));

  var position = new naver.maps.LatLng(latitude, longitude);

  var map = new naver.maps.Map("map", {
    center: position,
    zoomControl: true,
    zoom: 17,
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

  var infowindow = new naver.maps.InfoWindow({
    content: contentString,
    maxWidth: 400,
    backgroundColor: "#FDFDFD",
    borderColor: "#CFE2F3",
    borderWidth: 5,
    anchorSize: new naver.maps.Size(30, 30),
    anchorSkew: true,
    anchorColor: "#FDFDFD",
    pixelOffset: new naver.maps.Point(20, -20),
  });

  naver.maps.Event.addListener(marker, "click", function (e) {
    if (infowindow.getMap()) {
      infowindow.close();
    } else {
      infowindow.open(map, marker);
    }
  });

  infowindow.open(map, marker);
});
