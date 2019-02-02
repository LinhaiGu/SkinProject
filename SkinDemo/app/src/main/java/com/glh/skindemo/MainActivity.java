package com.glh.skindemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.glh.skindemo.skin.SkinChangeBiz;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private Button mBtnSkin;
    private Button mBtnSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        mBtnSkin=findViewById(R.id.btn_skin);
        mBtnSecond=findViewById(R.id.btn_second);
        mBtnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        mBtnSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行换肤
                SkinChangeBiz.getInstance().change("skinPlugin.apk");
            }
        });
    }

    public static void verifyStoragePermissions(AppCompatActivity activity) {

        try {
            int permission = ActivityCompat.checkSelfPermission(activity,PERMISSIONS_STORAGE[1]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
