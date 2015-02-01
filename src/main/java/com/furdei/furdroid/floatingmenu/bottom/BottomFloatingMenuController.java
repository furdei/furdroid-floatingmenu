package com.furdei.furdroid.floatingmenu.bottom;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.furdei.furdroid.floatingmenu.AnimationProvider;
import com.furdei.furdroid.floatingmenu.R;
import com.furdei.furdroid.floatingmenu.base.BaseFloatingMenuController;

/**
 * Basic out-of-the-box implementation of
 * {@link com.furdei.furdroid.floatingmenu.FloatingMenuController FloatingMenuController}.
 * In addition to
 * {@link com.furdei.furdroid.floatingmenu.base.BaseFloatingMenuController BaseFloatingMenuController}
 * provides control for open/close animation on window overlay.
 *
 * @author Stepan Furdey
 */
public class BottomFloatingMenuController extends BaseFloatingMenuController {

    private ViewGroup menuDecor;
    private ViewGroup menuRoot;
    private View menuOverlay;
    private int closedTranslation;
    private View.OnLayoutChangeListener menuRootLayoutChangeListener;
    private AnimationProvider overlayOpenAnimationProvider;
    private AnimationProvider overlayCloseAnimationProvider;

    public BottomFloatingMenuController(Activity activity) {
        super(activity);
        menuRootLayoutChangeListener = new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateClosedTranslation();
            }
        };
    }

    public ViewGroup getMenuRoot() {
        return menuRoot;
    }

    public void setMenuRoot(ViewGroup menuRoot) {
        if (this.menuRoot != null) {
            this.menuRoot.removeOnLayoutChangeListener(menuRootLayoutChangeListener);
        }

        this.menuRoot = menuRoot;
        this.menuRoot.addOnLayoutChangeListener(menuRootLayoutChangeListener);
        updateClosedTranslation();
    }

    public ViewGroup getMenuDecor() {
        return menuDecor;
    }

    public void setMenuDecor(ViewGroup menuDecor) {
        this.menuDecor = menuDecor;
    }

    public View getMenuOverlay() {
        return menuOverlay;
    }

    public void setMenuOverlay(View menuOverlay) {
        this.menuOverlay = menuOverlay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openAnimated() {
        menuOverlay.startAnimation(overlayOpenAnimationProvider.get());
        super.openAnimated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeAnimated() {
        menuOverlay.startAnimation(overlayCloseAnimationProvider.get());
        super.closeAnimated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doOpen() {
        menuRoot.setTranslationY(0f);
        menuOverlay.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doClose() {
        applyClosedTranslation();
        menuOverlay.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected View getOpenCloseAnimationView() {
        return menuRoot;
    }

    /**
     * Replace window decoration view our own.
     */
    public void replaceWindowView() {
        ViewGroup decorView = (ViewGroup) getActivity().getWindow().getDecorView();
        View oldRoot = decorView.getChildAt(0);
        View newRoot = menuDecor;
        ViewGroup newContent = (ViewGroup) newRoot.findViewById(R.id.quick_menu_content);
        decorView.removeViewAt(0);
        decorView.addView(newRoot);
        newContent.addView(oldRoot);
    }

    /**
     * Returns translation applied to {@link View#setTranslationY(float)} method of menu container.
     * This makes menu scroll over the bottom of the window so that the user can't see the menu.
     * When user opens the menu we just animate this translation from
     * <code>getClosedTranslation</code> value to zero and thus 'show' the menu.
     *
     * @return translation applied to Y coordinate of the menu in it's 'closed' state.
     */
    public int getClosedTranslation() {
        return closedTranslation;
    }

    public AnimationProvider getOverlayOpenAnimationProvider() {
        return overlayOpenAnimationProvider;
    }

    public void setOverlayOpenAnimationProvider(AnimationProvider overlayOpenAnimationProvider) {
        this.overlayOpenAnimationProvider = overlayOpenAnimationProvider;
    }

    public AnimationProvider getOverlayCloseAnimationProvider() {
        return overlayCloseAnimationProvider;
    }

    public void setOverlayCloseAnimationProvider(AnimationProvider overlayCloseAnimationProvider) {
        this.overlayCloseAnimationProvider = overlayCloseAnimationProvider;
    }

    private void updateClosedTranslation() {
        View container = getMenuContainer();
        closedTranslation = (container != null) ? container.getHeight() : 0;

        if (!isOpened() && menuRoot != null) {
            applyClosedTranslation();
        }

        rebuildAnimation(getContainerOpenAnimationProvider());
        rebuildAnimation(getContainerCloseAnimationProvider());
    }

    private void rebuildAnimation(AnimationProvider animationProvider) {
        if (animationProvider instanceof BottomMenuAnimationProvider) {
            ((BottomMenuAnimationProvider) animationProvider).rebuildAnimation(closedTranslation);
        }
    }

    private void applyClosedTranslation() {
        menuRoot.setTranslationY(closedTranslation);
    }

}
