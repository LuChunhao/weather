package com.example.tianqiyubao.util;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.tianqiyubao.R;


/**
 */

public class ShakeHelper {

    /**
     * edittext震动的辅助类
     *
     * @param editText
     */
    public static void shake(EditText editText){
        Animation shake = AnimationUtils.loadAnimation(editText.getContext(), R.anim.shake);
        editText.startAnimation(shake);
    }
}
