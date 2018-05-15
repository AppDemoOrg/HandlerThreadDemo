package com.abt.handler.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.abt.handler.R;

/**
 * Created by huangweiqi on 27/04/2018.
 */
public class HandlerActivity extends AppCompatActivity implements Handler.Callback {

    private Handler mMainHandler;
    private TextView tvMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SleepRunnable runnable = new SleepRunnable();
        new Thread(runnable).start();

        tvMain = findViewById(R.id.tv_main);
        mMainHandler = new Handler(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        /*try {
            String threadName = Thread.currentThread().getName();
            Logger.d(threadName+" --> doing long-running operations.");
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String result = "每隔2秒更新一下数据：";
        result += Math.random();
        tvMain.setText(result);
        return true;
    }

    class SleepRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message.obtain(mMainHandler).sendToTarget();
            }
        }
    }
}
