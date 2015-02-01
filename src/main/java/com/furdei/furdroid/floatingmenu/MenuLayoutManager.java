package com.furdei.furdroid.floatingmenu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Implementations of this interface are responsible for placing menu items into menu container.
 *
 * @author Stepan Furdey
 */
public interface MenuLayoutManager {

    /**
     * Create a view for a new menu item. If a menuItem has submenu then created view will be a
     * parent view for each submenu item.
     *
     * @param menuContainer - container view for menu items
     * @param menuItem - a new menu item
     * @return a view for a new menu item
     */
    public View newMenuItem(ViewGroup menuContainer, MenuItem menuItem);

    /**
     * Layout a menu item view into menu container.
     *
     * @param menuContainer - container view for menu items. If menuItem is a submenu item then
     *                      menuContainer is a view of it's parent menu item.
     * @param menuItem - a new menu item
     * @param menuItemView - a view for a new menu item
     */
    public void addMenuItem(ViewGroup menuContainer, MenuItem menuItem, View menuItemView);

    /**
     * Specify a primary color for menu. Used to tint menu icons
     *
     * @param colorPrimary
     */
    public void setColorPrimary(int colorPrimary);

    /**
     * Specify a darker shade for primary color. Used to tint menu icons in 'pressed' state
     *
     * @param colorPrimaryDark
     */
    public void setColorPrimaryDark(int colorPrimaryDark);

    /**
     * Specify an accent color for menu. Used to tint menu icons in 'focused' state and also
     * for ripple effect
     *
     * @param colorAccent
     */
    public void setColorAccent(int colorAccent);

}
