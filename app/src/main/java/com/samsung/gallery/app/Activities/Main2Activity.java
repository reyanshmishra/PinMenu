package com.samsung.gallery.app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samsung.gallery.app.R;

public class Main2Activity extends AppCompatActivity {

    TextView mNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String TAG = "TAG";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
}
