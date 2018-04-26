package com.abt.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by huangweiqi on 27/04/2018.
 */
public class HandlerActivity extends Activity {

    private Handler mMainHandler;// = new Handler();
    private TextView tvMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMain = findViewById(R.id.tv_main);

        mMainHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String result = "每隔2秒更新一下数据：";
                result += Math.random();
                tvMain.setText(result);
                // 这里已经是主线程了
                tvMain.post(new Runnable() {
                    @Override
                    public void run() {
                        // ...
                    }
                });
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 这里是子线程
                    Message.obtain(mMainHandler).sendToTarget();
                }
            }
        }).start();
    }
}
