package com.github.captain_miao.seatview;

import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * @author YanLu
 * @since 16/10/4
 */

public abstract class ValuesAnimation<T> extends AnimatorListenerAdapter implements TypeEvaluator<T>, ValueAnimator.AnimatorUpdateListener {

    /**
     * The interpolator, used for making animate 'naturally.'
     */
    private Interpolator mInterpolator;

    /**
     * The total animation duration.
     */
    private int mAnimationDurationMillis;

    ValueAnimator mValueAnimator;

    public ValuesAnimation(Context context) {
        mInterpolator = new DecelerateInterpolator();
        mAnimationDurationMillis = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    public ValuesAnimation(Interpolator interpolator, int durationMillis) {
        mInterpolator = interpolator;
        mAnimationDurationMillis = durationMillis;
    }


    public void start(T from, T to) {
        if(mValueAnimator != null){
            mValueAnimator.cancel();
        }
        mValueAnimator = ValueAnimator.ofObject(this, from, to);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.setDuration(mAnimationDurationMillis);
        mValueAnimator.start();
    }


    public void cancel(){
        mValueAnimator.cancel();
    }

}
