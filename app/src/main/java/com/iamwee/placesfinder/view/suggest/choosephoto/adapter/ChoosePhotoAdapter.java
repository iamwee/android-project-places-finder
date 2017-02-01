package com.iamwee.placesfinder.view.suggest.choosephoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.view.suggest.choosephoto.ChoosePhotoType;
import com.iamwee.placesfinder.view.suggest.choosephoto.model.BaseChoosePhotoItem;
import com.iamwee.placesfinder.view.suggest.choosephoto.model.PhotoItem;
import com.iamwee.placesfinder.view.suggest.choosephoto.viewholder.ChoosePhotoViewHolder;
import com.iamwee.placesfinder.view.suggest.choosephoto.viewholder.PhotoViewHolder;
import com.iamwee.placesfinder.view.suggest.choosephoto.viewholder.TakePhotoViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChoosePhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseChoosePhotoItem> baseChoosePhotoItems;

    public ChoosePhotoAdapter(List<BaseChoosePhotoItem> baseChoosePhotoItems) {
        this.baseChoosePhotoItems = baseChoosePhotoItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //int height = parent.getMeasuredHeight() / 4;
        int height = parent.getMeasuredWidth() / 3;
        switch (viewType) {
            case ChoosePhotoType.TAKE_PHOTO_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_take_photo, null);
                view.setMinimumHeight(height);
                return new TakePhotoViewHolder(view);
            case ChoosePhotoType.CHOOSE_PHOTO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_choose_photo, null);
                view.setMinimumHeight(height);
                return new ChoosePhotoViewHolder(view);
            case ChoosePhotoType.PHOTO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo, null);
                view.setMinimumHeight(height);
                return new PhotoViewHolder(view);
            default:
                throw new NullPointerException("View type : " + viewType + " doesn't match.");
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TakePhotoViewHolder) {
            TakePhotoViewHolder vh = (TakePhotoViewHolder) holder;
            setupTakePhotoSection(vh);
        } else if (holder instanceof ChoosePhotoViewHolder) {
            ChoosePhotoViewHolder vh = (ChoosePhotoViewHolder) holder;
            setupChoosePhotoSection(vh);
        } else if (holder instanceof PhotoViewHolder) {
            PhotoViewHolder vh = (PhotoViewHolder) holder;
            PhotoItem photoItem = (PhotoItem) baseChoosePhotoItems.get(position);
            setupPhotoSection(vh, photoItem);
        }
    }

    private void setupTakePhotoSection(TakePhotoViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.TAKE_PHOTO));
            }
        });
    }

    private void setupChoosePhotoSection(ChoosePhotoViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.CHOOSE_PHOTO));
            }
        });
    }

    private void setupPhotoSection(PhotoViewHolder viewHolder, final PhotoItem photoItem) {
        Glide.with(viewHolder.itemView.getContext())
                .load(photoItem.getPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.ivImg);
        viewHolder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(photoItem.getPath());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return baseChoosePhotoItems.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return baseChoosePhotoItems.size();
    }

}
