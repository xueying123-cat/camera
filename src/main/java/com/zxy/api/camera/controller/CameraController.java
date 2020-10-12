package com.zxy.api.camera.controller;

import com.zxy.api.common.CameraException;
import com.zxy.api.camera.manager.FFmpegManager;
import com.zxy.api.camera.config.CameraConfig;
import com.zxy.api.camera.manager.FFmpegManagerImpl;
import com.zxy.api.common.Result;
import com.zxy.api.camera.entity.Camera;
import com.zxy.api.common.SystemConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class CameraController {
    private static final Logger logger = LoggerFactory.getLogger(CameraController.class);

    public static Map<String, String> maps = new HashMap<>();
    public static FFmpegManager manager = new FFmpegManagerImpl();

    @Autowired
    public CameraConfig cameraConfig;


    // 启动视频转码
    @PostMapping(value = "/rtsp-to-rtmp")
    public Result convertKedaRtspToRtmp(@RequestBody Camera camera) throws CameraException {
        checkValueIsEmpty(camera);

        manager.stopAll();
        String rtsp = cameraConfig.getRtspUrl(camera);
        String rtmp = cameraConfig.getRtmpUrl();
        String command = getConvertCommand(rtsp, rtmp, camera.getType());
        String id = manager.start("tomcat", command);
        maps.put(camera.getChannel() + camera.getIp(), id);
        Result result = new Result();
        result.put("url", rtmp);
        return result;
    }

    private void checkValueIsEmpty(Camera camera) throws CameraException {
        if (Strings.isEmpty(camera.getUsername())) {
            throw new CameraException("请传入视频用户名");
        }
        if (Strings.isEmpty(camera.getPassword())) {
            throw new CameraException("请传入视频密码");
        }
        if (Strings.isEmpty(camera.getIp())) {
            throw new CameraException("请传入视频IP");
        }
        if (Strings.isEmpty(camera.getChannel())) {
            throw new CameraException("请传入视频通道");
        }
    }

    /*
    /**
     * 获取转化命令
     *
     * @return
     * @throws Exception
     */
    private String getConvertCommand(String rtspUrl, String rtmp, int type) throws CameraException {
        String command = null;
        if (SystemConstants.STREAM_TYPE_LIVE == type) {
            //直播
            command = manager.liveCommand(rtspUrl, rtmp);
        } else if (SystemConstants.STREAM_TYPE_REBROADCAST == type) {
            //历史回放
            command = manager.rebroadcastCommand(rtspUrl, rtmp);
        } else {
            throw new CameraException("未找到对应的播放类型");
        }
        if (null == command) {
            throw new CameraException("命令为空，执行异常");
        }
        return command;
    }

    // 关闭转流
    @PutMapping(value = "/{ip}/{channel}/close")
    public void close(@PathVariable(value = "ip") String ip, @PathVariable(value = "channel") String channel) {
        try {
            manager.stop(maps.get(channel + ip));
            System.out.println(channel + ip + "流已关闭");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
