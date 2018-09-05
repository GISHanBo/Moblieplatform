package com.mobile.map;

/**
 * 自定义批量撒点接口
 */
public interface CustomMultiPointL {
    /**
     * 自定义撒点范围变化事件
     * @param latMin 纬度最小值
     * @param lngMin 经度最小值
     * @param latMax 纬度最小值
     * @param lngMax 经度最大值
     */
     void onBoundsChange(MapView mapView,double latMin, double lngMin, double latMax, double lngMax);
}
