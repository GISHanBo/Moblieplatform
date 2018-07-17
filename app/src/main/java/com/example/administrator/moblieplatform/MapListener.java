package com.example.administrator.moblieplatform;

/**
 * 地图监听接口
 */
public interface MapListener {
    /**
     * 地图加载完成周执行
     */
    void onMapLoaded();

    /**
     * 图标点击事件
     * @param lat 图标纬度
     * @param lng 图标经度
     * @param id 点的id
     */
    void onIconClick(double lat, double lng,Integer id);

    /**
     * 地图缩放等级改变事件
     * @param zoomLevel 改变后地图缩放等级
     */
    void onZoomChange(double zoomLevel);

    /**
     * 地图中心改变事件
     * @param lat 纬度
     * @param lng 经度
     */
    void onCenterChange(double lat,double lng);
}
