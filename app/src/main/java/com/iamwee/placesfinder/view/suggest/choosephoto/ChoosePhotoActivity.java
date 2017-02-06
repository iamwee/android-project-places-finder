package com.iamwee.placesfinder.view.suggest.choosephoto;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.manager.MediaScannerManager;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.manager.permission.PermissionResult;
import com.iamwee.placesfinder.view.suggest.choosephoto.adapter.ChoosePhotoAdapter;
import com.iamwee.placesfinder.view.suggest.choosephoto.adapter.ChoosePhotoConverter;
import com.iamwee.placesfinder.view.suggest.choosephoto.model.BaseChoosePhotoItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChoosePhotoActivity extends PlacesFinderActivity
        implements PermissionManager.PermissionCallback {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CHOOSE_PHOTO = 2;
    private RecyclerView rvChoosePhoto;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        PermissionManager.requestPermission(Arrays.asList(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ), this);
    }

    @Override
    protected void initView() {
        rvChoosePhoto = (RecyclerView) findViewById(R.id.rv_choose_photo);
        rvChoosePhoto.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onTakeOrChoosePhoto(OpenActivity event) {
        if (event.getStatus() == OpenActivity.TAKE_PHOTO_ACTIVITY) {
            dispatchTakePictureIntent();
        } else if (event.getStatus() == OpenActivity.CHOOSE_PHOTO_ACTIVITY) {
            getImageFromGallery();
        }
    }

    @Subscribe
    public void onPhotoSelected(String path) {
        sendResult(path);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPermissionResult(PermissionResult result) {
        if (result.areAllPermissionsGranted()) {
            List<String> paths = MediaScannerManager.getAllShownImagesPath();
            Collections.reverse(paths);
            List<BaseChoosePhotoItem> baseChoosePhotoItems = new ArrayList<>();
            baseChoosePhotoItems.add(ChoosePhotoConverter.makeTakePhotoSection());
            baseChoosePhotoItems.add(ChoosePhotoConverter.makeChoosePhotoSection());
            baseChoosePhotoItems.addAll(ChoosePhotoConverter.makePhotoItemAll(
                    paths.subList(0, paths.size() > 91 ? 91 : paths.size()))
            );

            rvChoosePhoto.setAdapter(new ChoosePhotoAdapter(baseChoosePhotoItems));

        } else if (result.isAnyPermissionPermanentlyDenied()) {
            PermissionManager.showPermissionRequestDeniedDialog(
                    this,
                    getString(R.string.error_camera_storage_permission_denied)
            );
        } else {
            PermissionManager.showPermissionRequestDeniedDialog(
                    this,
                    getString(R.string.error_camera_storage_permission_denied)
            );
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void getImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_CHOOSE_PHOTO);
    }

    private String findPath(Uri imageUri) {
        String imagePath;
        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, columns, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imagePath = cursor.getString(columnIndex);
        } else {
            imagePath = imageUri.getPath();
        }
        return imagePath;
    }

    private void saveImageToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photoFile);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void sendResult(String imagePath) {
        Intent intent = new Intent();
        intent.putExtra("image_path", imagePath);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            saveImageToGallery();
            sendResult(photoFile.getPath());
        } else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            sendResult(findPath(imageUri));
        }
    }
}
