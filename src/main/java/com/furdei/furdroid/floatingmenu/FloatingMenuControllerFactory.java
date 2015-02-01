package com.furdei.furdroid.floatingmenu;

/**
 * Factory for creating new <code>FloatingMenuController</code> instances. Used by
 * <code>FloatingMenuBuilder</code>. You can provide your own factory to
 * <code>FloatingMenuBuilder</code>'s <code>setControllerFactory</code> method if you want to
 * change life cycle logic for your menu.
 *
 * @author Stepan Furdey
 */
public interface FloatingMenuControllerFactory {
    public FloatingMenuController createFloatingMenuControllerInstance();
}
