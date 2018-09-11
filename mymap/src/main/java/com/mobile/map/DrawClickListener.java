package com.mobile.map;

import com.mobile.map.entity.Device;
import com.mobile.map.entity.Line;

public interface DrawClickListener {

    /**
     * 获取点击设备信息，会在设备点击后触发
     * @param device 设备信息
     */
    void deviceClick(Device device);

    /**
     * 线路点击事件，点击线或第一次创建线后触发
     * @param line 导出的线信息
     */
    void lineClick(Line line);

    /**
     * 接收导出设备信息
     * @param json json格式设备信息
     */
    void exportDevice(String json);

    /**
     * 接收导出线路信息
     * @param json json格式线路信息
     */
    void exportLine(String json);
}
