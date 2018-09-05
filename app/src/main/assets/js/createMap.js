var map = L.map('map', {
	attributionControl: false,
	zoomControl: false,
	measureControl: true,
	maxZoom: 30,
	minZoom: 3
});

var heatMap=null;
map.on("load",function(e){
window.Android.onMapLoad();
})
map.setView([38, 113],5);


var events={};//地图事件集合

map.on("zoomend", function(e) {
	window.Android.onZoomChange(e.target.getZoom());
});
map.on("moveend",centerChange);
map.on("zoomend",centerChange);
function centerChange(e){
	var center=e.target.getCenter();
	window.Android.onCenterChange(center.lat,center.lng);
}

