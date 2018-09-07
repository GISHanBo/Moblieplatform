package com.mobile.map.entity;

/**
 * 设备实体类
 */
public class Device {
    private long id;//全局唯一ID
    private String name;//设备名称
    private String type;//设备类别
    private String category;//设备类型
    private float height;//设备高度
    private String material;//设备材质
    private String sLine;//所属线路
    private String picture;//设备图片

    /**
     * 获取设备类型
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置设备类型
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取全局唯一id
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 设置id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取设备名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置设备名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取设备类别
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * 设置设备类别
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取设备高度
     * @return
     */
    public float getHeight() {
        return height;
    }

    /**
     * 设置设备高度
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * 获取设备材质
     * @return
     */
    public String getMaterial() {
        return material;
    }

    /**
     * 设置设备材质
     * @param material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * 获取所属线路
     * @return
     */
    public String getsLine() {
        return sLine;
    }

    /**
     * 设置所属线路
     * @param sLine
     */
    public void setsLine(String sLine) {
        this.sLine = sLine;
    }

    /**
     * 获取图片信息
     * @return
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置图片信息
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "{'id':"+id+",'name':'"+name+"','type':'"+type+"','height':"+height+",'material':'"+material
                +"','sLine':'"+sLine+"','picture':'"+picture+"','category':'"+category+"'}";
    }
}
