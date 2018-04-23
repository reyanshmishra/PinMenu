package com.samsung.gallery.app.Models;

import java.util.ArrayList;

/**
 * Created by REYANSH on 7/19/2017.
 * <p>
 * <p>
 * This Model holds all the buckets in the phone and list of images in those buckets see {@link ImageModel}.
 */

public class BucketModel {

    public String mBucketId;
    public String mBucketName;
    public String mFirstPictureInBucketUri;
    public ArrayList<ImageModel> imagesInBucket;

    public BucketModel(String bucketId, String bucketName, String firstPictureInBucketUri, ArrayList<ImageModel> imageModels) {
        this.mBucketId = bucketId;
        this.mBucketName = bucketName;
        this.mFirstPictureInBucketUri = firstPictureInBucketUri;
        this.imagesInBucket = imageModels;
    }


}
