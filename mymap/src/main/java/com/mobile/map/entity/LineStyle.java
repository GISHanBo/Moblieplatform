package com.mobile.map.entity;

import android.graphics.Color;

/**
 * 线样式类
 */
public class LineStyle {
    /**
     * 是否展示注记信息
     */
    private boolean showLabel;
    /**
     * 线宽，单位为像素
     */
    private Integer width;
    /**
     * 线路颜色
     */
    private String color;

    /**
     * 获取是否展示注记
     * @return true为显示，false不显示
     */
    public boolean getShowLabel() {
        return showLabel;
    }

    /**
     * 设置是否展示注记
     * @param showLabel true为显示，false不显示
     */
    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    /**
     * 获取线宽
     * @return 线宽单位像素
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 设置线宽
     * @param width 线宽单位像素
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 获取线颜色
     * @return 颜色
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置线的颜色
     * @param color #开头16进制颜色
     */
    public void setColor(String color) {
        this.color = color;
    }
}
