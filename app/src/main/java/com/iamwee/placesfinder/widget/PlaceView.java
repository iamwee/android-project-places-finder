package com.iamwee.placesfinder.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iamwee.placesfinder.R;


public class PlaceView extends FrameLayout implements View.OnClickListener {

    private OnClickListener listener;

    public PlaceView(Context context) {
        super(context);
        init();
    }

    public PlaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithStyleable(attrs);
    }

    public PlaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithStyleable(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithStyleable(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 2 / 3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void init() {
        inflate(getContext(), R.layout.widget_place_view, this);
    }

    private void initWithStyleable(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PlaceView);

        typedArray.recycle();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
        findViewById(R.id.pv_root).setOnClickListener(this);
    }

    public void setImageUrl(String imageUrl) {
        ImageView ivImg = (ImageView) findViewById(R.id.pv_img);
        Glide.with(getContext())
                .load(imageUrl)
                .centerCrop()
                .into(ivImg);
    }

    public void setName(String name) {
        TextView tvName = (TextView) findViewById(R.id.pv_name);
        tvName.setText(name);
    }

    public void setAddress(String address) {
        TextView tvAddress = (TextView) findViewById(R.id.pv_address);
        tvAddress.setText(address);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onClick(this);
    }


    public interface OnClickListener {
        void onClick(View view);
    }
}
