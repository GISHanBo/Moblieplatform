var drawObj = {};

/**
 * 初始化绘制
 */
function startDrawDevice() {
    //设置为画设备模式
    drawObj.isDrawDevice = true;
    //画线时捕捉设备缓存区
    drawObj.tempDeviceId = null;
    //初始化设备点击事件
    drawObj.deviceClick = function (e) {
        if (drawObj.isDrawDevice) {
            drawObj.deviceClick_detail(e)
        } else {
            drawObj.deviceClick_catch(e)
        }
    };
    //绘制设备模式下，点击设备展示设备信息
    drawObj.deviceClick_detail = function (e) {
        var marker = e.target;
        var obj = {
            id: marker.id,
            name: marker.name || "",
            type: marker.type || "",
            height: marker.height || 0,
            material: marker.material || "",
            sLine: marker.sLine || "",
            picture: marker.picture || "",
            category: marker.category || ""
        };
        obj = JSON.stringify(obj);
        window.Android.onDeviceClick(obj);
    };
    //画线模式下，点击设备展示设备捕捉
    drawObj.deviceClick_catch = function (e) {
        var id = e.target.id;
        L.popup()
            .setLatLng(e.latlng)
            .setContent('<label onclick="closePopup(&apos;' + id + '&apos;)" style="border-bottom: 1px solid blue;line-height:14px">捕捉设备' + id + '</label>')
            .openOn(map);
    };
    //拖动设备点事件
    drawObj.device_drag = function (e) {
        if (e.target.lineId&&e.target.lineId.length) {
            e.target.lineId.forEach(function (lineId) {
                drawObj.lineUpdate(lineId, e.target.id, e.target.getLatLng());
            });
        }
    };
    drawObj.lineUpdate = function (lineId, id, latlng) {
        console.log("拖动"+lineId+"-"+id);
        map.eachLayer(function (layer) {
            if (layer.getAttribution() == "mssDevice" && layer.lineId.indexOf(lineId)>=0 && layer.id != id) {
                updateLinePoints(lineId, [layer.getLatLng(), latlng]);
            }
        })
    };
}

/**
 * 关闭捕捉的popup，保留捕捉点的id
 * @param id
 */
function closePopup(id) {
    map.closePopup();
    if (drawObj.tempDeviceId == null) {
        drawObj.tempDeviceId = id;
        return ;
    }
    var superId=0;
    var hasSuperId=false;
    var lineId = Date.now();
    var points = [];
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssDevice") {
            if (layer.id == id || layer.id == drawObj.tempDeviceId) {
                //判断属性是否存在
                if(!layer.hasOwnProperty("lineId")){
                    layer.lineId=[];
                }
                if(!layer.hasOwnProperty("hasSuperId")){

                }
                layer.lineId.push(lineId);
                points.push(layer.getLatLng());
            }
        }
    });
    drawObj.tempDeviceId = null;
    drawLine(lineId, points);
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
            marker.type = "电杆";
            break;
        case 1:
            marker.type = "电缆井";
            break;
        case 2:
            marker.type = "开关柜";
            break;
        case 3:
            marker.type = "电缆分支柜";
            break;
        case 4:
            marker.type = "配电箱";
            break;
        case 5:
            marker.type = "电缆折点";
            break;
        default:
            break;
    }
    marker.on('click', drawObj.deviceClick);
    marker.on('drag', drawObj.device_drag)
}

/**
 * 开始绘制线
 */
function startDrawLine() {
    //设置为画线模式
    drawObj.isDrawDevice = false;
    console.log("开始绘制线");
    //初始化线点击事件
    drawObj.lineClick = function (line) {
        var points = line.getLatLngs();
        var length = map.distance(points[0], points[1]);
        var obj = {
            id: line.id,
            name: line.name || "",
            type: line.type || "",
            abbreviation: line.abbreviation || "",
            length: length,
            level: line.level || "",
            model: line.model || "",
            sort: line.sort || "",
            color: line.options.color || "#3388ff",
            opacity: line.options.opacity || 1,
            width: line.options.weight || 3,
            showLabel: line.showLabel || false
        };
        window.Android.onLineClick(JSON.stringify(obj));
    };
    drawObj._lineClick = function (e) {
        drawObj.lineClick(e.target);
    }
}

/**
 * 自动画线
 * @param point
 */
