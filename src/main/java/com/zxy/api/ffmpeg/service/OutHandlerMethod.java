package com.zxy.api.ffmpeg.service;
/**
 * 输出消息处理
 */
public interface OutHandlerMethod {
	/**
	 * 解析消息
	 * @param msg
	 * @param msg 
	 */
	public void parse(String type, String msg);
}
