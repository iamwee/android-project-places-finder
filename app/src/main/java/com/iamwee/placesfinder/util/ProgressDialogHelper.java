package com.iamwee.placesfinder.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.iamwee.placesfinder.R;


public class ProgressDialogHelper {

    private static ProgressDialog dialog;

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, final Callback callback) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.msg_loading));
        dialog.setCancelable(false);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,
                context.getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callback != null) callback.onProgressDialogCancelled();
                    }
                });
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        dialog = null;
    }

    public interface Callback {
        void onProgressDialogCancelled();
    }

}
