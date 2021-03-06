package com.lyw.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.Gravity;
import android.widget.Toast;

import com.lyw.app.cache.DataCleanManager;
import com.lyw.app.ui.api.MyApi;
import com.lyw.app.utils.MethodsCompat;
import com.lyw.app.ui.widget.SimplexToast;

import java.util.Properties;


/**
 * @author lyw
 * 全局应用程序类
 * 用于保存和调用全局应用配置及访问网络数据
 * 全局application初始化
 *
 */
public class GlobalApplication extends Application {
    //sp设立的文件名字-基础application
    private static final String PREF_NAME = "creativelocker.pref";
    // 默认分页大小
    public static final int PAGE_SIZE = 20;
    //全局单例
    private static GlobalApplication instance;
    //上下文对象
    private static Context _context;
    private static Handler handler;
    private static  int mainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        _context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();

        // 初始化
        init();
    }



    //全局单例
    public static GlobalApplication getInstance(){
        return instance;
    }
    //当前运行的全局上下文
    public static Context getContext(){
        return _context;
    }
    //主线程handler
    public static Handler getHandler(){
        return handler;
    }
    //主线程id
    public static int getMainThreadId(){
        return mainThreadId;
    }


    //应用配置文件和sp文件不同

    //获取该全局对应的app_config目录下的config
    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }
    //设置键值对
    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }
    //获取键对应值
    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }
    //移除对应键
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }

        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    //是否加载图片的标注方法
    /*public static void setLoadImage(boolean flag) {
        set(KEY_LOAD_IMAGE, flag);
    }*/


    //重新初始化操作
    public static void reInit() {
        ((GlobalApplication) GlobalApplication.getInstance()).init();
    }
    private void init() {
        // 初始化异常捕获类
        AppCrashHandler.getInstance().init(this);
        // 初始化账户基础信息
       // AccountHelper.init(this);
        // 初始化网络请求
      //  OkHttpClient.init(this);
        //初始化其他相关，框架，sdk，数据库,百度地图....
        MyApi.init(this);
    }



    //关于sp的静态操作方法
    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }
    //获取文件名为xx的sp文件
    public static SharedPreferences getPreferences() {
        return getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(getContext().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(getContext().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon, int gravity) {
        Context context = _context;
        if (context != null)
            SimplexToast.show(context, message, gravity, duration);
    }


}

