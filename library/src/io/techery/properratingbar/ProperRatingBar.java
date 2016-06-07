package io.techery.properratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
    The MIT License (MIT)

    Copyright (c) 2015 Techery (http://techery.io/)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
 */

/**
 * TODO : add doc
 * * See {@link R.styleable#ProperRatingBar ProperRatingBar Attributes}
 */
public class ProperRatingBar extends LinearLayout {

    private static final int DF_TOTAL_TICKS = 5;
    private static final int DF_DEFAULT_TICKS = 3;
    private static final boolean DF_CLICKABLE = false;
    private static final int DF_SYMBOLIC_TICK_RES = R.string.prb_default_symbolic_string;
    private static final int DF_SYMBOLIC_TEXT_SIZE_RES = R.dimen.prb_symbolic_tick_default_text_size;
    private static final int DF_SYMBOLIC_TEXT_STYLE = Typeface.NORMAL;
    private static final int DF_SYMBOLIC_TEXT_NORMAL_COLOR = Color.BLACK;
    private static final int DF_SYMBOLIC_TEXT_SELECTED_COLOR = Color.GRAY;
    private static final int DF_TICK_SPACING_RES = R.dimen.prb_drawable_tick_default_spacing;

    private int totalTicks;
    private int lastSelectedTickIndex;
    private boolean clickable;
    private String symbolicTick;
    private int customTextSize;
    private int customTextStyle;
    private int customTextNormalColor;
    private int customTextSelectedColor;
    private Drawable tickNormalDrawable;
    private Drawable tickSelectedDrawable;
    private int tickSpacing;

    private boolean useSymbolicTick = false;
    private int rating;
    private RatingListener listener = null;

    public ProperRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProperRatingBar);
        //
        totalTicks = a.getInt(R.styleable.ProperRatingBar_prb_totalTicks, DF_TOTAL_TICKS);
        rating = a.getInt(R.styleable.ProperRatingBar_prb_defaultRating, DF_DEFAULT_TICKS);
        //
        clickable = a.getBoolean(R.styleable.ProperRatingBar_prb_clickable, DF_CLICKABLE);
        //
        symbolicTick = a.getString(R.styleable.ProperRatingBar_prb_symbolicTick);
        if (symbolicTick == null) symbolicTick = context.getString(DF_SYMBOLIC_TICK_RES);
        //
        customTextSize = a.getDimensionPixelSize(R.styleable.ProperRatingBar_android_textSize,
                context.getResources().getDimensionPixelOffset(DF_SYMBOLIC_TEXT_SIZE_RES));
        customTextStyle = a.getInt(R.styleable.ProperRatingBar_android_textStyle, DF_SYMBOLIC_TEXT_STYLE);
        customTextNormalColor = a.getColor(R.styleable.ProperRatingBar_prb_symbolicTickNormalColor,
                DF_SYMBOLIC_TEXT_NORMAL_COLOR);
        customTextSelectedColor = a.getColor(R.styleable.ProperRatingBar_prb_symbolicTickSelectedColor,
                DF_SYMBOLIC_TEXT_SELECTED_COLOR);
        //
        tickNormalDrawable = a.getDrawable(R.styleable.ProperRatingBar_prb_tickNormalDrawable);
        tickSelectedDrawable = a.getDrawable(R.styleable.ProperRatingBar_prb_tickSelectedDrawable);
        tickSpacing = a.getDimensionPixelOffset(R.styleable.ProperRatingBar_prb_tickSpacing,
                context.getResources().getDimensionPixelOffset(DF_TICK_SPACING_RES));
        //
        afterInit();
        //
        a.recycle();
    }

    private void afterInit() {
        if (rating > totalTicks) rating = totalTicks;
        lastSelectedTickIndex = rating - 1;
        //
        if (tickNormalDrawable == null || tickSelectedDrawable == null) {
            useSymbolicTick = true;
        }
        //
        addTicks(this.getContext());
    }

    private void addTicks(Context context) {
        this.removeAllViews();
        for (int i = 0; i < totalTicks; i++) {
            addTick(context, i);
        }
        redrawTicks();
    }

    private void addTick(Context context, int position) {
        if (useSymbolicTick) {
            addSymbolicTick(context, position);
        } else {
            addDrawableTick(context, position);
        }
    }

    private void addSymbolicTick(Context context, int position) {
        TextView tv = new TextView(context);
        tv.setText(symbolicTick);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, customTextSize);
        if (customTextStyle != 0) {
            tv.setTypeface(Typeface.DEFAULT, customTextStyle);
        }
        if (clickable) {
            tv.setTag(R.id.prb_child_tag_id, position);
            tv.setOnClickListener(mTickClickedListener);
        }
        this.addView(tv);
    }

    private void addDrawableTick(Context context, int position) {
        ImageView iv = new ImageView(context);
        iv.setPadding(tickSpacing, tickSpacing, tickSpacing, tickSpacing);
        if (clickable) {
            iv.setTag(R.id.prb_child_tag_id, position);
            iv.setOnClickListener(mTickClickedListener);
        }
        this.addView(iv);
    }

    private View.OnClickListener mTickClickedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            lastSelectedTickIndex = (int) v.getTag(R.id.prb_tick_tag_id);
            rating = lastSelectedTickIndex + 1;
            redrawTicks();
            if (listener != null) listener.onRatePicked(ProperRatingBar.this);
        }
    };

        for (int i = 0; i < totalTicks; i++) {
            if (useSymbolicTick) {
                redrawChildSelection((TextView) ProperRatingBar.this.getChildAt(i), i <= lastSelectedTickIndex);
            } else {
                redrawChildSelection((ImageView) ProperRatingBar.this.getChildAt(i), i <= lastSelectedTickIndex);
    private void redrawTicks() {
            }
        }
    }

    private void redrawTickSelection(ImageView tick, boolean isSelected) {
        if (isSelected) {
            tick.setImageDrawable(tickSelectedDrawable);
        } else {
            tick.setImageDrawable(tickNormalDrawable);
        }
    }

    private void redrawTickSelection(TextView tick, boolean isSelected) {
        if (isSelected) {
            tick.setTextColor(customTextSelectedColor);
        } else {
            tick.setTextColor(customTextNormalColor);
        }
    }

    private void iterateTicks(TicksIterator iterator) {
        if (iterator == null) throw new IllegalArgumentException("Iterator can't be null!");

        for (int i = 0; i < getChildCount(); i++) {
            iterator.onTick(getChildAt(i), i);
        }
    }

    private interface TicksIterator {
        void onTick(View tick, int position);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Saving and restoring state
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.rating = rating;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        setRating(savedState.rating);
    }

    static class SavedState extends BaseSavedState {

        int rating;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.rating = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.rating);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    ///////////////////////////////////////////////////////////////////////////
    // Them getter and setter methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get the attached {@link RatingListener}
     * @return listener or null if none was set
     */
    public RatingListener getListener() {
        return listener;
    }

    /**
     * Set the {@link RatingListener} to be called when user taps rating bar's ticks
     * @param listener listener to set
     *
     * @throws IllegalArgumentException if listener is <b>null</b>
     */
    public void setListener(RatingListener listener) {
        if (listener == null) throw new IllegalArgumentException("listener cannot be null!");

        this.listener = listener;
    }

    /**
     * Remove listener
     */
    public void removeRatingListener() {
        this.listener = null;
    }

    /**
     * Get the current rating shown
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set the rating to show
     * @param rating new rating value
     */
    public void setRating(int rating) {
        if (rating  > this.totalTicks) rating = totalTicks;
        this.rating = rating;
        lastSelectedTickIndex = rating - 1;
        redrawTicks();
    }

    public void setSymbolicTick(String tick) {
        this.symbolicTick = tick;
        afterInit();
    }

    public String getSymbolicTick() {
        return this.symbolicTick;
    }
}
