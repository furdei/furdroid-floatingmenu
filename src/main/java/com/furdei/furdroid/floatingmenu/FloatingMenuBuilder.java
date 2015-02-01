package com.furdei.furdroid.floatingmenu;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.furdei.furdroid.floatingmenu.animation.ResourceAnimationProvider;
import com.furdei.furdroid.floatingmenu.base.BaseFloatingMenuControllerFactory;
import com.furdei.furdroid.components.graphics.drawable.TintedDrawable;
import com.furdei.furdroid.floatingmenu.internal.PaletteUtils;
import com.furdei.furdroid.floatingmenu.layout.FlatMenuLayoutManager;

/**
 * <code>FloatingMenuBuilder</code> helps you create a floating menu. You can customize any
 * parameter you want or leave it with default values. Example use case: create a menu,
 * assign an open/close button, specify a container and provide a menu resource, open and close
 * animations.
 * <p>
 * <h3>Building up a menu</h3>
 * <p>
 * Use {@link #setMenuResId(int)} method to provide menu resource. Menu builder uses this resource
 * to inflate menu content dynamically.
 * </p>
 * Sample code:
 * <pre>
 * {@code
 * FloatingMenuBuilder menuBuilder = new FloatingMenuBuilder(this)
 *      .setOpenCloseButton(R.id.quick_menu_button)
 *      .setMenuContainer(R.id.quick_menu_container)
 *      .setMenuResId(R.menu.navigation_toolbar)
 *      .setOpenAnimation(R.anim.open_scale_from_bottom)
 *      .setCloseAnimation(R.anim.close_scale_to_bottom);
 * FloatingMenuController floatingMenuController = menuBuilder.build();
 * }
 * </pre>
 * <h3>Menu resources</h3>
 * <p>
 * Menu resource can be either hierarchical or a flat one. Hierarchical menu means that you can
 * combine menu items into groups and specify separate layouts for groups and items inside groups.
 * You can also use group titles inside a group layout.
 * {@link com.furdei.furdroid.floatingmenu.layout.HierarchicalContainerMenuLayoutManager
 * HierarchicalContainerMenuLayoutManager}
 * is responsible for laying out inflated menu items using groups or single items layouts.
 * Both {@link MenuLayoutManager MenuLayoutManager} and layout
 * resources for {@link com.furdei.furdroid.floatingmenu.layout.HierarchicalContainerMenuLayoutManager
 * HierarchicalContainerMenuLayoutManager}
 * implementation are fully customizable.
 * </p>
 * Hierarchical menu resource example:
 * <pre>
 * {@code
 * <menu xmlns:android="http://schemas.android.com/apk/res/android">
 *      <item android:title="@string/menu_group_1_title" >
 *          <menu>
 *              <item android:id="@+id/menu_item_1"
 *                  android:icon="@drawable/ic_action_1"
 *                  android:title="@string/menu_item_1_title" />
 *
 *              <item android:id="@+id/menu_item_2"
 *                  android:icon="@drawable/ic_action_2"
 *                  android:title="@string/menu_item_2_title" />
 *          </menu>
 *      </item>
 *
 *      <item android:title="@string/menu_group_2_title" >
 *          <menu>
 *              <item android:id="@+id/menu_item_3"
 *                  android:icon="@drawable/ic_action_3"
 *                  android:title="@string/menu_item_3_title" />
 *
 *              <item android:id="@+id/menu_item_4"
 *                  android:icon="@drawable/ic_action_4"
 *                  android:title="@string/menu_item_4_title" />
 *          </menu>
 *      </item>
 *  </menu>
 * }
 * </pre>
 * <p>
 * Flat menu layout does not have any sub menus.
 * {@link com.furdei.furdroid.floatingmenu.layout.FlatMenuLayoutManager FlatMenuLayoutManager}
 * manages layout of a flat menu. You can specify a layout to be used by
 * {@link com.furdei.furdroid.floatingmenu.layout.FlatMenuLayoutManager FlatMenuLayoutManager}
 * through a constructor argument.
 * </p>
 * Flat menu resource example:
 * <pre>
 * {@code
 * <menu xmlns:android="http://schemas.android.com/apk/res/android">
 *       <item android:id="@+id/menu_item_1"
 *          android:icon="@drawable/ic_action_1"
 *          android:title="@string/menu_item_1_title" />
 *
 *      <item android:id="@+id/menu_item_2"
 *          android:icon="@drawable/ic_action_2"
 *          android:title="@string/menu_item_2_title" />
 *  </menu>
 * }
 * </pre>
 * <h3>Changing menu states</h3>
 * <p>
 * {@link #build()} method returns a
 * {@link FloatingMenuController FloatingMenuController}
 * instance. You can use it to open/close menu programmatically by calling
 * {@link FloatingMenuController#open() open},
 * {@link FloatingMenuController#close() close},
 * {@link FloatingMenuController#toggle() toggle},
 * {@link FloatingMenuController#openAnimated()
 * openAnimated},
 * {@link FloatingMenuController#closeAnimated()
 * closeAnimated},
 * {@link FloatingMenuController#toggleAnimated()
 * toggleAnimated} methods.
 * </p>
 * <h3>Processing menu events</h3>
 * <p>
 * Menu item clicks are dispatched to the current activity's
 * {@link android.app.Activity#onOptionsItemSelected(android.view.MenuItem) onOptionsItemSelected}
 * method. You should process them the same way you process event from an Action Bar, for example:
 * </p>
 * <pre>
 * {@code
 *  public boolean onOptionsItemSelected(MenuItem item) {
 *      switch (item.getItemId()) {
 *      case R.id.menu_item_1:
 *          // ... some processing here...
 *          return true;
 *
 *      case R.id.menu_item_2:
 *          // ... some processing here...
 *          return true;
 *
 *      case R.id.menu_item_3:
 *          // ... some processing here...
 *          return true;
 *
 *      case R.id.menu_item_4:
 *          // ... some processing here...
 *          return true;
 *      }
 *
 *      return super.onOptionsItemSelected(item);
 *  }
 * }
 * </pre>
 *
 * @author Stepan Furdey
 */
