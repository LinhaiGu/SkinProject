package com.glh.skindemo.skin;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.glh.skindemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkinChange {

    private SkinChange(){}

    public static SkinChange getInstance(){
        return Holder.SKIN_CHANGE;
    }

     private static class Holder{
         private static final SkinChange SKIN_CHANGE=new SkinChange();
    }

    private List<SkinChange.Skin> mSkinListView = new ArrayList<>();

    public List<SkinChange.Skin> getSkinViewList(){
        return mSkinListView;
    }

    public void saveSkin(Context context, AttributeSet attrs, View view) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Skin);
        boolean skin = a.getBoolean(R.styleable.Skin_skinChange, false);
        if (skin) {
            final int Len = attrs.getAttributeCount();
            HashMap<String, String> attrMap = new HashMap<>();
            for (int i = 0; i < Len; i++) {
                String attrName = attrs.getAttributeName(i);
                String attrValue = attrs.getAttributeValue(i);
                attrMap.put(attrName, attrValue);
//                Log.d("saveSkin","attrName="+attrName+"  attrValue="+attrValue);
            }
            Log.d("saveSkin",view.toString());
            SkinChange.Skin skinView = new SkinChange.Skin();
            skinView.view = view;
            skinView.attrsMap = attrMap;
            mSkinListView.add(skinView);
        }

    }


    public static class Skin{
        View view;
        HashMap<String, String> attrsMap;
    }
}
