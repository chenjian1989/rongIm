package com.rong.hvming;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogUtil {

    private final static String LOG_TAG = "wol";

    private static int SDCARD_LOG_FILE_SAVE_DAYS = 30;// sd卡中日志文件的最多保存天数

    private final static int FILE_SIZE = 1024 * 1024 * 10;// 单个日志文件大小：10M


    /**
     * LOG打印级别(LOG_LEVEL为1时，打印所有日志,为5时，仅打印error级别的日志，以此类推)
     */
    private final static int LOG_LEVEL = 2;

    private final static int LOG_LEVEL_V = 1;// verbose

    private final static int LOG_LEVEL_D = 2;// debug

    private final static int LOG_LEVEL_I = 3;// info

    private final static int LOG_LEVEL_W = 4;// warn

    private final static int LOG_LEVEL_E = 5;// error

    private final static int LOG_LEVEL_STAT = 6;// stat

    /**
     * LOG文件级别(LOG_LEVEL为1时，输出所有级别的日志到文件中,为5时，仅输出error级别的日志到文件中，以此类推)
     */
    private final static int FILE_LEVEL = 2;

    private final static int FILE_LEVEL_V = 1;

    private final static int FILE_LEVEL_D = 2;

    private final static int FILE_LEVEL_I = 3;

    private final static int FILE_LEVEL_W = 4;

    private final static int FILE_LEVEL_E = 5;

    private final static int FILE_LEVEL_STAT = 6;


    /**
     * LOG基本路径
     */
    public final static String LOG_PATH = SDUtil.getFilePathLog();

    public static Map<String, String> logFileName;

    static {
        logFileName = new HashMap<String, String>();
        logFileName.put(FILE_LEVEL_V + "", "_verbose.txt");
        logFileName.put(FILE_LEVEL_D + "", "_debug.txt");
        logFileName.put(FILE_LEVEL_I + "", "_info.txt");
        logFileName.put(FILE_LEVEL_W + "", "_warn.txt");
        logFileName.put(FILE_LEVEL_E + "", "_error.txt");
        logFileName.put(FILE_LEVEL_STAT + "", "_stat.txt");
    }

    /**
     * print v log
     */
    public static void v(String msg) {
        v(LOG_TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_V) {
            Log.v(tag, msg);
        }

        if (FILE_LEVEL <= FILE_LEVEL_V) {
            logToFile(tag, msg, FILE_LEVEL_V);
        }
    }

    public static void v(String msg, Throwable tr) {
        v(LOG_TAG, msg, tr);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_V) {
            Log.v(tag, msg, tr);
        }

        if (FILE_LEVEL <= FILE_LEVEL_V) {
            logToFile(tag, msg, tr, FILE_LEVEL_V);
        }

    }

    /**
     * print d log
     */
    public static void d(String msg) {
        d(LOG_TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_D) {
            Log.d(tag, msg);
        }

        if (FILE_LEVEL <= FILE_LEVEL_D) {
            logToFile(tag, msg, FILE_LEVEL_D);
        }
    }

    public static void d(String msg, Throwable tr) {
        d(LOG_TAG, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_D) {
            Log.d(tag, msg, tr);
        }

        if (FILE_LEVEL <= FILE_LEVEL_D) {
            logToFile(tag, msg, tr, FILE_LEVEL_D);
        }
    }

    /**
     * print i log
     */
    public static void i(String msg) {
        i(LOG_TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_I) {
            Log.i(tag, msg);
        }

        if (FILE_LEVEL <= FILE_LEVEL_I) {
            logToFile(tag, msg, FILE_LEVEL_I);
        }
    }

    public static void i(String msg, Throwable tr) {
        i(LOG_TAG, msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_I) {
            Log.i(tag, msg, tr);
        }

        if (FILE_LEVEL <= FILE_LEVEL_I) {
            logToFile(tag, msg, tr, FILE_LEVEL_I);
        }
    }

    /**
     * print w log
     */
    public static void w(String msg) {
        w(LOG_TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_W) {
            Log.w(tag, msg);
        }

        if (FILE_LEVEL <= FILE_LEVEL_W) {
            logToFile(tag, msg, FILE_LEVEL_W);
        }
    }

    public static void w(String msg, Throwable tr) {
        w(LOG_TAG, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_W) {
            Log.w(tag, msg, tr);
        }

        if (FILE_LEVEL <= FILE_LEVEL_W) {
            logToFile(tag, msg, tr, FILE_LEVEL_W);
        }
    }

    /**
     * print e log
     */
    public static void e(String msg) {
        if (msg != null) {
            msg = msg.trim();
            int index = 0;
            int maxLength = 3500;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, index + maxLength);
                }
                index += maxLength;
                e(LOG_TAG, sub.trim());
            }
        }
    }

    public static void e(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_E) {
            Log.e(tag, msg);
        }
        if (!LOG_TAG.equals(tag)) {
            return;
        }
        if (FILE_LEVEL <= FILE_LEVEL_E) {
            logToFile(tag, msg, FILE_LEVEL_E);
        }
    }

    public static void e(String msg, Throwable tr) {
        e(LOG_TAG, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (msg == null) {
            msg = "null";
        }

        if (LOG_LEVEL <= LOG_LEVEL_E) {
            Log.e(tag, msg, tr);
        }

        if (FILE_LEVEL <= FILE_LEVEL_E) {
            logToFile(tag, msg, tr, FILE_LEVEL_E);
        }
    }


    private static void logToFile(String tag, String msg, int level) {
        String filePath = LOG_PATH + DateUtil.format(new Date(), DateUtil.SOURCEFORMAT6)
                + logFileName.get(level + "");
        String data = DateUtil.format(new Date(), DateUtil.SOURCEFORMAT7) + "-> " + tag + ": "
                + msg;
        writeLineToFile(filePath, data);
    }

    private static void logToFile(String tag, String msg, Throwable tr, int level) {
        String filePath = LOG_PATH + DateUtil.format(new Date(), DateUtil.SOURCEFORMAT6)
                + logFileName.get(level + "");
        String data = DateUtil.format(new Date(), DateUtil.SOURCEFORMAT7) + "-> " + tag + ": "
                + msg + " stackTrace: " + parseException(tr);
        writeLineToFile(filePath, data);
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private synchronized static void writeLineToFile(String filePath, String data) {
        OutputStream os = null;
        try {
            File dir = new File(LogUtil.LOG_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(filePath);
            if (!file.exists()) {
                // 如果文件不存在,说明今天还没记录过日志. 并且删除过期日志
                delFile();

                file.createNewFile();
            }

            boolean append = true;
            if (file.exists() && file.length() > FILE_SIZE) {
                append = false;
            }

            os = new FileOutputStream(filePath, append);
            os.write((data + "\n").getBytes("utf-8"));
            os.flush();
        } catch (Exception e) {
            // LogUtil.w("writeToFile error:" + parseException(e));
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                    // ignore exception
                }
            }
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        Date d = getDateBefore();
        File file = new File(LogUtil.LOG_PATH);
        if (file.exists()) {
            File[] logs = file.listFiles();
            for (int i = 0; i < logs.length; i++) {
                String date = logs[i].getName().split("_")[0];
                try {
                    if (DateUtil.parse(date, DateUtil.SOURCEFORMAT6).before(d)) {
                        logs[i].delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS + 1);
        return now.getTime();
    }


    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLineToRemoteFile(String filePath, String data, boolean append) {

        OutputStream os = null;

        try {

            File dir = new File(LogUtil.LOG_PATH);

            if (!dir.exists()) {

                dir.mkdirs();
            }

            File file = new File(filePath);

            if (!file.exists()) {

                delFile();
            }

            if (file.exists() && file.length() > FILE_SIZE) {

                append = false;
            }

            os = new FileOutputStream(filePath, append);

            os.write((data + "\n").getBytes("utf-8"));

            os.flush();

        } catch (Exception e) {
        } finally {

            if (null != os) {

                try {

                    os.close();

                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 将异常信息转化成字符串
     *
     * @param t
     * @return
     * @throws IOException
     */
    private static String parseException(Throwable t) {
        try {
            if (t == null)
                return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                t.printStackTrace(new PrintStream(baos));
            } finally {
                baos.close();
            }
            return baos.toString();
        } catch (Exception e) {
            return "parseException error";
        }
    }

}

