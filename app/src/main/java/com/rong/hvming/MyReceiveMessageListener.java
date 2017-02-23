
package com.rong.hvming;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;


public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

    /**
     * 收到消息的处理。
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(Message message, int left) {
        // 开发者根据自己需求自行处理
        if (message.getContent() instanceof TextMessage) {// 文本消息
            TextMessage textMessage = (TextMessage) message.getContent();
            Log.e("文本消息", "Receive-TextMessage:" + textMessage.getContent());
            if (!TextUtils.isEmpty(textMessage.getContent()) && textMessage.getContent().contains("开机")) {
                String[] str = textMessage.getContent().split(",");
                if (str.length == 4 && validationIp(str[1]) && validationMac(str[2])) {
                    try {
                        int lport = Integer.parseInt(str[3]);
                        if (lport > 0) {
                            Log.e("文本消息", "开机格式正确!");
                            new WakeThread(str[1], str[2], lport, message.getSenderUserId()).start();
                            return false;
                        }
                    } catch (Exception e) {
                    }
                }
                // 默认 ip 以及 mac地址
                new WakeThread("192.168.1.104", "38-D5-47-AA-CF-91", 7878, message.getSenderUserId()).start();
            }
        }
        return false;
    }

    private boolean validationIp(String ip) {
        /*正则表达式*/
        String gs_ip = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|[1-9])\\."
                + "(25[0-5]|2[0-4]\\d|1\\d{1,2}|\\d{2}|\\d)\\."
                + "(25[0-5]|2[0-4]\\d|1\\d{1,2}|\\d{2}|\\d)\\."
                + "(25[0-5]|2[0-4]\\d|1\\d{1,2}|\\d{2}|\\d)";//限定输入格式

        Pattern p = Pattern.compile(gs_ip);
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    private boolean validationMac(String macStr) {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            return false;
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
