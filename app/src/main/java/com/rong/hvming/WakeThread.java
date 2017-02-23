package com.rong.hvming;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class WakeThread extends Thread {
    String ip = null;
    String macAddr = null;
    int port;
    String mid;

    public WakeThread(String ip, String macAddr, int port, String id) {
        this.ip = ip;
        this.macAddr = macAddr;
        this.port = port;
        this.mid = id;
    }

    @Override
    public void run() {
        super.run();
        wakeOnLan(ip, macAddr, port);
    }

    public void wakeOnLan(String ip, String macAddr, int port) {
        DatagramSocket datagramSocket = null;
        try {
            byte[] mac = getMacBytes(macAddr);
            byte[] magic = new byte[6 + 16 * mac.length];
            //1.写入6个FF
            for (int i = 0; i < 6; i++) {
                magic[i] = (byte) 0xff;
            }
            //2.写入16次mac地址
            for (int i = 6; i < magic.length; i += mac.length) {
                System.arraycopy(mac, 0, magic, i, mac.length);
            }
            datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(magic, magic.length, InetAddress.getByName(ip), port);
            datagramSocket.send(datagramPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LogUtil.e("开机广播发送成功!");
            TextMessage textMessage = TextMessage.obtain("广播发送成功");
            Message message = Message.obtain(mid, Conversation.ConversationType.PRIVATE, textMessage);
            RongIMClient.getInstance().sendMessage(message, "广播发送成功", "广播发送成功"
                    , new RongIMClient.SendImageMessageCallback() {
                @Override
                public void onAttached(Message message) {
                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                }

                @Override
                public void onSuccess(Message message) {
                }

                @Override
                public void onProgress(Message message, int i) {
                }
            });

            if (datagramSocket != null)
                datagramSocket.close();
        }
    }

    private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
