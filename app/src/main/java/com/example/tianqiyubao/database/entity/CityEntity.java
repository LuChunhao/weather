package com.example.tianqiyubao.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CityEntity {
    @Id(autoincrement = true)
    private Long id;
    private String cityName;
    private String weatherId;
    private String userName;
    @Generated(hash = 1879372623)
    public CityEntity(Long id, String cityName, String weatherId, String userName) {
        this.id = id;
        this.cityName = cityName;
        this.weatherId = weatherId;
        this.userName = userName;
    }
    @Generated(hash = 2001321047)
    public CityEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getWeatherId() {
        return this.weatherId;
    }
    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    


}
