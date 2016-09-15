package com.example.bilaizi.androidagera;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class UiUtils {
    /**
     * Starts an animation that will jank if the UI thread is busy.
     * @param animView
     */
    public static void startAnimation(View animView) {
        Animation tx = new TranslateAnimation(-350, 350, 0, 0);
        tx.setDuration(1000);
        tx.setRepeatCount(Animation.INFINITE);
        tx.setInterpolator(new AccelerateDecelerateInterpolator());
        tx.setRepeatMode(Animation.REVERSE);
        animView.startAnimation(tx);
    }

    static void stopAnimation(View animView) {
        animView.clearAnimation();
    }
}
