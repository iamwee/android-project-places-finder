package com.iamwee.placesfinder.template;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

class TemplatePresenter implements TemplateContractor.Presenter {

    private TemplateContractor.View view;

    private TemplatePresenter(TemplateContractor.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    static TemplatePresenter newInstance(TemplateContractor.View view){
        return new TemplatePresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public Bundle onSaveInstanceState() {
        return null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }
}
