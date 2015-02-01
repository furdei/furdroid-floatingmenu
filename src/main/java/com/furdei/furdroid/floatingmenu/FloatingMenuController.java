package com.furdei.furdroid.floatingmenu;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * FloatingMenuController manages lifecycle of a floating menu components: button clicks,
 * animations etc. This class does NOT provide any view for the menu, so you can create floating
 * menus of any shapes and layouts you want. This class only controls view visibility, launches
 * animation, reacts to user events and provides interaction to your activity.
 * </p>
 * You can call it's methods programmatically to open/close/toggle menu with or without animation.
 * </p>
 * Normally you don't want to create instances of FloatingMenuController descendants yourself.
 * FloatingMenuBuilder uses FloatingMenuControllerFactory to instantiate FloatingMenuController
 * descendants. Create and provide your factory if you want to change life cycle logic for menu.
 *
 * @author Stepan Furdey
 */
public interface FloatingMenuController {

    /**
     * A listener to make it possible reacting to menu life cycle events
     */
    public interface FloatingMenuListener {
        /**
         * Called just before a menu is going to become visible. It's still hidden when this
         * event goes off.
         *
         * @param controller calling menu controller
         */
        void onBeforeOpened(FloatingMenuController controller);

        /**
         * Called right after a menu is fully opened, e.g. open animation has been finished or
         * menu has been opened without any animation.
         *
         * @param controller calling menu controller
         */
        void onAfterOpened(FloatingMenuController controller);

        /**
         * Called just before a menu is going to start closing. It's still visible and holds it
         * usual fully opened position. Close animation will start after this method is finished.
         *
         * @param controller calling menu controller
         */
        void onBeforeClosed(FloatingMenuController controller);

        /**
         * Called right after a menu is fully closed, e.g. close animation has been finished or
         * menu has been closed without any animation. A menu object has been hidden by the moment
         * this method gets called.
         *
         * @param controller calling menu controller
         */
        void onAfterClosed(FloatingMenuController controller);
    }

    View getOpenCloseButton();

    /**
     * View has to be clickable if you want this menu manager to show or hide menu in respond
     * to clicks by user. However you can temporary disable clickable state of the
     * <code>openCloseButton</code> if you don't want a user to open/close the menu. Please
     * consider hiding menu button (<code>setVisibility(View.GONE)</code>) instead of disabling it.
     * You have full control over <code>openCloseButton</code>'s state, visibility etc.
     * <code>FloatingMenuController</code> only replaces it's onClickListener to attach menu
     * life cycle logic.
     *
     * @param openCloseButton a button to show/hide menu
     */
    void setOpenCloseButton(View openCloseButton);

    ViewGroup getMenuContainer();

    /**
     * Container to host a menu. You should place it yourself into your fragment or activity.
     * There is no limitation on where to put this container. <code>FloatingMenuController</code>
     * will only take control over menuContainer's visibility and start show/hide animation.
     *
     * @param menuContainer a container to host a menu. It should be in a 'menu closed' state by
     *                      default, e.g. hidden.
     */
    void setMenuContainer(ViewGroup menuContainer);

    AnimationProvider getContainerOpenAnimationProvider();

    /**
     * Sets an animation provider which constructs an animation to play on a menu container
     * when menu is to be shown.
     *
     * @param openAnimationProvider an animation provider to construct open animation
     */
    void setContainerOpenAnimationProvider(AnimationProvider openAnimationProvider);

    AnimationProvider getContainerCloseAnimationProvider();

    /**
     * Sets an animation provider which constructs an animation to play on a menu container
     * when menu is to be hidden.
     *
     * @param closeAnimationProvider an animation provider to construct close animation
     */
    void setContainerCloseAnimationProvider(AnimationProvider closeAnimationProvider);

    AnimationProvider getButtonOpenAnimationProvider();

    /**
     * Sets an animation provider which constructs an animation to play on an 'open/close' button
     * when menu is to be shown.
     *
     * @param openAnimationProvider an animation provider to construct open animation
     */
    void setButtonOpenAnimationProvider(AnimationProvider openAnimationProvider);

    AnimationProvider getButtonCloseAnimationProvider();

    /**
     * Sets an animation provider which constructs an animation to play on an 'open/close' button
     * when menu is to be hidden.
     *
     * @param closeAnimationProvider an animation provider to construct close animation
     */
    void setButtonCloseAnimationProvider(AnimationProvider closeAnimationProvider);

    /**
     * Take control over a new menu item. Controller should set here any listeners it needs to
     * respond to user's actions.
     *
     * @param menuItem a new menu item
     * @param menuItemView a view to display new menu item
     */
    void initializeMenuItem(MenuItem menuItem, View menuItemView);

    /**
     * Checks if menu is opened and visible
     *
     * @return <code>true</code> if menu is opened, <code>false</code> otherwise
     */
    boolean isOpened();

    /**
     * Shows floating menu playing animation specified by
     * {@link #setContainerOpenAnimationProvider(AnimationProvider)} and
     * {@link #setButtonOpenAnimationProvider(AnimationProvider)} methods.
     * <code>openAnimated</code> method falls back to opening menu without animation in case when
     * neither container nor button animation is provided.
     */
    void openAnimated();

    /**
     * Hides floating menu playing animation specified by
     * {@link #setContainerCloseAnimationProvider(AnimationProvider)} and
     * {@link #setButtonCloseAnimationProvider(AnimationProvider)} methods.
     * <code>closeAnimated</code> method falls back to closing menu without animation in case when
     * neither container nor button animation is provided.
     */
    void closeAnimated();

    /**
     * Shows floating menu if menu is hidden or hides it otherwise. In any case this method tries
     * to play open/close animation. This method falls back to opening/closing menu without
     * animation in case when animation is not provided.
     * @see #openAnimated()
     * @see #closeAnimated()
     */
    void toggleAnimated();

    /**
     * Opens a floating menu without animation. Does not affect a menu if it is already visible.
     */
    void open();

    /**
     * Closes a floating menu without animation. Does not affect a menu if it is already hidden.
     */
    void close();

    /**
     * Shows floating menu if menu is hidden or hides it otherwise. Does not play any animation.
     * @see #open()
     * @see #close()
     */
    void toggle();

    /**
     * Adds a floating menu events listener. When there are several listeners for a single menu
     * then these methods are called in the same order the listeners have been added to a menu
     * controller:
     * {@link FloatingMenuController.FloatingMenuListener#onBeforeOpened(FloatingMenuController) onBeforeOpened},
     * {@link FloatingMenuController.FloatingMenuListener#onAfterOpened(FloatingMenuController) onAfterOpened}.
     * And these methods are called in backward order:
     * {@link FloatingMenuController.FloatingMenuListener#onBeforeClosed(FloatingMenuController) onBeforeClosed},
     * {@link FloatingMenuController.FloatingMenuListener#onAfterClosed(FloatingMenuController) onAfterClosed}.
     *
     * @param listener a new listener to add. The same listener cannot be added twice to the
     *                 same menu controller.
     */
    void addFloatingMenuListener(FloatingMenuListener listener);

    /**
     * Removes a floating menu events listener.
     *
     * @param listener a new listener to add. The same listener cannot be added twice to the
     *                 same menu controller.
     */
    void removeFloatingMenuListener(FloatingMenuListener listener);

    /**
     * Removes all floating menu events listener.
     */
    void removeAllFloatingMenuListeners();

    Drawable getClosedButtonDrawable();

    void setClosedButtonDrawable(Drawable closedButtonDrawable);

    Drawable getOpenedButtonDrawable();

    void setOpenedButtonDrawable(Drawable openedButtonDrawable);

}
