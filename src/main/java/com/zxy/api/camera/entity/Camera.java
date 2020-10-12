package com.zxy.api.camera.entity;

import java.io.Serializable;

public class Camera implements Serializable {
    private static final long serialVersionUID = -398264538179127920L;

    /**
     * 摄像头IP
     */
    public String ip;

    /**
     * 摄像头通道
     */
    public String channel;

    /**
     * 传入路径
     */
    public String inputStreamUrl;

    /**
     * 传出流路径
     */
    public String ouputStreamUrl;

    /**
     * 用户名
     */
    public String username;

    /**
     * 密码
     */
    public String password;

    /**
     * 开始时间
     */
    public String startTime;

    /**
     * 结束时间
     */
    public String endTime;

    /**
     * 直播、回播类型
     */
    public int type;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInputStreamUrl() {
        return inputStreamUrl;
    }

    public void setInputStreamUrl(String inputStreamUrl) {
        this.inputStreamUrl = inputStreamUrl;
    }

    public String getOuputStreamUrl() {
        return ouputStreamUrl;
    }

    public void setOuputStreamUrl(String ouputStreamUrl) {
        this.ouputStreamUrl = ouputStreamUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
