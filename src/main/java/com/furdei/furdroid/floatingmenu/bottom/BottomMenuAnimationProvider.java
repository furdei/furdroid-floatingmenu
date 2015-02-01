package com.furdei.furdroid.floatingmenu.bottom;

import com.furdei.furdroid.floatingmenu.AnimationProvider;

/**
 * Animation provider which should rebuild animation when bottom menu layout changes.
 *
 * @author Stepan Furdey
 */
public interface BottomMenuAnimationProvider extends AnimationProvider {

    /**
     * Default bottom menu animation duration
     */
    public static final int DEFAULT_DURATION = 250;

    /**
     * Rebuild animation using new Y translation in a closed state.
     *
     * @param closedTranslation value applied to {@link android.view.View#setTranslationY(float)
     *                          setTranslationY} method in a 'menu closed' state.
     */
    void rebuildAnimation(float closedTranslation);
}
