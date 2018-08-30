/**
 * 设置地图中心
 */
function setCenter(lat, lng) {
    map.panTo([lat, lng]);
}

/**
 * 根据坐标范围调整经纬度
 * @param {number} latMin
 * @param {number} lngMin
 * @param {number} latMax
 * @param {number} lngMax
 */
function setView(latMin, lngMin, latMax, lngMax) {
    map.fitBounds([
        [latMin, lngMin],
        [latMax, lngMax]
    ]);
}

/**
 * 设置地图的缩放等级,默认1-30级
 * @param {Number} minZoom 最小缩放等级
 * @param {Number} maxZoom 最大缩放等级
 */
function setZoomLimit(minZoom, maxZoom) {
    map.setMinZoom(minZoom);
    map.setMaxZoom(maxZoom);
}

/**
 * 缩小
 */
function zoomOut() {
    map.zoomOut(1);
}

/**
 * 放大
 */
function zoomIn() {
    map.zoomIn(1);
}

/**
 * 设置地图缩放等级
 * @param {number} level
 */
function setZoom(level) {
    map.setZoom(level)
}

/**
 * 添加高亮显示
 */
function addHighlight(lat, lng, rotate) {
    var myIcon = L.divIcon({
        html: "<img src='lib/leaflet/images/locate.png' style='transform: rotate(" + rotate + "deg);' />",
        className: 'my-locate-icon'
    });

    L.marker([lat, lng], {
        icon: myIcon,
        iconSize: [80, 80],
        iconAnchor: [40, 40],
        attribution: "高亮显示",
        zIndexOffset: 1000,
    }).addTo(map);
}

/**
 * 清除高亮
 */
function clearHighlight() {
    removeLayer("高亮显示");
}

/**
 * 开启点击地图弹出经纬度
 */
function showLatLng() {
    events.showLatLng = function (e) {
        if (hasLayer("popup")) {
            clearShowLatLng()
        }
        L.popup({
            attribution: "popup"
        })
            .setLatLng([e.latlng.lat, e.latlng.lng])
            .setContent("纬度：" + e.latlng.lat + "<br/>经度：" + e.latlng.lng)
            .openOn(map);
    }
    map.on("click", events.showLatLng);
}

/**
 * 关闭点击地图弹出经纬度
 */
function clearShowLatLng() {
    removeLayer("popup");
    map.off("click", events.showLatLng);
    delete events.showLatLng;
}

/**
 * 添加geoJson
 * @param {obj} geoJSON
 */
function addGeoJson(geoJSON) {
    L.geoJSON(geoJSON, {
        attribution: "geoJson"
    }).addTo(map);
}

/**
 * 添加带图标点
 * @param {Number} lat 纬度
 * @param {Number} lng  经度
 * @param {String} icon 图标名称
 * @param {Number} id 点的id
 */
function addIcon(lat, lng, icon, id) {
    var myIcon = L.icon({
        iconUrl: 'icon/' + icon,
        iconSize: [30, 30],
        iconAnchor: [15, 15]
    });
    var marker = L.marker([lat, lng], {
        icon: myIcon,
        id: id,
        attribution: "mssmarker"
    }).addTo(map);
    marker.addEventListener('click', function (e) {
        window.Android.iconClick(e.latlng.lat, e.latlng.lng, e.target.options.id);
    });
}

/**
 * 清除所有带图标点
 */
function removeIcons() {
    removeLayer("mssmarker");
}

/**
 * 清除geoJson图层
 */
function removeGeoJson() {
    removeLayer("geoJson");
}

/**
 * 添加WMS图层
 * @param {String} url 服务地址
 * @param {String} layers 图层名称
 * @param {String} format 格式
 * @param {String} name 自定义名称
 * @param {Number} minZoom 最小缩放等级
 * @param {Number} maxZoom 最大缩放等级
 */
function addWMSLayer(url, layers, format, name, minZoom, maxZoom) {
    L.tileLayer.wms(url, {
        layers: layers,
        format: format,
        transparent: true,
        attribution: name,
        minZoom: minZoom,
        maxZoom: maxZoom,
        zIndex: 3
    }).addTo(map);
}

/**
 * 通过图层名称移除名字
 * @param {String} name 图层名称
 */
function removeLayerByName(name) {
    removeLayer(name);
}

/**
 * 添加WMTS图层
 */
function addWMTSLayer(url, layer, tilematrixSet, format, name, minZoom, maxZoom, tileSize) {
    var ign = new L.TileLayer.WMTS(url, {
        layer: layer,
        tilematrixSet: tilematrixSet,
        tileSize: tileSize,
        format: format,
        maxZoom: maxZoom,
        minZoom: minZoom,
        attribution: name,
        zIndex: 3
    });
    map.addLayer(ign);
}

/**
 * 切换地图底图
 * @param {String} source 来源-MapBox/高德/谷歌/天地图
 * @param {String} type 地图类型-道路图/卫星图
 */
