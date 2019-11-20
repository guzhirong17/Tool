package com.gzr.tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends Activity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(2000);
                }catch(Exception e){
                    e.printStackTrace();
                }

                MyApplication.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(WelcomeActivity.this,LaunchSimpleActivity.class);
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    }
                });
            }
        }.start();
    }
}
