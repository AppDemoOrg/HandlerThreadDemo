package com.abt.handler;

import android.os.HandlerThread;

/**
 * @描述： @SleepThread
 * @作者： @黄卫旗
 * @创建时间： @15/05/2018
 */
public class SleepThread extends HandlerThread {


    public SleepThread(String name) {
        super(name);
    }

    public SleepThread(String name, int priority) {
        super(name, priority);
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
