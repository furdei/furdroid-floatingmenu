package com.furdei.furdroid.floatingmenu;

import android.view.animation.Animation;

/**
 * <p>
 * Implementations of this interface will provide construction logic for creating animation
 * for floating menus on-demand. Some floating menu implementations may require rebuilding
 * animation when their layout changes. They can call {@link #get()} several times
 * during menu life cycle. In most cases animation will be built once but you should not rely
 * on that.
 * </p><p>
 * Typical menu uses several different animations: open animation, close animation, animation on
 * 'open/close' button while opening or closing a menu etc. Different instances of
 * <code>AnimationProvider</code> children should be used for each kind of animation, there is no
 * need to build all these animation with a single <code>AnimationProvider</code>.
 * </p>
 *
 * @author Stepan Furdey
 */
public interface AnimationProvider {

    /**
     * Get animation for menu. You can load, build or rebuild animation here or just return
     * previously built instance.
     *
     * @return animation object. You can return the same {@link android.view.animation.Animation
     *      Animation} instance for every call if there is no need to rebuild animation in this
     *      particular case.
     */
    Animation get();

}
