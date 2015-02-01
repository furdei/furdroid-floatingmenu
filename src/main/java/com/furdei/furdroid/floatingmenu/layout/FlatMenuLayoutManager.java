package com.furdei.furdroid.floatingmenu.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dreamers.graphics.RippleDrawable;

import com.furdei.furdroid.floatingmenu.R;
import com.furdei.furdroid.components.graphics.drawable.TintedDrawable;
import com.furdei.furdroid.floatingmenu.internal.PaletteUtils;

/**
 * This implementation only adds new item views to a container. You can specify a custom layout
 * through the constructor or use a default layout.
 *
 * @author Stepan Furdey
 */
public class FlatMenuLayoutManager extends BaseMenuLayoutManager {

    private final Context context;
    private final int layoutResId;

    public FlatMenuLayoutManager(Context context) {
        this(context, R.layout.simple_vertical_menu_item);
    }

    public FlatMenuLayoutManager(Context context, int layoutResId) {
        this.context = context;
        this.layoutResId = layoutResId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View newMenuItem(ViewGroup menuContainer, MenuItem menuItem) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(layoutResId, menuContainer, false);
        TextView title = (TextView) item.findViewById(R.id.floating_menu_item_text);

        if (title != null) {
            title.setText(menuItem.getTitle());
        }

        Drawable background = item.getBackground();

        if (background != null) {
            ColorStateList colorStateList = PaletteUtils.getIconColorState(
                    getColorPrimary(), getColorPrimaryDark(), getColorAccent());
            item.setBackgroundDrawable(new TintedDrawable(background, colorStateList));
            RippleDrawable.makeFor(item, PaletteUtils.getRippleColorState(getColorAccent()));
        }

        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMenuItem(ViewGroup menuContainer, MenuItem menuItem, View menuItemView) {
        menuContainer.addView(menuItemView);
    }

}
