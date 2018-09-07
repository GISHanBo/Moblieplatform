drawObj = {};

/**
 * 初始化绘制
 */
function startDraw() {
    //初始化设备点击事件
    drawObj.deviceClick = function (e) {
        var marker=e.target;
        var obj={
            id:marker.id,
            name:marker.name||"",
            type:marker.type||"",
            height:marker.height||0,
            material:marker.material||"",
            sLine:marker.sLine||"",
            picture:marker.picture||"",
            category:marker.category||""
        };
        obj=JSON.stringify(obj);
        window.Android.onDeviceClick(obj);
    };
    //初始化线点击事件
    drawObj.lineClick = function (e) {
        window.Android.onLineClick();
    };
}

/**
 * 绘制不同类型的设备
 * @param {number} type
 */
function drawDevice(type) {
    var center = map.getCenter();
    var iconUrl = "";
    switch (type) {
        case 0:
            iconUrl = "pole";
            break;
        case 1:
            iconUrl = "well";
            break;
        case 2:
            iconUrl = "switchgear";
            break;
        case 3:
            iconUrl = "box_branch";
            break;
        case 4:
            iconUrl = "box_distribution";
            break;
        case 5:
            iconUrl = "fold_point";
            break;
        default:
            break;
    }

    var myIcon = L.icon({
        iconUrl: "icon/draw/" + iconUrl + ".png",
        iconSize: [30, 30],
        iconAnchor: [15, 15]
    });
    //生成点
    var marker = L.marker(center, {
        icon: myIcon,
        attribution: "mssDevice",
        zIndexOffset: 501,
        draggable: true,
        autoPan: true
    }).addTo(map);
    marker.id = Date.now();
    switch (type) {
        case 0:
            marker.type="电杆";
            break;
        case 1:
            marker.type="电缆井";
            break;
        case 2:
            marker.type="开关柜";
            break;
        case 3:
            marker.type="电缆分支柜";
            break;
        case 4:
            marker.type="配电箱";
            break;
        case 5:
            marker.type="电缆折点";
            break;
        default:break;
    }
    marker.on('click',drawObj.deviceClick);
}

/**
 * 自动画线
 * @param point
 */
function drawLine(point) {
    pre_devices.push([point.lat, point.lng]);
    if (pre_devices.length == 2) {
        currentLine = L.polyline(pre_devices,
            {
                attribution: "mssLine",
                zIndexOffset: 500
            }
        ).addTo(map);
        currentLine.id = Date.now();
    } else if (pre_devices.length > 2) {
        currentLine.addLatLng(point);
    }


}

/**
 * 绘制下一个物体
 */
function nextObj() {

}

/**
 * 移除对应id的设备
 * @param {number} id
 */
function removeDevice(id) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssDevice" && layer.id == id) {
            map.removeLayer(layer);
        }
    });
}

/**
 * 移除设备对应的线
 * @param {number} id
 */
function removeLine(id) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLine" && layer.id == id) {
            map.removeLayer(layer);
        }
    });
}

/**
 * 根据id设置线样式
 * @param id
 * @param color
 * @param width
 */
function setLineStyle(id, color, width) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLine" && layer.id == id) {
            layer.setStyle({
                color: color,
                weight: width
            })
        }
    });
}

/**
 * 清除绘制
 */
function clearDraw() {
    removeLayer("mssDevice");
    removeLayer("mssLine");
    removeLayer("mssLabel");
    delete drawObj.deviceClick;
    delete drawObj.lineClick;
    nextObj();
}

/**
 * 指定位置添加标注
 * @param lat
 * @param lng
 * @param text
 * @param lineId
 */
function addLabel(lat, lng, text, lineId) {
    var myIcon = L.divIcon({
        html: "狗子",
        className: 'mssLabel',
        iconSize: 30
    });
    var label = L.marker([31.864942016, 117.2882028929], {
        icon: myIcon,
        attribution: "mssLabel",
        zIndexOffset: 502
    }).addTo(map);
    label.lineId = lineId;
}

function getLineDisTo(line, index) {

}

/**
 * 返回线的点数
 * @param line
 * @returns {*}
 */
function getLinePCount(line) {
    return line.getLatLngs().length;
}

/**
 * 通过ID查询线
 * @param id
 */
function getLineByID(id) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLine" && layer.id == id) {
            return layer;
        }
    });
}

/**
 * 绘制线的标注
 * @param {number} id
 */
function showLineLabels(id) {
    var line = getLineByID(id);
    var points = line.getLatLngs();

}

/**
 * 更新id相同的设备信息
 * @param {JSON}json
 */
function updateDeviceByID(json) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssDevice" && layer.id == json.id) {
            layer.name=json.name;
            layer.type=json.type;
            layer.height=json.height;
            layer.material=json.material;
            layer.sLine=json.sLine;
            layer.picture=json.picture;
            layer.category=json.category;
        }
    });
}

/**
 * 根据ID查找设备
 * @param {number}id
 */
function getDeviceByID(id) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssDevice" && layer.id == id) {
            return layer;
        }
    });
}



