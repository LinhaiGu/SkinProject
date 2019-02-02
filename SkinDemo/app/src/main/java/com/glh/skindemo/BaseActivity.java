package com.glh.skindemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.glh.skindemo.factory.SkinFactory;
import com.glh.skindemo.skin.SkinChangeBiz;

public abstract class BaseActivity extends AppCompatActivity {

    private SkinFactory mSkinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(null == mSkinFactory){
            mSkinFactory=new SkinFactory();
        }
        mSkinFactory.setDelegate(getDelegate());
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        layoutInflater.setFactory2(mSkinFactory);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinChangeBiz.getInstance().change();
    }
}
