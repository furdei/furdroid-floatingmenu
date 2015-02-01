package com.furdei.furdroid.floatingmenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.furdei.furdroid.floatingmenu.animation.ResourceAnimationProvider;
import com.furdei.furdroid.floatingmenu.bottom.BottomFloatingMenuController;
import com.furdei.furdroid.floatingmenu.bottom.BottomFloatingMenuControllerFactory;
import com.furdei.furdroid.floatingmenu.bottom.ContainerCloseAnimationProvider;
import com.furdei.furdroid.floatingmenu.bottom.ContainerOpenAnimationProvider;
import com.furdei.furdroid.floatingmenu.layout.HierarchicalContainerMenuLayoutManager;

/**
 * <p>
 * <code>BottomMenuBuilder</code> helps you create a floating menu at the bottom of the
 * window with an open/close button attached. You can slide menu up/down by clicking open/close
 * button. This UI pattern does <b>not</b> provide gestures, because sliding up/down gestures are
 * used primarily to scroll the content of activities. It could puzzle the user if we provide
 * vertical swipes to open/close horizontal menu. So it is decided not to affect user experience.
 * </p>
 * <h3>Building up a menu</h3>
 * <p>
 * Use {@link #setMenuResId(int)} method to provide menu resource. Menu builder uses this resource
 * to inflate menu content dynamically.
 * </p>
 * Sample code:
 * <pre>
 * {@code
 * BottomMenuBuilder menuBuilder = new BottomMenuBuilder(this)
 *      .setMenuResId(R.menu.dashboard_quick);
 * FloatingMenuController floatingMenuController = menuBuilder.build();
 * }
 * </pre>
 * See {@link FloatingMenuBuilder FloatingMenuBuilder}
 * for further details on using floating menus.
 *
 * @see FloatingMenuBuilder
 *
 * @author Stepan Furdey
 */
public class BottomMenuBuilder extends FloatingMenuBuilder {

    private ViewGroup menuLayout;
    private ViewGroup menuDecor;
    private View menuOverlay;
    private AnimationProvider overlayOpenAnimationProvider;
    private AnimationProvider overlayCloseAnimationProvider;

    public BottomMenuBuilder(Activity activity) {
        super(activity);
        activity.getTheme().applyStyle(R.style.Theme_FloatingMenu_Horizontal, false);
        setMenuLayoutManager(new HierarchicalContainerMenuLayoutManager(activity));
        setControllerFactory(new BottomFloatingMenuControllerFactory(activity));
        setMenuDecor(R.layout.bottom_menu_overlay);
        setMenuLayout(R.layout.bottom_menu_layout);
        setOpenCloseButton(R.id.quick_menu_button);
        setOpenedButtonDrawable(R.drawable.ic_close_menu);
        setClosedButtonDrawable(R.drawable.ic_open_menu);
        setMenuContainer(R.id.quick_menu_container);
        setOpenAnimationProvider(new ContainerOpenAnimationProvider());
        setCloseAnimationProvider(new ContainerCloseAnimationProvider());
        setOverlayOpenAnimationProvider(
                new ResourceAnimationProvider(activity, R.anim.alpha_open));
        setOverlayCloseAnimationProvider(
                new ResourceAnimationProvider(activity, R.anim.alpha_close));
    }

    /**
     * Decor view wraps all views in a window including Action Bar etc. Used to implement a
     * shade effect when menu is getting opened/closed
     */
    public ViewGroup getMenuDecor() {
        return menuDecor;
    }

    /**
     * Decor view wraps all views in a window including Action Bar etc. Used to implement a
     * shade effect when menu is getting opened/closed. Decoration view should have child views
     * with <code>R.id.quick_menu_layout</code> and <code>R.id.quick_menu_overlay</code> ids.
     *
     * @param menuDecor a new view to become a decoration view of the window
     * @return Link to the same {@link BottomMenuBuilder} instance to chain calls
     */
    public BottomMenuBuilder setMenuDecor(ViewGroup menuDecor) {
        this.menuDecor = menuDecor;
        this.menuLayout = (ViewGroup) menuDecor.findViewById(R.id.quick_menu_layout);
        this.menuOverlay = menuDecor.findViewById(R.id.quick_menu_overlay);
        return this;
    }

