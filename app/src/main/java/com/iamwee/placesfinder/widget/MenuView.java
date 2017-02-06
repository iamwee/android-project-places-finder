package com.iamwee.placesfinder.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamwee.placesfinder.R;

public class MenuView extends FrameLayout implements View.OnClickListener {

    private int imageResource;
    private String menu;
    private OnClickListener listener;

    public MenuView(Context context) {
        super(context);
        init();
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithStyleable(attrs);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithStyleable(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithStyleable(attrs);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState ss = new SavedState(parcelable);
        ss.imageResource = imageResource;
        ss.menu = menu;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if (state != null && state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            this.imageResource = ss.imageResource;
            this.menu = ss.menu;
        }
    }

    private void init() {
        inflate(getContext(), R.layout.widget_menu_view, this);
    }

    private void initWithStyleable(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MenuView);

        imageResource = typedArray.getResourceId(R.styleable.MenuView_mv_icon, R.mipmap.ic_launcher);
        menu = typedArray.getString(R.styleable.MenuView_mv_title);

        setIconResource();
        setMenuText();

        typedArray.recycle();
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
        findViewById(R.id.root_layout).setOnClickListener(this);
    }

    private void setIconResource() {
        ImageView ivImg = (ImageView) findViewById(R.id.mv_img);
        ivImg.setImageResource(imageResource);
    }

    private void setMenuText() {
        TextView tvMenu = (TextView) findViewById(R.id.mv_menu);
        tvMenu.setText(menu);

    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onClick(this);
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    private static class SavedState extends BaseSavedState {

        int imageResource;
        String menu;

        SavedState(Parcel source) {
            super(source);
            imageResource = source.readInt();
            menu = source.readString();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(imageResource);
            out.writeString(menu);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
