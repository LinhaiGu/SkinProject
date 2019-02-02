package com.glh.skindemo.skin;

import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class SkinChangeBiz implements ISkinView {

    private static class Holder {
        private static final SkinChangeBiz SKIN_CHANGE_BIZ = new SkinChangeBiz();
    }

    private String mPath=null;

    public static SkinChangeBiz getInstance() {
        return Holder.SKIN_CHANGE_BIZ;
    }

    @Override
    public void change(String path) {
        this.mPath=path;
        File skinFile = new File(Environment.getExternalStorageDirectory(), path);
        LoadResources.getInstance().load(skinFile.getAbsolutePath());
        for (SkinChange.Skin skinView : SkinChange.getInstance().getSkinViewList()) {
            changeSkin(skinView);
        }
    }

    public void change(){
        if(TextUtils.isEmpty(mPath)){
            return;
        }
        change(mPath);
    }

    private void changeSkin(SkinChange.Skin skinView) {
        if (!TextUtils.isEmpty(skinView.attrsMap.get("background"))) {
            int bgId = Integer.parseInt(skinView.attrsMap.get("background").substring(1));
            String attrType = skinView.view.getResources().getResourceTypeName(bgId);
            if (TextUtils.equals(attrType, "drawable")) {
                skinView.view.setBackground(LoadResources.getInstance().getDrawable(bgId));
            } else if (TextUtils.equals(attrType, "color")) {
                skinView.view.setBackgroundColor(LoadResources.getInstance().getColor(bgId));
            }
        }
        if (skinView.view instanceof ImageView) {
            if (!TextUtils.isEmpty(skinView.attrsMap.get("src"))) {
                int bgId = Integer.parseInt(skinView.attrsMap.get("src").substring(1));
                ((ImageView )skinView.view).setImageDrawable(LoadResources.getInstance().getDrawable(bgId));
            }
        }
        if (skinView.view instanceof TextView) {
            if (!TextUtils.isEmpty(skinView.attrsMap.get("textColor"))) {
                int textColorId = Integer.parseInt(skinView.attrsMap.get("textColor").substring(1));
                ((TextView) skinView.view).setTextColor(LoadResources.getInstance().getColor(textColorId));
            }
        }

    }

}
