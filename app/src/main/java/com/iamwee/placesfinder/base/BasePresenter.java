package com.iamwee.placesfinder.base;

import android.content.Context;

import com.iamwee.placesfinder.util.Contextor;

public class BasePresenter<T extends BaseView> {

    private T view;

    public BasePresenter(T view) {
        this.view = view;
    }

    protected T getView() {
        return view;
    }

    protected Context getContext() {
        return Contextor.getInstance().getContext();
    }
}
