package com.mobile.map.entity;

/**
 * 设备实体类
 */
public class Device {
    private int id;//全局唯一ID
    private String name;//设备名称
    private String type;//设备类型
    private String state;//设备状态
    private String category;//设备类别
    private String size;//设备规格
    private String manufacturer;//生产厂家

    /**
     * 获取ID
     * @return 设备地图ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置设备地图ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取设备名称
     * @return 设备名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置设备名称
     * @param name 设备名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取设备类型
     * @return 设备类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置设备类型
     * @param type 设备类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取设备状态
     * @return 设备状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置设备状态
     * @param state 设备状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取设备类别
     * @return 设备类别
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置设备类别
     * @param category 设备类别
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取设备规格
     * @return 设备规格
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置设备规格
     * @param size 设备规格
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 获取生产厂家
     * @return 生产厂家
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置生产厂家
     * @param manufacturer 生产厂家
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