function drawLine(lineId, points) {
    var line = L.polyline(points,
        {
            attribution: "mssLine",
            zIndexOffset: 500
        }
    ).addTo(map);
    line.on('click', drawObj._lineClick);
    line.id = lineId;
    drawObj.lineClick(line);
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
 * 移除线
 * @param id 线id
 */
function removeLine(id) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLine" && layer.id == id) {
            map.removeLayer(layer);
            map.eachLayer(function (device) {
              if(device.getAttribution() == "mssDevice" && device.lineId){
                 for (var i=0;i<device.lineId.length;i++){
                     if(id==device.lineId[i]){
                         device.lineId.splice(i,1);
                         break;
                     }
                 }
              }
            });
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
    drawObj={};

}

/**
 * 指定位置添加标注
 * @param lat
 * @param lng
 * @param text
 * @param lineId
 */
function addLabel(latLng, text, lineId) {
    var myIcon = L.divIcon({
        html: text,
        className: 'mssLabel',
        iconSize: 30
    });
    var label = L.marker(latLng, {
        icon: myIcon,
        attribution: "mssLabel",
        zIndexOffset: 502,
        interactive: false
    }).addTo(map);
    label.lineId = lineId;
}

/**
 * 根据线设备id移除对应的标注
 * @param {number} lineId 标注对应的线id
 */
function removeLabel(lineId) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLabel" && layer.lineId == lineId) {
            map.removeLayer(layer);
        }
    });
}

/**
 * 更新注记位置
 * @param lineId
 * @param point
 */
function updateLabel(lineId, point) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLabel" && layer.lineId == lineId) {
            layer.setLatLng(point);
        }
    });
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
 * 更新id相同的设备信息
 * @param {JSON}json
 */
function updateDeviceByID(json) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssDevice" && layer.id == json.id) {
            layer.name = json.name;
            layer.type = json.type;
            layer.height = json.height;
            layer.material = json.material;
            layer.sLine = json.sLine;
            layer.picture = json.picture;
            layer.category = json.category;
        }
    });
}

/**
 * 更新id相同的线路信息
 * @param {JSON}json json格式设备信息
 */
function updateLineByID(json) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLine" && layer.id == json.id) {
            //更新信息
            layer.name = json.name;
            layer.type = json.type;
            layer.abbreviation = json.abbreviation;
            layer.level = json.level;
            layer.model = json.model;
            layer.sort = json.sort;

            //更新样式
            layer.setStyle({
                color: json.color,
                opacity: json.opacity,
                weight: json.width
            });
            if (json.showLabel && !layer.showLabel) {
                console.log("添加注记");
                addLabel(layer.getCenter(), layer.name, layer.id);
            } else if (!json.showLabel && layer.showLabel) {
                console.log("移除注记");
                removeLabel(layer.id);
            }
            layer.showLabel = json.showLabel;
        }
    });
}

/**
 * 根据端点变化，更新线的位置
 * @param id
 * @param {Array} points
 */
function updateLinePoints(id, points) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == "mssLine" && layer.id == id) {
            layer.setLatLngs(points);
            if (layer.showLabel) {
                updateLabel(id, layer.getCenter())
            }
        }
    })
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

/**
 * 导出线路信息
 */
function exportLine() {
    var lines=[];
    map.eachLayer(function (layer) {
        if ("mssLine"== layer.getAttribution()) {
            var points = layer.getLatLngs();
            var length = map.distance(points[0], points[1]);
            var line={
                id: layer.id,
                name: layer.name || "",
                type: layer.type || "",
                abbreviation: layer.abbreviation || "",
                length: length,
                level: layer.level || "",
                model: layer.model || "",
                sort: layer.sort || "",
                color: layer.options.color || "#3388ff",
                opacity: layer.options.opacity || 1,
                width: layer.options.weight || 3,
                showLabel: layer.showLabel || false,
                points:points
            };
            lines.push(line);
        }
    });
    window.Android.exportLine(JSON.stringify(lines));
}

/**
 * 导出设备信息
 */
function exportDevice() {
    var devices=[];
    map.eachLayer(function (layer) {
        if ("mssDevice"== layer.getAttribution()) {
            var device={
                id: layer.id,
                name: layer.name || "",
                type: layer.type || "",
                height: layer.height || 0,
                material: layer.material || "",
                sLine: layer.sLine || "",
                picture: layer.picture || "",
                category: layer.category || "",
                point:layer.getLatLng()
            };
            devices.push(device);
        }
    });
    window.Android.exportDevice(JSON.stringify(devices));
}



