# Floating Menu

![Floating Menu Logo](http://www.furdei.systems/img/portfolio/furdroid-menu.jpg "Floating Menu Logo")

**Furdroid-floatingmenu** is a trendy widget to help you implement
material-styled menu. It's totally customizable and extendable. It can be of any shape,
open and close itself using any animation. And it is designed to be easily styled with your custom theme.

## Before you start

Before you start using **furdroid** please make sure you have Android artifacts 'android:android' and
'com.android.support' in your local Maven repository. If you don't please visit
[maven-android-sdk-deployer](https://github.com/simpligility/maven-android-sdk-deployer)
project and follow the instruction.

## Maven Dependency

```xml
<dependency>
  <groupId>systems.furdei</groupId>
  <artifactId>furdroid-floatingmenu</artifactId>
  <version>${project.version}</version>
</dependency>
```

## Gradle Dependency

```groovy
dependencies {
  compile 'systems.furdei:furdroid-floatingmenu:${project.version}'
}

```

## Description

Use *FloatingMenuBuilder* to configure and create a floating menu in your app.
You can customize any parameter you want or leave it with default values. Example use case: create a menu,
assign an open/close button, specify a container and provide a menu resource, open and close
animations.

### Building up a menu

Use setMenuResId(int) method to provide menu resource. Menu builder uses this resource
to inflate menu content dynamically.

Sample code:
```java
FloatingMenuBuilder menuBuilder = new FloatingMenuBuilder(this)
     .setOpenCloseButton(R.id.quick_menu_button)
     .setMenuContainer(R.id.quick_menu_container)
     .setMenuResId(R.menu.navigation_toolbar)
     .setOpenAnimation(R.anim.open_scale_from_bottom)
     .setCloseAnimation(R.anim.close_scale_to_bottom);
FloatingMenuController floatingMenuController = menuBuilder.build();
```

### Menu resources

Menu resource can be either hierarchical or a flat one. Hierarchical menu means that you can
combine menu items into groups and specify separate layouts for groups and items inside groups.
You can also use group titles inside a group layout. HierarchicalContainerMenuLayoutManager
is responsible for laying out inflated menu items using groups or single items layouts.
Both MenuLayoutManager and layout resources for HierarchicalContainerMenuLayoutManager
implementation are fully customizable.

Hierarchical menu resource example:

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
     <item android:title="@string/menu_group_1_title" >
         <menu>
             <item android:id="@+id/menu_item_1"
                 android:icon="@drawable/ic_action_1"
                 android:title="@string/menu_item_1_title" />

             <item android:id="@+id/menu_item_2"
                 android:icon="@drawable/ic_action_2"
                 android:title="@string/menu_item_2_title" />
         </menu>
     </item>

     <item android:title="@string/menu_group_2_title" >
         <menu>
             <item android:id="@+id/menu_item_3"
                 android:icon="@drawable/ic_action_3"
                 android:title="@string/menu_item_3_title" />

             <item android:id="@+id/menu_item_4"
                 android:icon="@drawable/ic_action_4"
                 android:title="@string/menu_item_4_title" />
         </menu>
     </item>
</menu>
```

Flat menu layout does not have any sub menus. FlatMenuLayoutManager
manages layout of a flat menu. You can specify a layout to be used by
FlatMenuLayoutManager through a constructor argument.

Flat menu resource example:

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
      <item android:id="@+id/menu_item_1"
         android:icon="@drawable/ic_action_1"
         android:title="@string/menu_item_1_title" />

     <item android:id="@+id/menu_item_2"
         android:icon="@drawable/ic_action_2"
         android:title="@string/menu_item_2_title" />
 </menu>
```

### Changing menu states

build() method returns a FloatingMenuController instance. You can use it to open/close
menu programmatically by calling open(), close(), toggle(), openAnimated(), closeAnimated(),
toggleAnimated() methods.

### Processing menu events

Menu item clicks are dispatched to the current activity's onOptionsItemSelected(MenuItem)
method. You should process them the same way you process event from an Action Bar, for example:

```java
 public boolean onOptionsItemSelected(MenuItem item) {
     switch (item.getItemId()) {
     case R.id.menu_item_1:
         // ... some processing here...
         return true;

     case R.id.menu_item_2:
         // ... some processing here...
         return true;

     case R.id.menu_item_3:
         // ... some processing here...
         return true;

     case R.id.menu_item_4:
         // ... some processing here...
         return true;
     }

     return super.onOptionsItemSelected(item);
 }
```

## furdroid

**Furdroid-components** is distributed as a part of [furdroid](https://github.com/furdei/furdroid) project.
Follow [this link](https://github.com/furdei/furdroid) to find more useful visual components, widgets and database
tools by [furdei.systems](http://www.furdei.systems).
