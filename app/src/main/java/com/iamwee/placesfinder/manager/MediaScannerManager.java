package com.iamwee.placesfinder.manager;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.iamwee.placesfinder.util.Contextor;

import java.util.ArrayList;

public class MediaScannerManager {

    public static ArrayList<String> getAllShownImagesPath() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        int columnIndexData;
        ArrayList<String> listOfAllImages = new ArrayList<>();

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        Cursor cursor = Contextor.getInstance()
                .getContext()
                .getContentResolver()
                .query(uri, projection, null, null, null);

        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            listOfAllImages.add(cursor.getString(columnIndexData));
        }
        return listOfAllImages;
    }
}
