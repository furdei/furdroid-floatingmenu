package com.furdei.furdroid.floatingmenu.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dreamers.graphics.RippleDrawable;

import com.furdei.furdroid.floatingmenu.R;
import com.furdei.furdroid.components.graphics.drawable.TintedDrawable;
import com.furdei.furdroid.floatingmenu.internal.PaletteUtils;

/**
 * This implementation only adds new item views to a container. You can specify custom layouts
 * for menu groups and menu items through the constructor or use default layouts.

 * @author Stepan Furdey
 */
public class HierarchicalContainerMenuLayoutManager extends HierarchicalMenuLayoutManager {

    private final Context context;
    private final int itemLayoutResId;
    private final int groupLayoutResId;

    public HierarchicalContainerMenuLayoutManager(Context context) {
        this(context, R.layout.simple_horizontal_menu_item, R.layout.simple_horizontal_menu_group);
    }

    public HierarchicalContainerMenuLayoutManager(Context context, int itemLayoutResId,
                                                  int groupLayoutResId) {
        this.context = context;
        this.itemLayoutResId = itemLayoutResId;
        this.groupLayoutResId = groupLayoutResId;
    }

    @Override
    protected View newMenuGroup(ViewGroup menuContainer, MenuItem menuItem) {
       return inflateTextMenuItem(groupLayoutResId, menuContainer, menuItem.getTitle());
    }

    @Override
    protected View newSubMenuItem(ViewGroup menuContainer, MenuItem menuItem) {
        View item = inflateTextMenuItem(itemLayoutResId, menuContainer, menuItem.getTitle());
        ImageView icon = (ImageView) item.findViewById(R.id.floating_menu_item_icon);

        if (icon != null) {
            Drawable drawable = menuItem.getIcon();

            if (drawable != null) {
                drawable = new TintedDrawable(drawable, PaletteUtils.getIconColorState(
                        getColorPrimary(), getColorPrimaryDark(), getColorAccent()));
                icon.setImageDrawable(drawable);
            }
        }

        RippleDrawable.makeFor(item, PaletteUtils.getRippleColorState(getColorAccent()));

        return item;
    }

    @Override
    protected void addMenuGroup(ViewGroup menuContainer, MenuItem menuItem, View menuItemView) {
        menuContainer.addView(menuItemView);
    }

    @Override
    protected void addSubMenuItem(ViewGroup menuContainer, MenuItem menuItem, View menuItemView) {
        ViewGroup childrenContainer = (ViewGroup) menuContainer.findViewById(R.id.floating_sub_menu);

        if (childrenContainer != null) {
            childrenContainer.addView(menuItemView);
        } else {
            menuContainer.addView(menuItemView);
        }
    }

    private View inflateTextMenuItem(int resId, ViewGroup container, CharSequence textHeader) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(resId, container, false);
        TextView title = (TextView) item.findViewById(R.id.floating_menu_item_text);

        if (title != null && textHeader != null) {
            title.setText(textHeader);
        }

        return item;
    }

}
