package com.reyansh.gallery.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.reyanshmishra.pinmenu.PinDialog;
import com.reyanshmishra.pinmenu.PinMenu;
import com.reyanshmishra.pinmenu.PinSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Context mContext;
    private PinDialog mPinDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(new RecyclerAdapter());

        mPinDialog = new PinDialog(this);
        mPinDialog.setContentView(R.layout.layout_pin_menu);
        mPinDialog.setPinSelectListener(new PinSelectListener() {
            @Override
            public void pinSelected(PinMenu pinMenu) {
                Toast.makeText(mContext, "" + pinMenu.getPinName(), Toast.LENGTH_SHORT).show();
            }
        });
        mPinDialog.addToRecyclerView(mRecyclerView);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}