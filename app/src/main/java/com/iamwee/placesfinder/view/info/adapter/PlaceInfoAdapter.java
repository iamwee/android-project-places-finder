package com.iamwee.placesfinder.view.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;
import com.iamwee.placesfinder.view.info.adapter.model.HeaderItem;
import com.iamwee.placesfinder.view.info.adapter.model.MapItem;
import com.iamwee.placesfinder.view.info.adapter.model.MorePhotoHeaderItem;
import com.iamwee.placesfinder.view.info.adapter.model.MorePhotoItem;
import com.iamwee.placesfinder.view.info.adapter.model.PlaceInfoType;
import com.iamwee.placesfinder.view.info.adapter.model.ReviewItem;
import com.iamwee.placesfinder.view.info.adapter.model.SectionItem;
import com.iamwee.placesfinder.view.info.adapter.model.SummaryItem;
import com.iamwee.placesfinder.view.info.adapter.viewholder.HeaderViewHolder;
import com.iamwee.placesfinder.view.info.adapter.viewholder.MapViewHolder;
import com.iamwee.placesfinder.view.info.adapter.viewholder.MorePhotoHeaderViewHolder;
import com.iamwee.placesfinder.view.info.adapter.viewholder.PhotoViewHolder;
import com.iamwee.placesfinder.view.info.adapter.viewholder.ReviewViewHolder;
import com.iamwee.placesfinder.view.info.adapter.viewholder.SectionViewHolder;
import com.iamwee.placesfinder.view.info.adapter.viewholder.SummaryViewHolder;
import com.iamwee.placesfinder.widget.MenuView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class PlaceInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BasePlaceInfoItem> basePlaceInfoItems;

    public PlaceInfoAdapter(List<BasePlaceInfoItem> basePlaceInfoItems) {
        this.basePlaceInfoItems = basePlaceInfoItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent
                .getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case PlaceInfoType.HEADER_TYPE:
                return new HeaderViewHolder(inflater.inflate(R.layout.view_place_info_header, parent, false));
            case PlaceInfoType.SECTION_TYPE:
                return new SectionViewHolder(inflater.inflate(R.layout.view_place_info_section, parent, false));
            case PlaceInfoType.SUMMARY_TYPE:
                return new SummaryViewHolder(inflater.inflate(R.layout.view_place_info_summary, parent, false));
            case PlaceInfoType.MAP_TYPE:
                return new MapViewHolder(inflater.inflate(R.layout.view_place_info_map, parent, false));
            case PlaceInfoType.REVIEW_TYPE:
                return new ReviewViewHolder(inflater.inflate(R.layout.view_place_info_review, parent, false));
            case PlaceInfoType.MORE_PHOTO_HEADER_TYPE:
                return new MorePhotoHeaderViewHolder(inflater.inflate(R.layout.view_section_photo, parent, false));
            case PlaceInfoType.MORE_PHOTO_TYPE:
                return new PhotoViewHolder(inflater.inflate(R.layout.view_photos, parent, false));
            default:
                throw new NullPointerException("View type : " + viewType + " doesn't match.");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return basePlaceInfoItems.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            HeaderItem item = (HeaderItem) basePlaceInfoItems.get(position);
            setupHeaderSection(headerViewHolder, item);
        } else if (holder instanceof SectionViewHolder) {
            SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
            SectionItem item = (SectionItem) basePlaceInfoItems.get(position);
            setupSection(sectionViewHolder, item);
        } else if (holder instanceof SummaryViewHolder) {
            SummaryViewHolder summaryViewHolder = (SummaryViewHolder) holder;
            SummaryItem item = (SummaryItem) basePlaceInfoItems.get(position);
            setupSummarySection(summaryViewHolder, item);
        } else if (holder instanceof MapViewHolder) {
            MapViewHolder mapViewHolder = (MapViewHolder) holder;
            MapItem item = (MapItem) basePlaceInfoItems.get(position);
            setupMapSection(mapViewHolder, item);
        } else if (holder instanceof ReviewViewHolder) {
            ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
            ReviewItem item = (ReviewItem) basePlaceInfoItems.get(position);
            setupReviewSection(reviewViewHolder, item);
        } else if (holder instanceof MorePhotoHeaderViewHolder) {
            MorePhotoHeaderViewHolder morePhotoHeaderViewHolder = (MorePhotoHeaderViewHolder) holder;
            setupPhotoHeaderSection(morePhotoHeaderViewHolder);
        } else if (holder instanceof PhotoViewHolder) {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            MorePhotoItem item = (MorePhotoItem) basePlaceInfoItems.get(position);
            setupPhotoListSection(photoViewHolder, item);
        }
    }

    private void setupPhotoHeaderSection(MorePhotoHeaderViewHolder holder) {
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.PHOTO_LIST));
            }
        });
    }

    private void setupPhotoListSection(PhotoViewHolder holder, MorePhotoItem item) {
        holder.rvPhotos.setAdapter(new MorePhotoInfoAdapter(item.getImageUrl()));
    }

    private void setupHeaderSection(HeaderViewHolder holder, HeaderItem item) {
        if (!item.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(HttpManager.DEV_IMAGE_BASE_URL + item.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivImg);
        }

        holder.tvName.setText(item.getData().getName());
        holder.tvSummary.setText(item.getSummary());

        holder.mvSubmitPlace.setOnClickListener(new MenuView.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.SUBMIT_PLACE));
            }
        });

        holder.mvWriteReview.setOnClickListener(new MenuView.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.WRITE_REVIEW));
            }
        });

        holder.mvAddPhoto.setOnClickListener(new MenuView.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.CHOOSE_PHOTO));
            }
        });
    }

    private void setupSection(SectionViewHolder holder, SectionItem item) {
        holder.tvTitle.setTitle(item.getTitle());
    }

    private void setupSummarySection(SummaryViewHolder holder, SummaryItem item) {
        holder.tvPlaceCategory.setText(item.getCategory());
        holder.tvPlaceAddress.setText(item.getAddress());
        holder.tvPlaceDetail.setText(item.getDetail());
    }

    private void setupMapSection(MapViewHolder holder, MapItem item) {
        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivMap);
        holder.ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivity(OpenActivity.DIRECTION));
            }
        });
    }

    private void setupReviewSection(ReviewViewHolder holder, ReviewItem item) {
        holder.tvCodename.setText(item.getCodeName());
        holder.tvEmail.setText(item.getEmail());
        holder.tvDateReview.setText(item.getDate());
        holder.tvReviewMessage.setText(item.getReviewMessage());
    }


    @Override
    public int getItemCount() {
        return basePlaceInfoItems.size();
    }

    public void update(List<BasePlaceInfoItem> basePlaceInfoItems) {
        this.basePlaceInfoItems = basePlaceInfoItems;
        notifyDataSetChanged();
    }
}
