package com.gzr.tool;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gzr.tool.util.ScreenUtil;
import com.gzr.tool.util.SharePref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_get_screeninfo)
    Button btnGetScreeninfo;

    //1、首先声明一个数组permissions，将所有需要申请的权限都放在里面
    private String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};
    //2、创建一个mPermissionList，逐个判断哪些权限未授权，将未授权的权限存储到mPermissionList中
    private List<String> mPermissionList = new ArrayList<String>();
    //3、声明一个请求码，在请求权限的回调方法onRequestPermissionsResult中需要判断使用
    private final int mRequestCode = 100;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //boolean first = SharePref.getBoolean("frist",true);
        //if(first){
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
        //}else{
        //   initView();
        //}
    }

    private void initView(){
        ButterKnife.bind(this);

        btnGetScreeninfo.setOnClickListener(this);

        initPermission();
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

    //4、权限判断和申请
    private void initPermission(){
        mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0;i<permissions.length;i++){
            if (ContextCompat.checkSelfPermission(this,permissions[i])!=
                    PackageManager.PERMISSION_GRANTED){
                mPermissionList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
            }
        }
        //申请权限
        if (mPermissionList.size()>0){//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this,permissions,mRequestCode);
        }else {
            //权限已经都通过了，可以将程序继续打开了
        }
    }

    /**
     * 5.请求权限后回调的方法
     * @param requestCode 是我们自己定义的权限请求码
     * @param permissions 是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限
     *                     名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode==requestCode){
            for (int i=0;i<grantResults.length;i++){
                if (grantResults[i]==-1){
                    hasPermissionDismiss=true;
                    break;
                }
            }
        }
        if (hasPermissionDismiss){//如果有没有被允许的权限
            showPermissionDialog();
        }else {
            //权限已经都通过了，可以将程序继续打开了
        }
    }

    /**
     *  6.不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;
    String mPackName = "com.gzr.tool";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.
                                    ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            MainActivity.this.finish();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //7、关闭对话框
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }
}
