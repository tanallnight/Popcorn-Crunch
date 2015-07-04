package com.tanmay.popcorncrunch.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tanmay.popcorncrunch.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(getDisplayHomeAsUpEnabled());
        } else {
            throw new NullPointerException("Layout must contain a toolbar with id 'toolbar'");
        }
    }

    protected void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected abstract boolean getDisplayHomeAsUpEnabled();

    protected abstract int getLayoutResource();
}
