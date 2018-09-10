package com.mobile.map.entity;

/**
 * 线路实体类
 */
public class Line {
    private long id;//全局唯一ID
    private String name;//线路名称
    private String type;//线路类别
    private String abbreviation;//类别简称
    private float length;//线段长度
    private String level;//电压等级
    private String model;//导线型号
    private String sort;//导线分类
    private LineStyle lineStyle;//线样式

    /**
     * 获取线样式
     * @return 线样式
     */
    public LineStyle getLineStyle() {
        return lineStyle;
    }

    /**
     * 设置线样式
     * @param lineStyle 线样式
     */
    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    /**
     * 获取类别简称
     * @return
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * 设置类别简称
     * @param abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * 获取电压等级
     * @return
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置电压等级
     * @param level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取导线型号
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置导线型号
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取导线分类
     * @return
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置导线分类
     * @param sort
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 获取线路长度
     * @return 线路长度
     */
    public float getLength() {
        return length;
    }

    /**
     * 设置线路长度
     * @param length 线路长度
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * 获取ID
     * @return 线路地图ID
     */
    public long getId() {
        return id;
    }

    /**
     * 设置线路地图ID
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取线路名称
     * @return 线路名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置线路名称
     * @param name 线路名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取线路类别
     * @return 线路类别
     */
    public String getType() {
        return type;
    }

    /**
     * 设置线路类别
     * @param type 线路类别
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{'id':"+id+",'name':'"+name+"','type':'"+type+"','abbreviation':'"+abbreviation+"','length':"+length
                +",'level':'"+level+"','model':'"+model+"','sort':'"+sort
                +"','color':'"+lineStyle.getColor()+"','opacity':"+lineStyle.getOpacity()
                +",'width':"+lineStyle.getWidth()+",'showLabel':"+lineStyle.getShowLabel()+"}";
    }
}
