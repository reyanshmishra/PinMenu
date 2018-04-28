package com.samsung.gallery.app.Activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.L;
import com.samsung.gallery.app.Adapters.BucketAdapter;
import com.samsung.gallery.app.Helpers.Utils;
import com.samsung.gallery.app.PinDialog;
import com.samsung.gallery.app.Models.BucketModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    private PinDialog mPinDialog;
    private ArrayList<BucketModel> mBucketList;

    /**
     * UIL options
     */

    public DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.default_place_holder)
            .showImageForEmptyUri(R.drawable.default_place_holder)
            .showImageOnFail(R.drawable.default_place_holder)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .cacheOnDisk(true)
            .build();


    //Binding view using butterknife.
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Adapter for recyclerview.
     */
    private BucketAdapter mBucketAdapter;

    /**
     * Context of the App.
     */

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mCompositeDisposable = new CompositeDisposable();
        getSupportActionBar().hide();
        initImageLoader();
    
        /**
         *Tell ButterKnife to bind the views.
         */
        ButterKnife.bind(this);

        mBucketList = Utils.getBuckets();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //Our gallery Adapter
        mBucketAdapter = new BucketAdapter(this, mBucketList);
        mRecyclerView.setAdapter(mBucketAdapter);

        mPinDialog = new PinDialog(this);
        mPinDialog.setPinSelectListener((pinMenu) -> Toast.makeText(mContext, "" + pinMenu.getPinName(), Toast.LENGTH_SHORT).show());
        mPinDialog.addPinListener(mRecyclerView);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        mCompositeDisposable.dispose();
    }


    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

        L.writeDebugLogs(false);
        L.writeLogs(false);
    }

}