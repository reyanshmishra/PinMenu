package com.samsung.gallery.app.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.samsung.gallery.app.Adapters.BucketImagesAdapter;
import com.samsung.gallery.app.Helpers.Utils;
import com.samsung.gallery.app.Models.ImageModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by REYANSH on 7/19/2017.
 */

public class BucketImageActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Context mContext;
    private BucketImagesAdapter mBucketImagesAdapter;
    private ArrayList<ImageModel> mImageModels;

    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_image);
        mContext = getApplicationContext();
        ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        String bucketName = getIntent().getExtras().getString("BUCKET_ID");
        mImageModels=new ArrayList<>();
        if (bucketName.equalsIgnoreCase("Animals")) {
            for (int j = 0; j < Utils.animals.length; j++) {
                mImageModels.add(new ImageModel("" + Utils.animals[j], "drawable://" + Utils.animals[j]));
            }
        } else if (bucketName.equalsIgnoreCase("Architecture")) {
            for (int j = 0; j < Utils.architecture.length; j++) {
                mImageModels.add(new ImageModel("" + Utils.architecture[j], "drawable://" + Utils.architecture[j]));
            }
        } else if (bucketName.equalsIgnoreCase("Foods")) {
            for (int j = 0; j < Utils.foods.length; j++) {
                mImageModels.add(new ImageModel("" + Utils.foods[j], "drawable://" + Utils.foods[j]));
            }
        } else if (bucketName.equalsIgnoreCase("Posters")) {
            for (int j = 0; j < Utils.posters.length; j++) {
                mImageModels.add(new ImageModel("" + Utils.posters[j], "drawable://" + Utils.posters[j]));
            }
        } else if (bucketName.equalsIgnoreCase("Scenery")) {
            for (int j = 0; j < Utils.scenery.length; j++) {
                mImageModels.add(new ImageModel("" + Utils.scenery[j], "drawable://" + Utils.scenery[j]));
            }
        }


        mBucketImagesAdapter = new BucketImagesAdapter(mImageModels);
        mRecyclerView.setAdapter(mBucketImagesAdapter);


        /**
         * RxJava to do the hard work on the background
         * those its not that necessary but still lets be on safer side.
         *
         */

       /* mCompositeDisposable.add(Observable.fromCallable(() -> Utils.getImagesFromBucket(mContext, getIntent().getExtras().getString("BUCKET_ID")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<ImageModel>>() {

                    @Override
                    public void onNext(ArrayList<ImageModel> imageModels) {
                        mImageModels = imageModels;
                        mBucketImagesAdapter.updateData(mImageModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RX-X", "" + e.getCause());

                    }

                    @Override
                    public void onComplete() {

                    }
                }));*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Back button clicked go back.
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        clean Up
//        mCompositeDisposable.clear();
//        mCompositeDisposable.dispose();
    }
}
