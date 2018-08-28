package com.Map.entity;

/**
 * 带图标点类型实体类
 */
public class Marker {
    private double lat;
    private double lng;
    private String icon;
    private Integer serial;

    /**
     * 构造器
     * @param lat 纬度
     * @param lng 经度
     */
    public Marker(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * 构造器
     * @param lat 纬度
     * @param lng 经度
     * @param icon 图标名称
     * @param serial 编号
     */
    public Marker(double lat, double lng, String icon, Integer serial) {
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.serial = serial;
    }

    /**
     * 获取纬度
     * @return 纬度
     */
    public double getLat() {
        return lat;
    }

    /**
     * 设置纬度
     * @param lat 纬度
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
    /**
     * 获取经度
     * @return 经度
     */
    public double getLng() {
        return lng;
    }
    /**
     * 设置经度
     * @param lng 经度
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * 获取图标
     * @return 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取编号
     * @return 编号
     */
    public Integer getSerial() {
        return serial;
    }

    /**
     * 设置编号
     * @param serial 编号
     */
    public void setSerial(Integer serial) {
        this.serial = serial;
    }
}
