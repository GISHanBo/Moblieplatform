package com.Map.entity;

/**
 * 线路实体类
 */
public class Line {
    private int id;//全局唯一ID
    private String name;//线路名称
    private String type;//线路类型
    private String state;//线路状态
    private float length;//线路长度
    private String size;//线路规格
    private String manufacturer;//线路厂家

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
    public int getId() {
        return id;
    }

    /**
     * 设置线路地图ID
     * @param id
     */
    public void setId(int id) {
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
     * 获取线路类型
     * @return 线路类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置线路类型
     * @param type 线路类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取线路状态
     * @return 线路状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置线路状态
     * @param state 线路状态
     */
    public void setState(String state) {
        this.state = state;
    }



    /**
     * 获取线路规格
     * @return 线路规格
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置线路规格
     * @param size 线路规格
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 获取线路厂家
     * @return 线路厂家
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置线路厂家
     * @param manufacturer 线路厂家
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