    /**
     * Decor view wraps all views in a window including Action Bar etc. Used to implement a
     * shade effect when menu is getting opened/closed. Decoration view should have child views
     * with <code>R.id.quick_menu_layout</code> and <code>R.id.quick_menu_overlay</code> ids.
     *
     * @param menuOverlayResId a new view resource to become a decoration view of the window
     * @return Link to the same {@link BottomMenuBuilder} instance to chain calls
     */
    public BottomMenuBuilder setMenuDecor(int menuOverlayResId) {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return setMenuDecor((ViewGroup) inflater.inflate(menuOverlayResId, null, false));
    }

    /**
     * Menu layout holds a menu itself (hidden over the bottom of the window by default) and an
     * Open/Close menu button (visible right above the bottom of the window). This means that
     * menu layout is partially visible.
     */
    public ViewGroup getMenuLayout() {
        return (ViewGroup) menuLayout.getChildAt(0);
    }

    /**
     * Menu layout holds a menu itself (hidden over the bottom of the window by default) and an
     * Open/Close menu button (visible right above the bottom of the window). This means that
     * menu layout is partially visible.
     *
     * @param menuLayout a new menu layout
     * @return Link to the same {@link BottomMenuBuilder} instance to chain calls
     */
    public BottomMenuBuilder setMenuLayout(ViewGroup menuLayout) {
        if (this.menuLayout == null) {
            throw new IllegalStateException("You should call setMenuDecor first");
        }

        this.menuLayout.removeAllViews();
        this.menuLayout.addView(menuLayout);
        return this;
    }

    /**
     * Menu layout holds a menu itself (hidden over the bottom of the window by default) and an
     * Open/Close menu button (visible right above the bottom of the window). This means that
     * menu layout is partially visible.
     *
     * @param menuLayoutResId a new menu layout resource
     * @return Link to the same {@link BottomMenuBuilder} instance to chain calls
     */
    public BottomMenuBuilder setMenuLayout(int menuLayoutResId) {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return setMenuLayout((ViewGroup) inflater.inflate(menuLayoutResId, null, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatingMenuBuilder setOpenCloseButton(int openCloseButtonResId) {
        if (menuLayout == null) {
            throw new IllegalStateException("You should call setMenuLayout first");
        }

        return super.setOpenCloseButton(menuLayout.findViewById(openCloseButtonResId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatingMenuBuilder setMenuContainer(int menuContainerResId) {
        if (menuLayout == null) {
            throw new IllegalStateException("You should call setMenuLayout first");
        }

        return super.setMenuContainer((ViewGroup) menuLayout.findViewById(menuContainerResId));
    }

    /**
     * Animation provider that creates animation to play upon a window shadow overlay while
     * menu is being opened. Alpha animation is the most common example here.
     */
    public AnimationProvider getOverlayOpenAnimationProvider() {
        return overlayOpenAnimationProvider;
    }

    /**
     * Animation provider that creates animation to play upon a window shadow overlay while
     * menu is being opened. Alpha animation is the most common example here.
     *
     * @param overlayOpenAnimationProvider an animation provider for window overlay
     * @return Link to the same {@link BottomMenuBuilder} instance to chain calls
     */
    public BottomMenuBuilder setOverlayOpenAnimationProvider(
            AnimationProvider overlayOpenAnimationProvider) {
        this.overlayOpenAnimationProvider = overlayOpenAnimationProvider;
        return this;
    }

    /**
     * Animation provider that creates animation to play upon a window shadow overlay while
     * menu is being closed. Alpha animation is the most common example here.
     */
    public AnimationProvider getOverlayCloseAnimationProvider() {
        return overlayCloseAnimationProvider;
    }

    /**
     * Animation provider that creates animation to play upon a window shadow overlay while
     * menu is being closed. Alpha animation is the most common example here.
     *
     * @param overlayCloseAnimationProvider an animation provider for window overlay
     * @return Link to the same {@link BottomMenuBuilder} instance to chain calls
     */
    public BottomMenuBuilder setOverlayCloseAnimationProvider(
            AnimationProvider overlayCloseAnimationProvider) {
        this.overlayCloseAnimationProvider = overlayCloseAnimationProvider;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUpMenuController(FloatingMenuController controller) {
        super.setUpMenuController(controller);
        BottomFloatingMenuController bottomController = (BottomFloatingMenuController) controller;
        bottomController.setMenuDecor(menuDecor);
        bottomController.setMenuRoot((ViewGroup) menuLayout.getChildAt(0));
        bottomController.setMenuOverlay(menuOverlay);
        bottomController.setOverlayOpenAnimationProvider(overlayOpenAnimationProvider);
        bottomController.setOverlayCloseAnimationProvider(overlayCloseAnimationProvider);
        bottomController.replaceWindowView();
    }
}
