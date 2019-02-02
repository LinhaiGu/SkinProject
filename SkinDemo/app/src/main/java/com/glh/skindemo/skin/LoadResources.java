package com.glh.skindemo.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.lang.reflect.Method;

public class LoadResources {

    public static LoadResources getInstance() {
        return Holder.LOAD_RESOURCES;
    }

    private LoadResources() {
    }

    private static class Holder{
        private static final LoadResources LOAD_RESOURCES=new LoadResources();
    }
    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private Resources mSkinResources;
    private Context mContext;
    private String mOutPkgName;

    public void load(final String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        PackageManager mPm = mContext.getPackageManager();
        PackageInfo mInfo = mPm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        mOutPkgName = mInfo.packageName;
        AssetManager assetManager;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            mSkinResources = new Resources(assetManager,
                    mContext.getResources().getDisplayMetrics(),
                    mContext.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getColor(int resId) {
        if (mSkinResources == null) {
            return resId;
        }
        String resName = mSkinResources.getResourceEntryName(resId);
        int outResId = mSkinResources.getIdentifier(resName, "color", mOutPkgName);
        if (outResId == 0) {
            return resId;
        }
        return mSkinResources.getColor(outResId);
    }

    public Drawable getDrawable(int resId) {
        if (mSkinResources == null) {
            return ContextCompat.getDrawable(mContext, resId);
        }
        String resName = mSkinResources.getResourceEntryName(resId);
        int outResId = mSkinResources.getIdentifier(resName, "drawable", mOutPkgName);
        if (outResId == 0) {
            return ContextCompat.getDrawable(mContext, resId);
        }
        return mSkinResources.getDrawable(outResId);
    }


}
