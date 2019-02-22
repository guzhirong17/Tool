package com.gzr.tool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gzr.tool.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_get_screeninfo)
    Button btnGetScreeninfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnGetScreeninfo.setOnClickListener(this);
    }

    public void showScreenInfo() {
        int width = ScreenUtil.getScreenWidth(this);
        int height = ScreenUtil.getScreenHeight(this);
        float density = ScreenUtil.getScreenDensity(this);
        String str = "当前手机屏幕分辨率宽度：" + width + "\r\n" +
                "当前手机屏幕分辨率高度：" + height + "\r\n" +
                "当前手机屏幕分辨率密度：" + density;
        tv.setText(str);
    }

    @Override
    public void onClick(View v) {
        if(v == btnGetScreeninfo){
            showScreenInfo();
        }
    }
}
