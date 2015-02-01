package com.furdei.furdroid.floatingmenu.internal;

import android.content.Context;
import android.os.Parcelable;
import android.view.AbsSavedState;
import android.view.View;

import com.furdei.furdroid.floatingmenu.R;

/**
 * This view participates in activity's view hierarchy and does nothing but notifying a listener
 * of saving/restoring instance state. A state change listener would be a menu controller.
 *
 * @author Stepan Furdey
 */
public class MenuAnchorView extends View {

    public interface StateChangeListener {
        Parcelable onSaveInstanceState(Parcelable state);
        void onRestoreInstanceState(Parcelable state);
    }

    private StateChangeListener stateChangeListener;

    public MenuAnchorView(Context context) {
        super(context);
        setId(R.id.floating_menu_anchor); // we need this to have our state saved
        setVisibility(View.GONE);
    }

    public StateChangeListener getStateChangeListener() {
        return stateChangeListener;
    }

    public void setStateChangeListener(StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        return stateChangeListener != null ?
                stateChangeListener.onSaveInstanceState(parcelable) : parcelable;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof AbsSavedState) {
            super.onRestoreInstanceState(((AbsSavedState) state).getSuperState());
        }

        if (stateChangeListener != null) {
            stateChangeListener.onRestoreInstanceState(state);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }
}
