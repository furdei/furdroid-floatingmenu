package com.furdei.furdroid.floatingmenu.bottom;

import android.app.Activity;

import com.furdei.furdroid.floatingmenu.base.BaseFloatingMenuControllerFactory;
import com.furdei.furdroid.floatingmenu.FloatingMenuController;

/**
 * This
 * {@link com.furdei.furdroid.floatingmenu.FloatingMenuController FloatingMenuController} factory
 * creates
 * {@link com.furdei.furdroid.floatingmenu.bottom.BottomFloatingMenuController
 * BottomFloatingMenuController} instances. This is default out-of-the-box implementation.
 *
 * @author Stepan Furdey
 */
public class BottomFloatingMenuControllerFactory extends BaseFloatingMenuControllerFactory {

    public BottomFloatingMenuControllerFactory(Activity activity) {
        super(activity);
    }

    @Override
    public FloatingMenuController createFloatingMenuControllerInstance() {
        return new BottomFloatingMenuController(getActivity());
    }
}
