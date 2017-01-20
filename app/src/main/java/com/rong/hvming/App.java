
package com.rong.hvming;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import io.rong.imkit.RongIM;

public class App extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))
                || "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            RongIM.init(this);

            /**
             * 设置接收消息的监听器。
             */
            RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

            /**
             * 设置用户信息提供者。
             */
            RongIM.setUserInfoProvider(new MyUserInfoProvider(), true);

        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
