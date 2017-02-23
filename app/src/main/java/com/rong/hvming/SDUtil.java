package com.rong.hvming;


import android.os.Environment;

import java.io.File;

public class SDUtil {
    /** i8xiaoshi存储文件的根目录 */
    private static String filePath = null;

    /** i8xiaoshi临时文件目录 */
    private static String filePathTemp = getFilePath() + "temp"+ File.separator;

    /** i8xiaoshi日志文件目录 */
    private static String filePathLog = getFilePath() + "log"+File.separator;

    /** i8xiaoshi缓存文件目录 */
    private static String filePathCache = getFilePath() + "cache"+File.separator;

    /** i8xiaoshi资源文件目录 */
    private static String filePathAssert = getFilePath() + "assets"+File.separator;

    /**
     * 返回是否存在sd卡.
     *
     * @return
     */
    public static boolean existSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else
            return false;
    }

    /**
     * 返回手机存储目录的位置
     * <p>
     * 如果有sd卡，返回sd卡的根目录，否则返回手机的缓存目录
     *
     * @return String
     */
    public static String getFilePath() {
        if (filePath != null) {
            return filePath;
        } else {
            if (existSDCard()) {
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + App.getInstance().getPackageName() + File.separator;
                File file = new File(filePath);
                if(!file.exists()){
                    file.mkdirs();
                }
                return filePath;
            } else {
                filePath = App.getInstance().getCacheDir().getAbsolutePath() + File.separator
                        + App.getInstance().getPackageName() + File.separator;
                File file = new File(filePath);
                if(!file.exists()){
                    file.mkdirs();
                }
                return filePath;
            }
        }
    }

    /**
     * 返回日志文件的路径
     * <p>
     *
     * @return String
     */
    public static String getFilePathLog() {
        File file = new File(filePathLog);
        if(!file.exists()){
            file.mkdirs();
        }
        return filePathLog;
    }
}
