package com.abt.handler.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.abt.handler.R;
import com.abt.handler.SleepThread;
import com.orhanobut.logger.Logger;

public class ThreadActivity extends AppCompatActivity {

    private TextView tvMain;
    private HandlerThread mHandlerThread;
    private Handler mThreadHandler; // 子线程中的handler
    // private Handler mMainHandler; // = new Handler(); // UI线程中的handler
    private boolean isUpdateInfo; // 以防退出界面后Handler还在执行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMain = findViewById(R.id.tv_main);
        initThread();
        Logger.d("onCreate");
    }

    private void initThread() {
        mHandlerThread = new SleepThread("SleepThread");
        mHandlerThread.start();

        mThreadHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                updateUI(); // 模拟数据更新

                try {
                    String threadName = Thread.currentThread().getName();
                    Logger.d(threadName+" --> doing long-running operations.");
                    Thread.sleep(2000); // 模拟耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isUpdateInfo) {
                    Message.obtain(mThreadHandler).sendToTarget();
                }
            }
        };
    }

    private void updateUI() {
        // 在子线程中可以这样更新UI
        tvMain.post(new Runnable() {
            @Override
            public void run() {
                String result = "每隔2秒更新一下数据：";
                result += Math.random();
                tvMain.setText(result);
            }
        });

        /*mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                String result = "每隔2秒更新一下数据：";
                result += Math.random();
                tvMain.setText(result);
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        isUpdateInfo = true; // 开始查询
        Message.obtain(mThreadHandler).sendToTarget();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUpdateInfo = false; // 停止查询，以防退出界面后Handler还在执行
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit(); // 释放资源
    }

}
