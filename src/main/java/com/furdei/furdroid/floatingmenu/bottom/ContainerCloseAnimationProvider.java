package com.furdei.furdroid.floatingmenu.bottom;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.furdei.furdroid.floatingmenu.animation.StaticAnimationProvider;

/**
 * Constructs close animation for bottom menu container
 *
 * @author Stepan Furdey
 */
public class ContainerCloseAnimationProvider extends StaticAnimationProvider
        implements BottomMenuAnimationProvider {

    @Override
    public void rebuildAnimation(float closedTranslation) {
        AnimationSet closeAnimation = new AnimationSet(true);
        closeAnimation.addAnimation(new AlphaAnimation(1.0f, 0.5f));
        closeAnimation.addAnimation(new TranslateAnimation(0f, 0f, 0f, closedTranslation));
        closeAnimation.setDuration(DEFAULT_DURATION);
        closeAnimation.setInterpolator(new AccelerateInterpolator());
        setAnimation(closeAnimation);
    }

}
