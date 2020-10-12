package com.zxy.api.camera.config;


import com.zxy.api.camera.entity.Camera;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kedaconfig")
public class KedaConfig extends CameraConfig {
    /**
     * 服务器ip
     */
    public String ip;
    /**
     * 科达访问接口
     */
    public String path;

    /**
     * 用户名
     */
    public String username;

    /**
     * 密码
     */
    public String password;

    /**
     * 本地ip
     */
    public String localip;

    /**
     * rtsp格式
     */
    public String rtsp;

    /**
     * rtmp格式
     */
    public String rtmp;

    /**
     * 推送端口
     */
    private String push_port;

    /**
     * 推送地址
     */
    private String push_host;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getLocalip() {
        return localip;
    }

    public void setLocalip(String localip) {
        this.localip = localip;
    }

    @Override
    public String getPush_port() {
        return push_port;
    }

    @Override
    public void setPush_port(String push_port) {
        this.push_port = push_port;
    }

    @Override
    public String getPush_host() {
        return push_host;
    }

    @Override
    public void setPush_host(String push_host) {
        this.push_host = push_host;
    }

    public String getRtsp() {
        return rtsp;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtsp(String rtsp) {
        this.rtsp = rtsp;
    }

    @Override
    public String getRtspUrl(Camera camera) {
        camera.setInputStreamUrl(rtsp);
        return super.getRtspUrl(camera);
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getRtmpUrl() {
        return rtmp.replace("pushhost", push_host).replace("pushport", push_port);
    }

    public String getPathUrl() {
        return path.replace("ip", ip);
    }
}
