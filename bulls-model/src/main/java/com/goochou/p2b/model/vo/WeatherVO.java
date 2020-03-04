 package com.goochou.p2b.model.vo;

 /**
 * @author sxy
 * @date 2019/12/27
 */
public class WeatherVO {

    private String degree;
    private String weatherCode;
    private String weather;
    private String weatherPicUrl;
    private Integer aqiLevel;
    private String aqiName;
    
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getWeatherCode() {
        return weatherCode;
    }
    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getWeatherPicUrl() {
        return weatherPicUrl;
    }
    public void setWeatherPicUrl(String weatherPicUrl) {
        this.weatherPicUrl = weatherPicUrl;
    }
    public Integer getAqiLevel() {
        return aqiLevel;
    }
    public void setAqiLevel(Integer aqiLevel) {
        this.aqiLevel = aqiLevel;
    }
    public String getAqiName() {
        return aqiName;
    }
    public void setAqiName(String aqiName) {
        this.aqiName = aqiName;
    }
    
}
