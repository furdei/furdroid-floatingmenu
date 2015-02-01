package com.furdei.furdroid.floatingmenu.layout;

import com.furdei.furdroid.floatingmenu.MenuLayoutManager;

/**
 * This class only provides support for theming methods.
 *
 * @author Stepan Furdey
 */
public abstract class BaseMenuLayoutManager implements MenuLayoutManager {

    private int colorPrimary;
    private int colorPrimaryDark;
    private int colorAccent;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColorPrimaryDark(int colorPrimaryDark) {
        this.colorPrimaryDark = colorPrimaryDark;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColorAccent(int colorAccent) {
        this.colorAccent = colorAccent;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public int getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    public int getColorAccent() {
        return colorAccent;
    }

}