public class FloatingMenuBuilder {

    private final Activity activity;
    private int menuResId;
    private MenuLayoutManager menuLayoutManager;
    private FloatingMenuControllerFactory controllerFactory;
    private View openCloseButton;
    private ViewGroup menuContainer;
    private AnimationProvider openAnimationProvider;
    private AnimationProvider closeAnimationProvider;
    private AnimationProvider buttonOpenAnimationProvider;
    private AnimationProvider buttonCloseAnimationProvider;
    private FloatingMenuController controller;
    private Drawable closedButtonDrawable;
    private Drawable openedButtonDrawable;

    // color palette
    private int primaryColorDark;
    private int primaryColorHandle;
    private int primaryColor;
    private int accentColor;

    private static final int MENU_UNDEFINED = -1;

    public FloatingMenuBuilder(Activity activity) {
        this.activity = activity;
        this.menuResId = MENU_UNDEFINED;
        this.menuLayoutManager = new FlatMenuLayoutManager(activity);
        this.controllerFactory = new BaseFloatingMenuControllerFactory(activity);

        activity.getTheme().applyStyle(R.style.Theme_FloatingMenu, false);
        TypedArray attrs = getActivity().getTheme().obtainStyledAttributes(null, R.styleable.FloatingMenu,
                R.style.Theme_FloatingMenu, 0);
        primaryColorDark = attrs.getColor(
                R.styleable.FloatingMenu_floatingMenuColorPrimaryDark, 0);
        primaryColorHandle = attrs.getColor(
                R.styleable.FloatingMenu_floatingMenuColorPrimaryHandle, 0);
        primaryColor = attrs.getColor(R.styleable.FloatingMenu_floatingMenuColorPrimary, 0);
        accentColor = attrs.getColor(R.styleable.FloatingMenu_floatingMenuColorAccent, 0);
        attrs.recycle();
    }

