package com.iamwee.placesfinder.manager.permission;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class PermissionResult implements Parcelable {

    private List<Permission> permissionList;

    public PermissionResult() {
    }

    public PermissionResult(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    protected PermissionResult(Parcel in) {
        permissionList = in.createTypedArrayList(Permission.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(permissionList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PermissionResult> CREATOR = new Creator<PermissionResult>() {
        @Override
        public PermissionResult createFromParcel(Parcel in) {
            return new PermissionResult(in);
        }

        @Override
        public PermissionResult[] newArray(int size) {
            return new PermissionResult[size];
        }
    };

    public boolean areAllPermissionsGranted() {
        if (isPermissionListAvailable()) {
            for (Permission permission : permissionList) {
                if (permission.isDenied()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isAnyPermissionPermanentlyDenied() {
        if (isPermissionListAvailable()) {
            for (Permission permission : permissionList) {
                if (permission.isDenied()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean isPermissionListAvailable() {
        return permissionList != null && permissionList.size() > 0;
    }

    public List<Permission> getDeniedPermissionResponses() {
        List<Permission> deniedPermissionList = new ArrayList<>();
        if (isPermissionListAvailable()) {
            for (Permission permission : permissionList) {
                if (permission.isDenied()) {
                    deniedPermissionList.add(permission);
                }
            }
        }
        return deniedPermissionList;
    }

    public List<Permission> getGrantedPermissionResponses() {
        List<Permission> grantedPermissionList = new ArrayList<>();
        if (isPermissionListAvailable()) {
            for (Permission permission : permissionList) {
                if (permission.isGranted()) {
                    grantedPermissionList.add(permission);
                }
            }
        }
        return grantedPermissionList;
    }

    public static class Permission implements Parcelable {
        String permissionName;
        boolean isGranted;

        public Permission() {
        }

        public Permission(String permissionName, boolean isGranted) {
            this.permissionName = permissionName;
            this.isGranted = isGranted;
        }

        protected Permission(Parcel in) {
            permissionName = in.readString();
            isGranted = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(permissionName);
            dest.writeByte((byte) (isGranted ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Permission> CREATOR = new Creator<Permission>() {
            @Override
            public Permission createFromParcel(Parcel in) {
                return new Permission(in);
            }

            @Override
            public Permission[] newArray(int size) {
                return new Permission[size];
            }
        };

        public String getPermissionName() {
            return permissionName;
        }

        public boolean isGranted() {
            return isGranted;
        }

        public boolean isDenied() {
            return !isGranted;
        }
    }
}
