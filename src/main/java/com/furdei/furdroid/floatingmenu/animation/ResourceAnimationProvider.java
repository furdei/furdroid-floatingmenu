package com.furdei.furdroid.floatingmenu.animation;

import android.content.Context;
import android.view.animation.AnimationUtils;

/**
 * Implementation of {@link com.furdei.furdroid.floatingmenu.AnimationProvider AnimationProvider}
 * which loads animation from resources.
 *
 * @author Stepan Furdey
 */
public class ResourceAnimationProvider extends StaticAnimationProvider {

    public ResourceAnimationProvider(Context context, int animationResourceId) {
        super(AnimationUtils.loadAnimation(context, animationResourceId));
    }

}
