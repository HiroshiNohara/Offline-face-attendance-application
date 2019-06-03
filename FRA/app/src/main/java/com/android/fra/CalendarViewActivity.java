package com.android.fra;

import android.content.Intent;
import android.os.Bundle;

public class CalendarViewActivity extends BaseActivity {
    private CalendarViewFragment fragment;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        this.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.color.colorTransparent));
        fragment = new CalendarViewFragment();
        fragment.show(getSupportFragmentManager(), null);
    }

    public String getUid() {
        return uid;
    }

}
