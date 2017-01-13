package com.iamwee.placesfinder;

import com.iamwee.placesfinder.view.register.RegisterPresenter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Zeon on 4/1/2560.
 */

public class RegisterUnitTest {

    @Test
    public void shouldPasswordMustLengthMoreThanSeven(){
        RegisterPresenter presenter = new RegisterPresenter();
        Assert.assertEquals(true, presenter.isPasswordValidated("123456789"));
    }

    @Test
    public void shouldPasswordAndConfirmPasswordMustMatch(){
        RegisterPresenter presenter = new RegisterPresenter();
        Assert.assertEquals(true,
                presenter.isPasswordAndConfirmPasswordMatched("123456789", "123456789"));
    }

}
