package com.smartmqtt.websocket;


/**
 * @author kerry
 */
public interface IReceiveMessage {


    // 连接成功
    void onConnectSuccess();

    // 连接失败
    void onConnectFailed();

    // 关闭
    void onClose();

    void onMessage(String text);
}
