package com.iamwee.placesfinder.manager.permission;

import android.content.Context;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    public static void initialize(Context context) {
        Dexter.initialize(context.getApplicationContext());
    }

    public static void requestPermission(List<String> permissions, final PermissionCallback callback) {

        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (callback != null) {
                    List<PermissionResult.Permission> permissionList = new ArrayList<>();
                    permissionList.addAll(getGrantedPermissionList(report.getGrantedPermissionResponses()));
                    permissionList.addAll(getDeniedPermissionList(report.getDeniedPermissionResponses()));
                    PermissionResult permissionResult = new PermissionResult(permissionList);
                    callback.onPermissionResult(permissionResult);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                           PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, permissions);
    }

    private static List<PermissionResult.Permission> getGrantedPermissionList(
            List<PermissionGrantedResponse> permissionGrantedResponseList
    ) {
        List<PermissionResult.Permission> permissionList = new ArrayList<>();
        if (permissionGrantedResponseList != null && permissionGrantedResponseList.size() > 0) {
            for (PermissionGrantedResponse permissionGrantedResponse : permissionGrantedResponseList) {
                String permissionName = permissionGrantedResponse.getPermissionName();
                PermissionResult.Permission permission = new PermissionResult.Permission(permissionName, true);
                permissionList.add(permission);
            }
        }
        return permissionList;
    }

    private static List<PermissionResult.Permission> getDeniedPermissionList(
            List<PermissionDeniedResponse> permissionDeniedResponseList
    ) {
        List<PermissionResult.Permission> permissionList = new ArrayList<>();
        if (permissionDeniedResponseList != null && permissionDeniedResponseList.size() > 0) {
            for (PermissionDeniedResponse permissionDeniedResponse : permissionDeniedResponseList) {
                String permissionName = permissionDeniedResponse.getPermissionName();
                PermissionResult.Permission permission = new PermissionResult.Permission(permissionName, false);
                permissionList.add(permission);
            }
        }
        return permissionList;
    }

    public interface PermissionCallback {
        void onPermissionResult(PermissionResult permissionResult);
    }

}
