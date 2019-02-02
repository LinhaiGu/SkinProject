package com.glh.skindemo.factory;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.glh.skindemo.skin.SkinChange;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class SkinFactory implements LayoutInflater.Factory2 {

    private AppCompatDelegate mDelegate;

    static final Class<?>[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};//
    final Object[] mConstructorArgs = new Object[2];
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap = new HashMap<String, Constructor<? extends View>>();
    static final String[] prefix = new String[]{
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    public void setDelegate(AppCompatDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        View view = mDelegate.createView(parent, name, context, attrs);
        if (view == null) {
            mConstructorArgs[0] = context;
            try {
                if (-1 == name.indexOf('.')) {
                    view = createViewByPrefix(context, name, prefix, attrs);
                } else {
                    view = createViewByPrefix(context, name, null, attrs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //保存需要换肤的View
        SkinChange.getInstance().saveSkin(context, attrs, view);

        return view;
    }

    private  View createViewByPrefix(Context context, String name, String[] prefixs, AttributeSet attrs) {

        Constructor<? extends View> constructor = sConstructorMap.get(name);
        Class<? extends View> clazz = null;

        if (constructor == null) {
            try {
                if (prefixs != null && prefixs.length > 0) {
                    for (String prefix : prefixs) {
                        clazz = context.getClassLoader().loadClass(
                                prefix != null ? (prefix + name) : name).asSubclass(View.class);
                        if (clazz != null) break;
                    }
                } else {
                    clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                }
                if (clazz == null) {
                    return null;
                }
                constructor = clazz.getConstructor(mConstructorSignature);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            constructor.setAccessible(true);
            //缓存
            sConstructorMap.put(name, constructor);
        }
        Object[] args = mConstructorArgs;
        args[1] = attrs;
        try {
            //通过反射创建View对象
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
