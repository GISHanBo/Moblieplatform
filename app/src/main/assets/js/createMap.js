var map = L.map('map', {
	attributionControl: false,
	zoomControl: false,
	measureControl: true,
//	center: [0, 0],
//	zoom: 1,
	maxZoom: 30,
	minZoom: 1
});

var multiPointMode=false;
var heatMapMode=false;
var heatMap=null;
map.on("load",function(e){
window.Android.onMapLoad();
})
map.setView([0,0],1);

map.on("zoomend", function(e) {
	window.Android.onZoomChange(e.target.getZoom());
})
map.on("moveend",centerChange);
map.on("zoomend",centerChange);
function centerChange(e){
	var center=e.target.getCenter();
	window.Android.onCenterChange(center.lat,center.lng);
};
map.on("moveend",function(e){
	var bounds=e.target.getBounds();
	if(multiPointMode){
	window.Android.onViewChange(bounds._southWest.lat,bounds._southWest.lng,bounds._northEast.lat,bounds._northEast.lng);
	}
	if(heatMapMode){
	window.Android.onHeatViewChange(bounds._southWest.lat,bounds._southWest.lng,bounds._northEast.lat,bounds._northEast.lng);
	}
});
function getBounds(){
	var bounds=map.getBounds();
	if(multiPointMode){
	window.Android.onViewChange(bounds._southWest.lat,bounds._southWest.lng,bounds._northEast.lat,bounds._northEast.lng);
	}
	if(heatMapMode){
	window.Android.onHeatViewChange(bounds._southWest.lat,bounds._southWest.lng,bounds._northEast.lat,bounds._northEast.lng);
	}
}