    /**
     * Returns an identifier of the resource which is used to inflate a menu
     *
     * @return menu resource identifier
     */
    public int getMenuResId() {
        return menuResId;
    }

    /**
     * Specifies an identifier of the resource which is used to inflate a menu
     *
     * @param menuResId menu resource identifier
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setMenuResId(int menuResId) {
        this.menuResId = menuResId;
        return this;
    }

    /**
     * Returns an instance of {@link MenuLayoutManager
     * MenuLayoutManager} used to build menu view hierarchy.
     *
     * @return {@link MenuLayoutManager
     *          MenuLayoutManager} instance.
     */
    public MenuLayoutManager getMenuLayoutManager() {
        return menuLayoutManager;
    }

    /**
     * Specifies an instance of {@link MenuLayoutManager
     * MenuLayoutManager} used to build menu view hierarchy.
     *
     * @param menuLayoutManager {@link MenuLayoutManager
     *          MenuLayoutManager} instance.
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setMenuLayoutManager(MenuLayoutManager menuLayoutManager) {
        this.menuLayoutManager = menuLayoutManager;
        return this;
    }

    /**
     * Primary dark color of a menu. Used to tint menu icons in pressed state
     */
    public int getPrimaryColorDark() {
        return primaryColorDark;
    }

    /**
     * Primary dark color of a menu. Used to tint menu icons in pressed state
     */
    public void setPrimaryColorDark(int primaryColorDark) {
        this.primaryColorDark = primaryColorDark;
        resetOpenCloseButtonDrawable();
    }

    /**
     * Primary color of a menu. Used to tint Open/Close menu button in a normal state
     */
    public int getPrimaryColorHandle() {
        return primaryColorHandle;
    }

    /**
     * Primary color of a menu. Used to tint Open/Close menu button in a normal state
     */
    public void setPrimaryColorHandle(int primaryColorHandle) {
        this.primaryColorHandle = primaryColorHandle;
        resetOpenCloseButtonDrawable();
    }

    /**
     * Primary color of a menu. Used to tint menu icons in a normal state
     */
    public int getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Primary color of a menu. Used to tint menu icons in a normal state
     */
    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    /**
     * Accent color of a menu. Used for ripple effect and to tint icons in focused state
     */
    public int getAccentColor() {
        return accentColor;
    }

