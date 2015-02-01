package com.furdei.furdroid.floatingmenu.bottom;

import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.furdei.furdroid.floatingmenu.animation.StaticAnimationProvider;

/**
 * Constructs open animation for bottom menu container
 *
 * @author Stepan Furdey
 */
public class ContainerOpenAnimationProvider extends StaticAnimationProvider
        implements BottomMenuAnimationProvider {

    @Override
    public void rebuildAnimation(float closedTranslation) {
        AnimationSet openAnimation = new AnimationSet(true);
        openAnimation.addAnimation(new AlphaAnimation(0.5f, 1.0f));
        openAnimation.addAnimation(new TranslateAnimation(0f, 0f, closedTranslation, 0f));
        openAnimation.setDuration(DEFAULT_DURATION);
        openAnimation.setInterpolator(new DecelerateInterpolator());
        setAnimation(openAnimation);
    }
}
