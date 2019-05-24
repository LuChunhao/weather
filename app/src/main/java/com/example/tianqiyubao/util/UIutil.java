package com.example.tianqiyubao.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class UIutil {

    public static void zoomIn(View view, float scale, float dist) {
        view.setPivotY((float)view.getHeight());
        view.setPivotX((float)(view.getWidth() / 2));
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", new float[]{1.0F, scale});
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", new float[]{1.0F, scale});
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", new float[]{0.0F, -dist});
        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300L);
        mAnimatorSet.start();
    }

    public static void zoomOut(View view, float scale) {
        view.setPivotY((float)view.getHeight());
        view.setPivotX((float)(view.getWidth() / 2));
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", new float[]{scale, 1.0F});
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", new float[]{scale, 1.0F});
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", new float[]{view.getTranslationY(), 0.0F});
        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300L);
        mAnimatorSet.start();
    }
}
