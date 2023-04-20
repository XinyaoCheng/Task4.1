package com.example.a41p;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class myNumberPicker extends NumberPicker {


    public myNumberPicker(Context context) {
        super(context);
    }

    public myNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public myNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
        public void addView(View child) {
            super.addView(child);
            updateView(child);
        }

        @Override
        public void addView(View child, int index,
                            android.view.ViewGroup.LayoutParams params) {
            super.addView(child, index, params);
            updateView(child);
        }

        @Override
        public void addView(View child, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, params);
            updateView(child);
        }

        public void updateView(View view) {
            if (view instanceof EditText) {
                ((EditText) view).setTextColor(Color.parseColor("#FD7B15"));
            ((EditText) view).setTextSize(20);
            }
        }


}
