package com.samsung.gallery.app.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.samsung.gallery.app.Models.BucketModel;
import com.samsung.gallery.app.Models.ImageModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

/**
 * Created by REYANSH on 7/19/2017.
 * <p>
 * Utils class to make your life easier.
 */

public class Utils {

    /**
     * Constants
     */
    public static int READ_EXTERNAL_STORAGE_PERMISSION = 450;

    /**
     * Version check.
     */
    public static boolean isMarshmello() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }


    /**
     * This will return the list of images from a bucket list.
     *
     * @param context  Application context.
     * @param bucketId from which we need to fetch the images.
     * @return ArrayList<ImageModel>
     */

    public static ArrayList<ImageModel> getImagesFromBucket(Context context, String bucketId) {
        ArrayList<ImageModel> imageModels = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };

        String condition = MediaStore.Images.ImageColumns.BUCKET_ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, condition, new String[]{bucketId}, null);
        if (cursor.moveToFirst()) {
            do {
                imageModels.add(new ImageModel(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)), "file://" + cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return imageModels;
    }

    /**
     * This will return the all the list of buckets in the device.
     *
     * @param context Application context.
     * @return ArrayList<BucketModel>
     */
    public static ArrayList<BucketModel> fetchBucketsList(Context context) {
        ArrayList<BucketModel> bucketModels = new ArrayList<>();

        String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";

        //Order by most recent.
        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

        String[] projection = new String[]{
                MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA
        };

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        if (cursor.moveToFirst()) {
            do {

                String bucketId = cursor.getString(0);
                String bucketName = cursor.getString(1);
                ArrayList<ImageModel> imagesList = getImagesFromBucket(context, bucketId);

                if (imagesList.size() > 0) {
                    bucketModels.add(new BucketModel(bucketId, bucketName, imagesList.get(0).imageUri, imagesList));
                }

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return bucketModels;
    }


    public static int animals[] = new int[]{
            R.drawable.animals_1,
            R.drawable.animals_2,
            R.drawable.animals_3,
            R.drawable.animals_4,
            R.drawable.animals_5,
            R.drawable.animals_6
    };


    public static int architecture[] = new int[]{
            R.drawable.architecture_1,
            R.drawable.architecture_2,
            R.drawable.architecture_3,
            R.drawable.architecture_4,
    };


    public static int foods[] = new int[]{
            R.drawable.food_1,
            R.drawable.food_2,
            R.drawable.food_3,
            R.drawable.food_4,
            R.drawable.food_5,
    };


    public static int posters[] = new int[]{
            R.drawable.poster_1,
            R.drawable.poster_2,
            R.drawable.poster_3,
            R.drawable.poster_4,
            R.drawable.poster_5,
            R.drawable.poster_6,
    };
    public static int scenery[] = new int[]{
            R.drawable.scenery_1,
            R.drawable.scenery_2,
            R.drawable.scenery_3,
            R.drawable.scenery_4,
            R.drawable.scenery_5,
            R.drawable.scenery_6,
    };
    public static int images[] = new int[]{
            R.drawable.animals_1,
            R.drawable.architecture_1,
            R.drawable.food_1,
            R.drawable.poster_1,
            R.drawable.scenery_1,
    };

    public static ArrayList<BucketModel> getBuckets() {


        String names[] = new String[]{"Animals", "Architecture", "Foods", "Posters", "Scenery"};

        ArrayList<BucketModel> bucketModels = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ArrayList<ImageModel> imageModels = new ArrayList<>();

            if (names[i].equalsIgnoreCase("Animals")) {
                for (int j = 0; j < animals.length; j++) {
                    imageModels.add(new ImageModel("" + animals[j], "drawable://" + animals[j]));
                }
            } else if (names[i].equalsIgnoreCase("Architecture")) {
                for (int j = 0; j < architecture.length; j++) {
                    imageModels.add(new ImageModel("" + architecture[j], "drawable://" + architecture[j]));
                }
            } else if (names[i].equalsIgnoreCase("Foods")) {
                for (int j = 0; j < foods.length; j++) {
                    imageModels.add(new ImageModel("" + foods[j], "drawable://" + foods[j]));
                }
            } else if (names[i].equalsIgnoreCase("Posters")) {
                for (int j = 0; j < posters.length; j++) {
                    imageModels.add(new ImageModel("" + posters[j], "drawable://" + posters[j]));
                }
            } else if (names[i].equalsIgnoreCase("Scenery")) {
                for (int j = 0; j < scenery.length; j++) {
                    imageModels.add(new ImageModel("" + scenery[j], "drawable://" + scenery[j]));
                }
            }

            bucketModels.add(new BucketModel("", names[i], "drawable://" + images[i], imageModels));
        }
        return bucketModels;
    }
}
