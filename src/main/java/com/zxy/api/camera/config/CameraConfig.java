package com.zxy.api.camera.config;

import com.zxy.api.camera.entity.Camera;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config")
public class CameraConfig {
    /**
     * 保活时长（分钟）
     */
    private String keep_alive;

    /**
     * 推送地址
     */
    private String push_host;

    /**
     * 额外地址
     */
    private String host_extra;

    /**
     * 推送端口
     */
    private String push_port;

    /**
     * 主码流最大码率
     */
    private String main_code;

    /**
     * 主码流最大码率
     */
    private String sub_code;

    /**
     * rtsp流
     */
    private String rtsp;

    /**
     * rtmp流
     */
    private String rtmp;

    public String getHost_extra() {
        return host_extra;
    }

    public void setHost_extra(String host_extra) {
        this.host_extra = host_extra;
    }

    public String getKeep_alive() {
        return keep_alive;
    }

    public void setKeep_alive(String keep_alive) {
        this.keep_alive = keep_alive;
    }

    public String getPush_host() {
        return push_host;
    }

    public void setPush_host(String push_host) {
        this.push_host = push_host;
    }

    public String getPush_port() {
        return push_port;
    }

    public void setPush_port(String push_port) {
        this.push_port = push_port;
    }

    public String getMain_code() {
        return main_code;
    }

    public void setMain_code(String main_code) {
        this.main_code = main_code;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    @Override
    public String toString() {
        return "Config [keep_alive=" + keep_alive + ", push_host=" + push_host + ", push_port=" + push_port + "]";
    }

    public void setRtsp(String rtsp) {
        this.rtsp = rtsp;
    }

    public String getRtspUrl(Camera camera) {
        return camera.getInputStreamUrl().replace("username", camera.getUsername())
                .replace("password", camera.getPassword())
                .replace("ip", camera.getIp())
                .replace("channel", camera.getChannel());
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getRtmpUrl() {
        return rtmp.replace("pushhost", getPush_host()).replace("pushport", getPush_port());
    }
}
