package com.furdei.furdroid.floatingmenu.layout;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Menu layout manager that provides logic for creating menu groups or submenu items.
 *
 * @author Stepan Furdey
 */
public abstract class HierarchicalMenuLayoutManager extends BaseMenuLayoutManager {

    @Override
    public final View newMenuItem(ViewGroup menuContainer, MenuItem menuItem) {
        return menuItem.hasSubMenu() ?
                newMenuGroup(menuContainer, menuItem) : newSubMenuItem(menuContainer, menuItem);
    }

    @Override
    public final void addMenuItem(ViewGroup menuContainer, MenuItem menuItem, View menuItemView) {
        if (menuItem.hasSubMenu()) {
            addMenuGroup(menuContainer, menuItem, menuItemView);
        } else {
            addSubMenuItem(menuContainer, menuItem, menuItemView);
        }
    }

    /**
     * Create a view for a new menu group. Created view will be a parent view for each submenu item.
     *
     * @param menuContainer - container view for menu group
     * @param menuItem - a new menu group
     * @return a view for a new menu group
     */
    protected abstract View newMenuGroup(ViewGroup menuContainer, MenuItem menuItem);

    /**
     * Create a view for a new menu group item.
     *
     * @param menuContainer - container view for menu item
     * @param menuItem - a new menu item
     * @return a view for a new menu item
     */
    protected abstract View newSubMenuItem(ViewGroup menuContainer, MenuItem menuItem);

    /**
     * Layout a menu group view into menu container.
     *
     * @param menuContainer - container view for menu group. This is a view of a menu group.
     * @param menuItem - a new menu group
     * @param menuItemView - a view for a new menu group
     */
    protected abstract void addMenuGroup(ViewGroup menuContainer, MenuItem menuItem, View menuItemView);

    /**
     * Layout a menu item view into menu container.
     *
     * @param menuContainer - container view for menu items. If menuItem is a submenu item then
     *
     * @param menuItem - a new menu item
     * @param menuItemView - a view for a new menu item
     */
    protected abstract void addSubMenuItem(ViewGroup menuContainer, MenuItem menuItem, View menuItemView);

}
