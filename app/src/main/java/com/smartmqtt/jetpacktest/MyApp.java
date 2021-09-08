package com.smartmqtt.jetpacktest;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author: kerry
 * date: On $ {DATE}
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        if (isDebug(this)) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

}
