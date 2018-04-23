package com.samsung.gallery.app.Models;

/**
 * Created by REYANSH on 7/19/2017.
 * <p>
 * <p>
 * This class hold images from the {@link BucketModel}
 */

public class ImageModel {
    public String imageUri;
    public String imageName;

    public ImageModel(String imageName, String imageUri) {
        this.imageUri = imageUri;
        this.imageName = imageName;
    }

}
