package com.furdei.furdroid.floatingmenu.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.furdei.furdroid.floatingmenu.AnimationProvider;
import com.furdei.furdroid.floatingmenu.FloatingMenuController;
import com.furdei.furdroid.floatingmenu.R;
import com.furdei.furdroid.floatingmenu.internal.MenuAnchorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic out-of-the-box implementation of
 * {@link com.furdei.furdroid.floatingmenu.FloatingMenuController FloatingMenuController}.
 * Provides control for open/close menu button clicks, menu items clicks,
 * running open/close animation.
 *
 * @author Stepan Furdey
 */
public class BaseFloatingMenuController implements FloatingMenuController {

    private Activity activity;
    private View openCloseButton;
    private ViewGroup menuContainer;
    private AnimationProvider containerOpenAnimationProvider;
    private AnimationProvider containerCloseAnimationProvider;
    private AnimationProvider buttonOpenAnimationProvider;
    private AnimationProvider buttonCloseAnimationProvider;
    private boolean isOpened;
    private List<FloatingMenuListener> listeners;
    private Drawable closedButtonDrawable;
    private Drawable openedButtonDrawable;

    public static class MenuSavedState extends View.BaseSavedState {

        private boolean isOpened;

        public MenuSavedState(Parcelable superState) {
            super(superState);
        }

        private MenuSavedState(Parcel source) {
            super(source);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(isOpened ? 1 : 0);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<MenuSavedState> CREATOR =
                new Parcelable.Creator<MenuSavedState>() {
                    public MenuSavedState createFromParcel(Parcel in) {
                        return new MenuSavedState(in);
                    }

                    public MenuSavedState[] newArray(int size) {
                        return new MenuSavedState[size];
                    }
                };
    }

