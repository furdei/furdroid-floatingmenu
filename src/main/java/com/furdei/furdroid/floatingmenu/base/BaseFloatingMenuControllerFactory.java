package com.furdei.furdroid.floatingmenu.base;

import android.app.Activity;

import com.furdei.furdroid.floatingmenu.FloatingMenuController;
import com.furdei.furdroid.floatingmenu.FloatingMenuControllerFactory;

/**
 * This
 * {@link com.furdei.furdroid.floatingmenu.FloatingMenuController FloatingMenuController} factory
 * creates
 * {@link com.furdei.furdroid.floatingmenu.base.BaseFloatingMenuController BaseFloatingMenuController}
 * instances. This is default out-of-the-box implementation.
 *
 * @author Stepan Furdey
 */
public class BaseFloatingMenuControllerFactory implements FloatingMenuControllerFactory {

    private Activity activity;

    public BaseFloatingMenuControllerFactory(Activity activity) {
        this.activity = activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatingMenuController createFloatingMenuControllerInstance() {
        return new BaseFloatingMenuController(activity);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
