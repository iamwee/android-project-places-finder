package com.iamwee.placesfinder.util;

import android.annotation.SuppressLint;
import android.content.Context;


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
