package com.rong.hvming;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.rong.imlib.ipc.PushMessageReceiver;

public class DemoNotificationReceiver extends PushMessageReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            if (intent.getAction().equals("io.rong.push.message")) {
                Bundle bundle = intent.getExtras();
                String str = bundle.getString("content");
                LogUtil.e("chenjian: " + str);
            } else {
                LogUtil.e("PushMessageReceiver--intent.getAction()--ERROR");
            }
        } catch (Exception e) {
            LogUtil.e("PushMessageReceiver--ERROR");
        }

    }
}