    /**
     * Accent color of a menu. Used for ripple effect and to tint icons in focused state
     */
    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
        resetOpenCloseButtonDrawable();
    }

    /**
     * Returns a button used to open or close a menu
     *
     * @return Open/close button view
     */
    public View getOpenCloseButton() {
        return openCloseButton;
    }

    /**
     * <p>
     * Specifies a button used to open or close a menu. A call to this method resets button
     * drawables specified by {@link #setClosedButtonDrawable(android.graphics.drawable.Drawable)}
     * and {@link #setOpenedButtonDrawable(android.graphics.drawable.Drawable)} methods to
     * <code>null</code>.
     * </p><p>
     * This method replaces <code>openCloseButton</code>'s
     * {@link android.view.View.OnClickListener onClickListener} to respond to it's 'click' events.
     * Don't set your own click listener for the button.
     * </p><p>
     * Also, <code>setOpenCloseButton</code> method changes <code>openCloseButton</code>'s
     * background to enable state-based color tinting. Your original background drawable will
     * be tinted with colors specified by these theme attributes:
     * <ul>
     *     <li><code>floatingMenuColorPrimaryDark</code> in a {@link android.R.attr#state_pressed
     *     state_pressed} state</li>
     *     <li><code>floatingMenuColorAccent</code> in a {@link android.R.attr#state_focused
     *     state_focused} state</li>
     *     <li><code>floatingMenuColorPrimary</code> in a normal state and any other states</li>
     * </ul>
     * </p><p>
     * You can specify any color for these theme attributes in your custom theme. By default they
     * are initialized with primary
     * <a href="https://developer.android.com/training/material/theme.html">Material</a> colors:
     * <code>android:colorPrimary</code> for <code>floatingMenuColorPrimary</code>,
     * <code>android:colorPrimaryDark</code> for <code>floatingMenuColorPrimaryDark</code> and
     * <code>android:colorAccent</code> for <code>floatingMenuColorAccent</code>.
     * </p><p>
     * You can change basic material colors in your theme to affect both floating menu and other
     * controls or change just floating menu color attributes in your theme. For example, this
     * theme overrides menu colors with orange color palette leaving other controls' color untouched:
     * <pre>
     * {@code
     * <style name="VegaNetBase" parent="Theme.AppCompat.Light.NoActionBar">
     *     <item name="floatingMenuColorPrimary">#ffffbb33</item>
     *     <item name="floatingMenuColorPrimaryDark">#ffff8800</item>
     *     <item name="floatingMenuColorAccent">#ffff8800</item>
     *     <!-- other styles go here -->
     *     <!-- ... -->
     * </style>
     * }
     * </pre>
     * </p><p>
     * And this will affect both menu's and other controls' color:
     * <pre>
     * {@code
     * <style name="VegaNetBase" parent="Theme.AppCompat.Light.NoActionBar">
     *     <item name="colorPrimary">#ffffbb33</item>
     *     <item name="colorPrimaryDark">#ffff8800</item>
     *     <item name="colorAccent">#ffff8800</item>
     *     <!-- other styles go here -->
     *     <!-- ... -->
     * </style>
     * }
     * </pre>
     * </p>
     *
     * @param openCloseButton - any clickable view that will be used as an open/close menu button.
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     * @see <a href="https://developer.android.com/training/material/theme.html">Using the
     * Material Theme</a>
     */
    public FloatingMenuBuilder setOpenCloseButton(View openCloseButton) {
        this.openCloseButton = openCloseButton;
        resetOpenCloseButtonDrawable();
        setOpenedButtonDrawable(null);
        setClosedButtonDrawable(null);
        return this;
    }

    /**
     * Specifies a button used to open or close a menu. See
     * {@link #setOpenCloseButton(android.view.View)} for further details.
     *
     * @param openCloseButtonResId - ID of any clickable view that will be used as an open/close
     *                             menu button.
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     * @see #setOpenCloseButton(android.view.View)
     */
    public FloatingMenuBuilder setOpenCloseButton(int openCloseButtonResId) {
        return setOpenCloseButton(activity.findViewById(openCloseButtonResId));
    }

    /**
     * Returns a menu container - a root view that holds all visible menu child views.
     *
     * @return a menu container view
     */
    public ViewGroup getMenuContainer() {
        return menuContainer;
    }

    /**
     * <p>
     * Specifies a container to host a menu. You should place it yourself into your fragment
     * or activity. There is no limitation on where to put this container.
     * {@link FloatingMenuController} will only take
     * control over <code>menuContainer</code>'s visibility and start show/hide animation.
     * </p><p>
     * <code>menuContainer</code> will be passed to
     * {@link MenuLayoutManager MenuLayoutManager}'s methods
     * as a <code>menuContainer</code> parameter.
     * {@link MenuLayoutManager MenuLayoutManager} will put
     * all menu view into this container.
     * </p>
     * @param menuContainer a container view for menu items
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setMenuContainer(ViewGroup menuContainer) {
        this.menuContainer = menuContainer;
        return this;
    }

    /**
     * Specifies a container to host a menu. See {@link #setMenuContainer(android.view.ViewGroup)}
     * for further details.
     *
     * @param menuContainerResId
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setMenuContainer(int menuContainerResId) {
        return setMenuContainer((ViewGroup)activity.findViewById(menuContainerResId));
    }

    /**
     * Returns an 'open menu' animation provider. Animation created by this provider will be
     * applied to a menu container specified by {@link #setMenuContainer(android.view.ViewGroup)}
     * method.
     *
     * @return 'open menu' animation provider or <code>null</code> if animation provider
     *          has not been set
     */
    public AnimationProvider getOpenAnimationProvider() {
        return openAnimationProvider;
    }

    /**
     * Specify an animation provider which creates animation to play when menu is going to be
     * shown. This animation will be applied to a menu container specified by
     * {@link #setMenuContainer(android.view.ViewGroup)} method.
     *
     * @param openAnimationProvider - animation provider object or <code>null</code>
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setOpenAnimationProvider(AnimationProvider openAnimationProvider) {
        this.openAnimationProvider = openAnimationProvider;
        return this;
    }

    /**
     * Specify an animation to play when menu is going to be shown. This animation will be
     * applied to a menu container specified by {@link #setMenuContainer(android.view.ViewGroup)}
     * method.
     *
     * @param openAnimationResId - animation resource ID
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setOpenAnimation(int openAnimationResId) {
        return setOpenAnimationProvider(
                new ResourceAnimationProvider(activity, openAnimationResId));
    }

    /**
     * Returns an 'close menu' animation provider. Animation created by this provider will be
     * applied to a menu container specified by {@link #setMenuContainer(android.view.ViewGroup)}
     * method.
     *
     * @return 'close menu' animation provider or <code>null</code> if animation provider
     *          has not been set
     */
    public AnimationProvider getCloseAnimationProvider() {
        return closeAnimationProvider;
    }

    /**
     * Specify an animation provider which creates animation to play when menu is going to be
     * hidden. This animation will be applied to a menu container specified by
     * {@link #setMenuContainer(android.view.ViewGroup)} method.
     *
     * @param closeAnimationProvider - animation provider object or <code>null</code>
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setCloseAnimationProvider(AnimationProvider closeAnimationProvider) {
        this.closeAnimationProvider = closeAnimationProvider;
        return this;
    }

    /**
     * Specify an animation to play when menu is going to be hidden. This animation will be
     * applied to a menu container specified by {@link #setMenuContainer(android.view.ViewGroup)}
     * method.
     *
     * @param closeAnimationResId - animation resource ID
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setCloseAnimation(int closeAnimationResId) {
        return setCloseAnimationProvider(
                new ResourceAnimationProvider(activity, closeAnimationResId));
    }

    /**
     * Returns an 'open menu' animation provider. Animation created by this provider will be
     * applied to an 'open/close' button specified by
     * {@link #setOpenCloseButton(android.view.View)} method.
     *
     * @return 'open menu' animation provider or <code>null</code> if animation provider
     *          has not been set
     */
    public AnimationProvider getButtonOpenAnimationProvider() {
        return buttonOpenAnimationProvider;
    }

    /**
     * Specify an animation provider which creates animation to play when menu is going to be
     * shown. This animation will be applied to an 'open/close' button specified by
     * {@link #setOpenCloseButton(android.view.View)} method.
     *
     * @param openAnimationProvider - animation object or <code>null</code>
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setButtonOpenAnimationProvider(
            AnimationProvider openAnimationProvider) {
        buttonOpenAnimationProvider = openAnimationProvider;
        return this;
    }

    /**
     * Specify an animation to play when menu is going to be shown. This animation will be
     * applied to an 'open/close' button specified by {@link #setOpenCloseButton(android.view.View)}
     * method.
     *
     * @param openAnimationResId - animation resource ID
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setButtonOpenAnimation(int openAnimationResId) {
        return setButtonOpenAnimationProvider(new ResourceAnimationProvider(
                activity, openAnimationResId));
    }

    /**
     * Returns an 'close menu' animation provider. Animation created by this provider will be
     * applied to an 'open/close' button specified by
     * {@link #setOpenCloseButton(android.view.View)} method.
     *
     * @return 'close menu' animation provider or <code>null</code> if animation provider
     *          has not been set
     */
    public AnimationProvider getButtonCloseAnimation() {
        return buttonCloseAnimationProvider;
    }

    /**
     * Specify an animation provider which creates animation to play when menu is going to be
     * hidden. This animation will be applied to an 'open/close' button specified by
     * {@link #setOpenCloseButton(android.view.View)} method.
     *
     * @param closeAnimationProvider - animation provider object or <code>null</code>
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setButtonCloseAnimationProvider(
            AnimationProvider closeAnimationProvider) {
        buttonCloseAnimationProvider = closeAnimationProvider;
        return this;
    }

    /**
     * Specify an animation to play when menu is going to be hidden. This animation will be
     * applied to an 'open/close' button specified by {@link #setOpenCloseButton(android.view.View)}
     * method.
     *
     * @param closeAnimationResId - animation resource ID
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setButtonAnimationOnClose(int closeAnimationResId) {
        return setButtonCloseAnimationProvider(new ResourceAnimationProvider(
                activity, closeAnimationResId));
    }

    /**
     * Returns a factory used to instantiate menu controllers.
     *
     * @return {@link FloatingMenuControllerFactory
     *          FloatingMenuControllerFactory} instance.
     */
    public FloatingMenuControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    /**
     * Specifies a factory used to instantiate a menu controller.
     * {@link com.furdei.furdroid.floatingmenu.base.BaseFloatingMenuControllerFactory
     * BaseFloatingMenuControllerFactory} is used by default. You can set your own factory if you
     * want to use your implementation of
     * {@link FloatingMenuController
     * FloatingMenuController}.
     *
     * @param controllerFactory a new factory to instantiate a menu controller.
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setControllerFactory(FloatingMenuControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
        return this;
    }

    /**
     * Returns an activity to which menu is attached.
     *
     * @return {@link android.app.Activity Activity} specified in constructor
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Drawable that is shown above the Open/Close button when a menu is in it's 'closed' state
     */
    public Drawable getClosedButtonDrawable() {
        return closedButtonDrawable;
    }

    /**
     * Drawable that is shown above the Open/Close button when a menu is in it's 'closed' state
     *
     * @param closedButtonDrawable drawable for 'closed' state. Does not get tinted
     *
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setClosedButtonDrawable(Drawable closedButtonDrawable) {
        this.closedButtonDrawable = closedButtonDrawable;
        return this;
    }

    /**
     * Drawable that is shown above the Open/Close button when a menu is in it's 'closed' state
     *
     * @param closedButtonDrawableResId drawable resource for 'closed' state. Does not get tinted
     *
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setClosedButtonDrawable(int closedButtonDrawableResId) {
        return setClosedButtonDrawable(activity.getResources().getDrawable(closedButtonDrawableResId));
    }

    /**
     * Drawable that is shown above the Open/Close button when a menu is in it's 'opened' state
     */
    public Drawable getOpenedButtonDrawable() {
        return openedButtonDrawable;
    }

    /**
     * Drawable that is shown above the Open/Close button when a menu is in it's 'opened' state
     *
     * @param openedButtonDrawable drawable for 'opened' state. Does not get tinted
     *
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setOpenedButtonDrawable(Drawable openedButtonDrawable) {
        this.openedButtonDrawable = openedButtonDrawable;
        return this;
    }

    /**
     * Drawable that is shown above the Open/Close button when a menu is in it's 'opened' state
     *
     * @param openedButtonDrawableResId drawable resource for 'opened' state. Does not get tinted
     *
     * @return Link to the same {@link FloatingMenuBuilder} instance to chain calls
     */
    public FloatingMenuBuilder setOpenedButtonDrawable(int openedButtonDrawableResId) {
        return setOpenedButtonDrawable(activity.getResources().getDrawable(openedButtonDrawableResId));
    }

    /**
     * Builds a menu and returns a menu controller. All builder settings should be specified before
     * calling <code>build</code> method. There is no sense to do it afterwards. These methods are
     * required to be called before building a menu:
     * <ul>
     * <li>{@link #setMenuResId(int)}</li>
     * <li>{@link #setOpenCloseButton(android.view.View)}</li>
     * <li>{@link #setMenuContainer(android.view.ViewGroup)}</li>
     * </ul>
     * Menu layout manager and menu controller factory are set by default. You can change them
     * before calling <code>build</code> method but you should not provide a <code>null</code>
     * value for neither of them. All other settings are optional.
     *
     * @return {@link FloatingMenuController
     *              FloatingMenuController} which you can use to obtain and change menu state,
     *              e.g. open, close, toggle with or without animation.
     */
    public FloatingMenuController build() {
        if (menuResId == MENU_UNDEFINED) {
            throw new IllegalStateException("setMenuResId method has not been called");
        }

        if (menuLayoutManager == null) {
            throw new IllegalStateException("setMenuLayoutManager method has not been called");
        }

        if (openCloseButton == null) {
            throw new IllegalStateException("setOpenCloseButton method has not been called");
        }

        if (menuContainer == null) {
            throw new IllegalStateException("setMenuContainer method has not been called");
        }

        if (controllerFactory == null) {
            throw new IllegalStateException("setControllerFactory method has not been called");
        }

        menuLayoutManager.setColorPrimary(primaryColor);
        menuLayoutManager.setColorPrimaryDark(primaryColorDark);
        menuLayoutManager.setColorAccent(accentColor);

        controller = controllerFactory.createFloatingMenuControllerInstance();
        setUpMenuController(controller);

        PopupMenu popupMenu = new PopupMenu(activity, null);
        Menu menu = popupMenu.getMenu();
        MenuInflater inflater = new MenuInflater(activity);
        inflater.inflate(menuResId, menu);
        int itemsCount = menu.size();

        for (int i = 0; i < itemsCount; i++) {
            MenuItem item = menu.getItem(i);
            buildMenuItem(item, menuContainer);
        }

        return controller;
    }

    /**
     * This method can be overridden by children to do some additional controller setup. This
     * method is called while building a menu with {@link #build()} method.
     *
     * @param controller a menu controller to set up.
     */
    protected void setUpMenuController(FloatingMenuController controller) {
        controller.setOpenCloseButton(openCloseButton);
        controller.setOpenedButtonDrawable(openedButtonDrawable);
        controller.setClosedButtonDrawable(closedButtonDrawable);
        controller.setMenuContainer(menuContainer);
        controller.setContainerOpenAnimationProvider(openAnimationProvider);
        controller.setContainerCloseAnimationProvider(closeAnimationProvider);
        controller.setButtonOpenAnimationProvider(buttonOpenAnimationProvider);
        controller.setButtonCloseAnimationProvider(buttonCloseAnimationProvider);
    }

    /**
     * Called recursively to build a view for menu item and it's submenu hierarchy if provided
     *
     * @param menuItem - item to build
     * @param menuContainer - container for a menu item. If menuItem is a submenu item then the
     *                      container is a view of it's parent menu item.
     */
    private void buildMenuItem(MenuItem menuItem, ViewGroup menuContainer) {
        View itemView = menuLayoutManager.newMenuItem(menuContainer, menuItem);
        controller.initializeMenuItem(menuItem, itemView);

        if (menuItem.hasSubMenu()) {
            SubMenu subMenu = menuItem.getSubMenu();
            int itemsCount = subMenu.size();

            for (int i = 0; i < itemsCount; i++) {
                MenuItem item = subMenu.getItem(i);
                buildMenuItem(item, (ViewGroup)itemView);
            }
        }

        menuLayoutManager.addMenuItem(menuContainer, menuItem, itemView);
    }

    /**
     * Called after either a button layout or any of colors changed to reset a background drawable
     */
    private void resetOpenCloseButtonDrawable() {
        ColorStateList colorStateList = PaletteUtils.getIconColorState(
                primaryColorHandle, primaryColorDark, accentColor);
        Drawable buttonBackground = openCloseButton.getBackground();

        if (buttonBackground instanceof TintedDrawable) {
            buttonBackground = ((TintedDrawable) buttonBackground).getPatternDrawable();
        }

        openCloseButton.setBackgroundDrawable(new TintedDrawable(buttonBackground, colorStateList));
    }

}
