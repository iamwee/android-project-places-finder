package com.iamwee.placesfinder.utilities;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by Zeon on 2/1/2560.
 */

public class Contextor {

    @SuppressLint("StaticFieldLeak")
    private static Contextor instance;

    public static Contextor getInstance(){
        if(instance == null) instance = new Contextor();
        return instance;
    }

    private Context context;

    public void init(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }
}