function switchBaseLayer(source, type) {
    removeLayer("mssBaseLayer");
    switch (source) {
        case "高德":
            if (type == "卫星图") {
                L.tileLayer.chinaProvider('GaoDe.Satellite.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
                L.tileLayer.chinaProvider('GaoDe.Satellite.Annotion', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            } else if (type == "卫星图无注记") {
                L.tileLayer.chinaProvider('GaoDe.Satellite.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            } else {
                L.tileLayer.chinaProvider('GaoDe.Normal.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            }
            break;
        case "天地图":
            if (type == "卫星图") {
                L.tileLayer.chinaProvider('TianDiTu.Satellite.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
                L.tileLayer.chinaProvider('TianDiTu.Satellite.Annotion', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            } else if (type == "卫星图无注记") {
                L.tileLayer.chinaProvider('TianDiTu.Satellite.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            } else {
                L.tileLayer.chinaProvider('TianDiTu.Normal.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
                L.tileLayer.chinaProvider('TianDiTu.Normal.Annotion', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            }
            break;
        case "谷歌":
            if (type == "卫星图") {
                L.tileLayer.chinaProvider('Google.Satellite.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            } else {
                L.tileLayer.chinaProvider('Google.Normal.Map', {
                    maxZoom: 22,
                    minZoom: 1,
                    attribution: "mssBaseLayer"
                }).addTo(map);
            }
            break;
        case "MapBox":
            if (type == "卫星图") {
                L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    attribution: 'mssBaseLayer',
                    maxZoom: 22,
                    minZoom: 1,
                    id: 'mapbox.streets-satellite',
                    accessToken: 'pk.eyJ1IjoiaGFtYnVnZXJkZXZlbG9wIiwiYSI6ImNqNXJtZzczcDB6aHgycW1scXZqd3FpNmcifQ.d4p32JmAhTek8BUIt3WGLw'
                }).addTo(map);
            } else {
                L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    attribution: 'mssBaseLayer',
                    maxZoom: 22,
                    minZoom: 1,
                    id: 'mapbox.streets',
                    accessToken: 'pk.eyJ1IjoiaGFtYnVnZXJkZXZlbG9wIiwiYSI6ImNqNXJtZzczcDB6aHgycW1scXZqd3FpNmcifQ.d4p32JmAhTek8BUIt3WGLw'
                }).addTo(map);
            }
            break;
        default:
            break;
    }
}

///**
// * 添加Mbtiles离线地图
// * @param {Object} path Mbtiles路径
// * @param {Object} maxZoom 最大图层缩放等级
// * @param {Object} minZoom 最小图层缩放等级
// */
//function addMbTiles(path, maxZoom, minZoom) {
//	L.tileLayer.mbTiles(path, {
//		minZoom: minZoom,
//		maxZoom: maxZoom,
//		transparent: true,
//		attribution: "mssMbtiles",
//		zIndex: 2
//	}).addTo(map);
//}
///**
// * 移除Mbtiles离线地图
// */
//function removeMbTiles() {
//	removeLayer("mssMbtiles");
//}
/**
 * 添加标准Tms地图
 * @param {Object} path Tms路径
 * @param {Object} maxZoom 最大图层缩放等级
 * @param {Object} minZoom 最小图层缩放等级
 */
function addTms(path, maxZoom, minZoom) {
    console.log(path);
    var options = {
        minZoom: minZoom,
        maxZoom: maxZoom,
        opacity: 1.0,
        attribution: 'mssTms',
        tms: false,
        zIndex: 2
    };
    layer = L.tileLayer(path, options).addTo(map);
}

/**
 * 移除TMS图层
 */
function removeTms() {
    removeLayer("mssTms");
}

/**
 * 开始海量点模式
 * @param {Boolean} bool
 */
function setMultiPointMode(bool) {
    if (bool) {
        events.multiPointChange = function (e) {
            removeIcons();
            var bounds=e.target.getBounds();
            window.Android.onViewChange(bounds._southWest.lat,bounds._southWest.lng,bounds._northEast.lat,bounds._northEast.lng);
        };
        map.on("moveend",events.multiPointChange);
        //初始化显示
        var bounds=map.getBounds();
        removeIcons();
        window.Android.onViewChange(bounds._southWest.lat,bounds._southWest.lng,bounds._northEast.lat,bounds._northEast.lng);
    } else {
        map.off("moveend",events.multiPointChange);
        removeIcons();
        delete events.multiPointChange;
    }
}

/**
 * 添加城市统计结果
 * @param {Number} lat
 * @param {Number} lng
 * @param {String} city
 * @param {Number} number
 */
function addPopup(lat, lng, city, number) {
    L.popup({
        attribution: 'mssPopup',
        className: 'mssPopup',
        closeOnClick: false,
        autoClose: false,
        closeButton: false,
        maxWidth: 600,
        minWidth: 100,
        autoPan: false
    })
        .setLatLng([lat, lng])
        .setContent('<div class="city">' + city + '</div>' + '<div class="number">' + number + '</div>')
        .openOn(map);
}

/**
 * 移除城市统计结果
 */
function removePopup() {
    removeLayer("mssPopup");
}

/**
 * 添加热力图
 */
function addHeatMap() {
    events.heatMapChange = function (e) {
        var bounds = e.target.getBounds();
        window.Android.onHeatViewChange(bounds._southWest.lat, bounds._southWest.lng, bounds._northEast.lat, bounds._northEast.lng);
    };
    heatmap = new L.DivHeatmapLayer({
        attribution: 'mssHeatMap',
    });
    heatmap.addTo(map);
    map.on("moveend", events.heatMapChange);
    //添加热力图数据后，更新显示
    var bounds = map.getBounds();
    window.Android.onHeatViewChange(bounds._southWest.lat, bounds._southWest.lng, bounds._northEast.lat, bounds._northEast.lng);
}

/**
 * 添加热力图数据
 * @param {Object} json
 */
function heatMapAddData(json) {
    heatmap.setData(json);
}

/**
 * 移除热力图
 */
function removeHeatMap() {
    removeLayer("mssHeatMap");
    heatMap = null;
    map.off("moveend", events.heatMapChange);
    delete events.heatMapChange;
}