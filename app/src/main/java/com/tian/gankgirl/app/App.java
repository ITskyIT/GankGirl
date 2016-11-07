package com.tian.gankgirl.app;

import android.app.Activity;
import android.app.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * 程序入口
 *
 * 作者：田
 * 日期：2016/11/5 10:15
 * 邮箱：18236110483@163.com
 */
public class App extends Application{
    private static App instance;
    //存放Activity的集合
    Set<Activity> activityList;
    //单例模式
    private App (){}


    public static App getInstance() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 退出程序
     * @author 田
     * @time 2016/11/5 11:18
     */
    public void exitApp(){
        if (activityList != null) {
            synchronized (activityList) {
                for (Activity activity : activityList) {
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new HashSet<Activity>();
        }
        activityList.add(activity);
    }

    /**
     * 移除指定的activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList != null) {
            activityList.remove(activity);
        }
    }
}
