package com.iamwee.placesfinder.view.suggest.choosephoto.adapter;

import com.iamwee.placesfinder.view.suggest.choosephoto.model.ChoosePhotoItem;
import com.iamwee.placesfinder.view.suggest.choosephoto.model.PhotoItem;
import com.iamwee.placesfinder.view.suggest.choosephoto.model.TakePhotoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeon on 1/25/17.
 */

public class ChoosePhotoConverter {

    public static TakePhotoItem makeTakePhotoSection() {
        return new TakePhotoItem();
    }

    public static ChoosePhotoItem makeChoosePhotoSection() {
        return new ChoosePhotoItem();
    }

    public static List<PhotoItem> makePhotoItemAll(List<String> paths) {
        List<PhotoItem> photoItemList = new ArrayList<>();
        for (String path : paths) {
            photoItemList.add(new PhotoItem(path));
        }
        return photoItemList;
    }

}