    private final View.OnClickListener openCloseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleAnimated();
        }
    };

    private final Animation.AnimationListener openAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            openInternal();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            notifyAfterOpened();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    private final Animation.AnimationListener closeAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            notifyBeforeClosed();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            closeInternal();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    private final View.OnClickListener menuItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MenuItem item = (MenuItem) v.getTag(R.integer.menuItemTag);
            onMenuItemClicked(item);
        }
    };

    private final MenuAnchorView.StateChangeListener stateChangeListener =
            new MenuAnchorView.StateChangeListener() {

                @Override
                public Parcelable onSaveInstanceState(Parcelable state) {
                    return BaseFloatingMenuController.this.onSaveInstanceState(state);
                }

                @Override
                public void onRestoreInstanceState(Parcelable state) {
                    if (state instanceof MenuSavedState) {
                        BaseFloatingMenuController.this.onRestoreInstanceState(state);
                    }
                }
            };

    public BaseFloatingMenuController(Activity activity) {
        this.activity = activity;
        this.isOpened = false;
        this.listeners = new ArrayList<FloatingMenuListener>(2);

        MenuAnchorView anchorView = new MenuAnchorView(activity);
        anchorView.setStateChangeListener(stateChangeListener);
        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
        content.addView(anchorView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getOpenCloseButton() {
        return openCloseButton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpenCloseButton(View openCloseButton) {
        this.openCloseButton = openCloseButton;
        this.openCloseButton.setOnClickListener(openCloseListener);
        this.openCloseButton.setTag(R.integer.menuItemTag, this);
        setClosedButtonDrawable(null);
        setOpenedButtonDrawable(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewGroup getMenuContainer() {
        return menuContainer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMenuContainer(ViewGroup menuContainer) {
        this.menuContainer = menuContainer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationProvider getContainerOpenAnimationProvider() {
        return containerOpenAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContainerOpenAnimationProvider(AnimationProvider openAnimationProvider) {
        this.containerOpenAnimationProvider = openAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationProvider getContainerCloseAnimationProvider() {
        return containerCloseAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContainerCloseAnimationProvider(AnimationProvider closeAnimationProvider) {
        this.containerCloseAnimationProvider = closeAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationProvider getButtonOpenAnimationProvider() {
        return buttonOpenAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setButtonOpenAnimationProvider(AnimationProvider openAnimationProvider) {
        buttonOpenAnimationProvider = openAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnimationProvider getButtonCloseAnimationProvider() {
        return buttonCloseAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setButtonCloseAnimationProvider(AnimationProvider closeAnimationProvider) {
        buttonCloseAnimationProvider = closeAnimationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeMenuItem(MenuItem menuItem, View menuItemView) {
        menuItemView.setTag(R.integer.menuItemTag, menuItem);
        menuItemView.setOnClickListener(menuItemClickListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpened() {
        return isOpened;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openAnimated() {
        if (containerOpenAnimationProvider != null) {
            Animation containerAnimation = containerOpenAnimationProvider.get();

            if (containerAnimation != null) {
                containerAnimation.setAnimationListener(openAnimationListener);
                getOpenCloseAnimationView().startAnimation(containerAnimation);
            } else {
                open();
            }

            if (buttonOpenAnimationProvider != null) {
                Animation buttonAnimation = buttonOpenAnimationProvider.get();

                if (buttonAnimation != null) {
                    getOpenCloseButton().startAnimation(buttonAnimation);
                }
            }
        } else {
            open();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeAnimated() {
        if (containerCloseAnimationProvider != null) {
            Animation containerAnimation = containerCloseAnimationProvider.get();

            if (containerAnimation != null) {
                containerAnimation.setAnimationListener(closeAnimationListener);
                getOpenCloseAnimationView().startAnimation(containerAnimation);
            } else {
                close();
            }

            if (buttonCloseAnimationProvider != null) {
                Animation buttonAnimation = buttonCloseAnimationProvider.get();

                if (buttonAnimation != null) {
                    getOpenCloseButton().startAnimation(buttonAnimation);
                }
            }
        } else {
            close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleAnimated() {
        if (isOpened) {
            closeAnimated();
        } else {
            openAnimated();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        openInternal();
        notifyAfterOpened();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        notifyBeforeClosed();
        closeInternal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggle() {
        if (isOpened) {
            close();
        } else {
            open();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFloatingMenuListener(FloatingMenuListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFloatingMenuListener(FloatingMenuListener listener) {
        listeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllFloatingMenuListeners() {
        listeners.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getClosedButtonDrawable() {
        return closedButtonDrawable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClosedButtonDrawable(Drawable closedButtonDrawable) {
        this.closedButtonDrawable = closedButtonDrawable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getOpenedButtonDrawable() {
        return openedButtonDrawable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpenedButtonDrawable(Drawable openedButtonDrawable) {
        this.openedButtonDrawable = openedButtonDrawable;
    }

    public Activity getActivity() {
        return activity;
    }

    /**
     * Here goes some code to show menu view. It can be overridden by children.
     */
    protected void doOpen() {
        menuContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Here goes some code to hide menu view. It can be overridden by children.
     */
    protected void doClose() {
        menuContainer.setVisibility(View.GONE);
    }

    /**
     * Specifies a view to apply animation to while menu is being opened/closed.
     *
     * @return a view to apply animation to
     */
    protected View getOpenCloseAnimationView() {
        return menuContainer;
    }

    /**
     * Called just before a menu is going to become visible. It's still hidden when this
     * event goes off.
     */
    protected void onBeforeOpened() {}

    /**
     * Called right after a menu is fully opened, e.g. open animation has been finished or
     * menu has been opened without any animation.
     */
    protected void onAfterOpened() {
        if (openedButtonDrawable != null && openCloseButton instanceof ImageView) {
            ImageView imageButton = (ImageView) openCloseButton;
            imageButton.setImageDrawable(openedButtonDrawable);
        }
    }

    /**
     * Called just before a menu is going to start closing. It's still visible and holds it
     * usual fully opened position. Close animation will start after this method is finished.
     */
    protected void onBeforeClosed() {}

    /**
     * Called right after a menu is fully closed, e.g. close animation has been finished or
     * menu has been closed without any animation. A menu object has been hidden by the moment
     * this method gets called.
     */
    protected void onAfterClosed() {
        if (closedButtonDrawable != null && openCloseButton instanceof ImageView) {
            ImageView imageButton = (ImageView) openCloseButton;
            imageButton.setImageDrawable(closedButtonDrawable);
        }
    }

    private void onMenuItemClicked(MenuItem item) {
        activity.onOptionsItemSelected(item);
    }

    private void openInternal() {
        notifyBeforeOpened();
        doOpen();
        isOpened = true;
    }

    private void closeInternal() {
        doClose();
        isOpened = false;
        notifyAfterClosed();
    }

    private void notifyBeforeOpened() {
        onBeforeOpened();
        int listenersCount = listeners.size();

        for (int i = 0; i < listenersCount; i++) {
            FloatingMenuListener listener = listeners.get(i);
            listener.onBeforeOpened(this);
        }
    }

    private void notifyAfterOpened() {
        onAfterOpened();
        int listenersCount = listeners.size();

        for (int i = 0; i < listenersCount; i++) {
            FloatingMenuListener listener = listeners.get(i);
            listener.onAfterOpened(this);
        }
    }

    private void notifyBeforeClosed() {
        onBeforeClosed();

        for (int i = listeners.size() - 1; i >= 0; i--) {
            FloatingMenuListener listener = listeners.get(i);
            listener.onBeforeClosed(this);
        }
    }

    private void notifyAfterClosed() {
        onAfterClosed();

        for (int i = listeners.size() - 1; i >= 0; i--) {
            FloatingMenuListener listener = listeners.get(i);
            listener.onAfterClosed(this);
        }
    }

    private Parcelable onSaveInstanceState(Parcelable state) {
        MenuSavedState menuSavedState = new MenuSavedState(state);
        menuSavedState.isOpened = isOpened;
        return menuSavedState;
    }

    private void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof MenuSavedState)) {
            throw new IllegalStateException("Parcelable state has to be an instance of MenuSavedState");
        }

        MenuSavedState menuSavedState = (MenuSavedState) state;
        if (menuSavedState.isOpened && !isOpened) {
            open();
        } else if (!menuSavedState.isOpened && isOpened) {
            close();
        }
    }

}
