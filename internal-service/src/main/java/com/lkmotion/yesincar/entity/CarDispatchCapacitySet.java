package com.lkmotion.yesincar.entity;

import java.util.Date;

public class CarDispatchCapacitySet {
    private Integer id;

    private String cityCode;

    private Boolean carServicePeriod;

    private Integer spareDriverCount;

    private Integer orderInterval;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Boolean getCarServicePeriod() {
        return carServicePeriod;
    }

    public void setCarServicePeriod(Boolean carServicePeriod) {
        this.carServicePeriod = carServicePeriod;
    }

    public Integer getSpareDriverCount() {
        return spareDriverCount;
    }

    public void setSpareDriverCount(Integer spareDriverCount) {
        this.spareDriverCount = spareDriverCount;
    }

    public Integer getOrderInterval() {
        return orderInterval;
    }

    public void setOrderInterval(Integer orderInterval) {
        this.orderInterval = orderInterval;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}