package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Response;

import java.io.Serializable;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:22
 * @Description:
 */
public class DeviceGpsResponse implements Serializable {
    private String id;
    private String device_id;
    private String uuid;
    private String firmware_version;
    private String updated_at;
    private String timestamp;
    private String mark;
    private String sms;
    private String owner;
    private String longitude;
    private String latitude;
    private String relative_altitude;
    private String ground_altitude;
    private String altitude;
    private String temperature;
    private String humidity;
    private String light;
    private String pressure;
    private String used_star;
    private String view_star;
    private String dimension;
    private String speed;
    private String horizontal;
    private String vertical;
    private String course;
    private String battery_voltage;
    private String signal_strength;
    private String point_location;
    private String fix_time;
    private String hdop;
    private String vdop;
    private String pdop;
    private String quality;
    private String[] coordinates;
    
   
    public String[] getCoordinates() {
    	 String[] coordinatess =  {longitude,latitude};
    	return coordinatess;
	}


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRelative_altitude() {
        return relative_altitude;
    }

    public void setRelative_altitude(String relative_altitude) {
        this.relative_altitude = relative_altitude;
    }

    public String getGround_altitude() {
        return ground_altitude;
    }

    public void setGround_altitude(String ground_altitude) {
        this.ground_altitude = ground_altitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getUsed_star() {
        return used_star;
    }

    public void setUsed_star(String used_star) {
        this.used_star = used_star;
    }

    public String getView_star() {
        return view_star;
    }

    public void setView_star(String view_star) {
        this.view_star = view_star;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBattery_voltage() {
        return battery_voltage;
    }

    public void setBattery_voltage(String battery_voltage) {
        this.battery_voltage = battery_voltage;
    }

    public String getSignal_strength() {
        return signal_strength;
    }

    public void setSignal_strength(String signal_strength) {
        this.signal_strength = signal_strength;
    }

    public String getPoint_location() {
        return point_location;
    }

    public void setPoint_location(String point_location) {
        this.point_location = point_location;
    }

    public String getFix_time() {
        return fix_time;
    }

    public void setFix_time(String fix_time) {
        this.fix_time = fix_time;
    }

    public String getHdop() {
        return hdop;
    }

    public void setHdop(String hdop) {
        this.hdop = hdop;
    }

    public String getVdop() {
        return vdop;
    }

    public void setVdop(String vdop) {
        this.vdop = vdop;
    }

    public String getPdop() {
        return pdop;
    }

    public void setPdop(String pdop) {
        this.pdop = pdop;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
