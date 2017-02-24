
package com.rong.hvming;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import io.rong.imkit.RongIM;

public class App extends Application {
    private final String mFirToken = "f763f154b6657147d94c698425128984";

    private Handler mHandler = new Handler();

    private Toast mToast;

    /**
     * App单例对象
     */
    private static App mInstance;

    /**
     * 获取App单例
     * <p/>
     *
     * @return App
     */
    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        // 初始化MyApplication实例
        mInstance = this;
        FIR.init(this);
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

    public void CheckVersion(final Context context) {
        FIR.checkForUpdateInFIR(mFirToken, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.e("fir", "check from fir.im success! " + "\n" + versionJson);
                if (!TextUtils.isEmpty(versionJson)) {
                    VersionEntity versionEntity = new Gson().fromJson(versionJson, VersionEntity.class);
                    if (versionEntity.getBuild() > getVersion()
                            && !TextUtils.isEmpty(versionEntity.getDirect_install_url())) {
                        // 有新版
                        String[] tips = {
                                getResources().getString(R.string.tip_new_version_forceup),
                                getResources().getString(
                                        R.string.tip_more_version_hasNewer_forceup)
                                , versionEntity.getChangelog(),
                                getResources().getString(R.string.update_now),
                                getResources().getString(R.string.update_later)
                        };
                        UpdateDialog dialog = new UpdateDialog(context, tips
                                , versionEntity.getDirect_install_url());
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFail(Exception exception) {
                Log.e("fir", "check fir.im fail! " + "\n" + exception.getMessage());
            }

            @Override
            public void onStart() {
                //Toast.makeText(getApplicationContext(), "正在获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                //Toast.makeText(getApplicationContext(), "获取完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取当前应用的版本号
     *
     * @return
     */
    public int getVersion() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getApplicationContext().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查网络是否好用.
     *
     * @return true or false
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            // 如果仅仅是用来判断网络连接　　　　　　
            // 则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo workinfo : info) {
                    if (workinfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 返回是否存在sd卡.
     *
     * @return
     */
    public boolean existSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else
            return false;
    }

    /**
     * 显示提示信息，居中显示
     */
    public void toastMiddle(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(mInstance, msg, Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
                Looper.loop();
            }
        });
    }

    /**
     * 安装apk文件
     */
    public Intent getInstallApkIntent(String filepath, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(filepath);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.rong.hvming.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }
}
