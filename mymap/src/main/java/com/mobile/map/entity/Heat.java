package com.mobile.map.entity;

/**
 * 热力图数据实体类
 */
public class Heat {
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;
    /**
     * 权重值，0-0.3为蓝色，0.3-0.6为黄色，0.6-1为红色
     */
    private float value;

    /**
     * 热力图数据实体类
     * @param lat 纬度
     * @param lng 经度
     * @param value 权重值，0-0.3为蓝色，0.3-0.6为黄色，0.6-1为红色
     */
    public Heat(double lat, double lng, float value) {
        this.lat = lat;
        this.lng = lng;
        this.value = value;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
