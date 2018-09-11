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
    private byte width;
    /**
     * 线路颜色
     */
    private String color;
    /**
     * 线的透明度
     */
    private float opacity;

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
    public byte getWidth() {
        return width;
    }

    /**
     * 设置线宽
     * @param width 线宽单位像素
     */
    public void setWidth(byte width) {
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

    /**
     * 获取注记状态
     * @return 标注显示状态该
     */
    public boolean isShowLabel() {
        return showLabel;
    }

    /**
     * 获取透明度
     * @return 0-1的透明度
     */
    public float getOpacity() {
        return opacity;
    }

    /**
     * 设置透明度
     * @param opacity 0-1的透明度
     */
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
}
