package com.iamwee.placesfinder.manager;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.iamwee.placesfinder.util.Contextor;

import java.util.ArrayList;

public class MediaScannerManager {

    public static ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int columnIndexData, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = Contextor.getInstance()
                .getContext()
                .getContentResolver()
                .query(uri, projection, null, null, null);

        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}
