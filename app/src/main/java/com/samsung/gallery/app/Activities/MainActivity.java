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
         *First thing we need to check get the permission from the user to access his content if the version is 6 greater.
         */
//        int readAccountsPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        /*if (Utils.isMarshmello()) {
            if (readAccountsPermission != PackageManager.PERMISSION_GRANTED) {
                //We do not have the permission ask for it.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Utils.READ_EXTERNAL_STORAGE_PERMISSION);
            } else {
                //we have the permission go ahead and fetch the content.
                fetchBucketsWithImageInTheme();
            }
        } else {
            //we have the permission go ahead and fetch the content cause android version is less then 6.
            fetchBucketsWithImageInTheme();
        }*/

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
        /**
         *ItemTouchListener for recyclerview to intercept the touch events.
         */
//        mRecyclerView.addOnItemTouchListener(mOnItemTouchListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Utils.READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //User loves you app go ahead have fun.
                fetchBucketsWithImageInTheme();
            } else {
                //User doesn't trust you so mission abort finish the activity.
                Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_LONG).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Don't keep any references clean the garbage.
        mCompositeDisposable.clear();
        mCompositeDisposable.dispose();
    }


    /**
     * RxJava is here again.
     */
    private void fetchBucketsWithImageInTheme() {


        mCompositeDisposable.add(Observable.fromCallable(() -> Utils.fetchBucketsList(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<BucketModel>>() {
                    @Override
                    public void onNext(ArrayList<BucketModel> data) {
                        mBucketList = data;
                        mBucketAdapter.updateData(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG-RX", e.getMessage() + " " + e.getCause());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    /**
     * Initialise imagelaoder before using.
     */


    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

        L.writeDebugLogs(false);
        L.writeLogs(false);
    }


    /**
     * On LongClick on Recyclerview Item show the dialog with the images in them.
     */
    public void onLongClick(int adapterPosition, View v) {
        Log.d("AAAA", "onLongClick: " + v.getX() + " Y:" + v.getY() + " w " + v.getWidth() + " h:" + v.getHeight());
    }
}
