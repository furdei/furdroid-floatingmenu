package com.furdei.furdroid.floatingmenu.animation;

import android.view.animation.Animation;

import com.furdei.furdroid.floatingmenu.AnimationProvider;

/**
 * Implementation of {@link com.furdei.furdroid.floatingmenu.AnimationProvider AnimationProvider}
 * which returns the same animation instance specified in constructor or through
 * {@link #setAnimation(android.view.animation.Animation)} method.
 *
 * @author Stepan Furdey
 */
public class StaticAnimationProvider implements AnimationProvider {

    private Animation animation;

    public StaticAnimationProvider() {
    }

    public StaticAnimationProvider(Animation animation) {
        setAnimation(animation);
    }

    @Override
    public Animation get() {
        return animation;
    }

    /**
     * Specify animation to return to menu controller
     *
     * @param animation new animation
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
