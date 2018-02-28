package com.shrikanthravi.cinemaappconcept.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by shrikanthravi on 28/02/18.
 */

public class TransitionHelper {

    public static void fixSharedElementTransitionForStatusAndNavigationBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        final View decor = activity.getWindow().getDecorView();
        if (decor == null)
            return;
        activity.postponeEnterTransition();
        decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onPreDraw() {
                decor.getViewTreeObserver().removeOnPreDrawListener(this);
                activity.startPostponedEnterTransition();
                return true;
            }
        });
    }

    public static void setSharedElementEnterTransition(final Activity activity, int transition) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;
        activity.getWindow().setSharedElementEnterTransition(TransitionInflater.from(activity).inflateTransition(transition));
    }
}
