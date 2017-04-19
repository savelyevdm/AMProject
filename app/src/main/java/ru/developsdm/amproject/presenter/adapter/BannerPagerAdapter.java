package ru.developsdm.amproject.presenter.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.model.BannerItem;
import ru.developsdm.amproject.utils.UtilLogging;

/**
 * Adapter for banners viewpager with bullets.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class BannerPagerAdapter extends PagerAdapter {

    private WeakReference<Context> contextRef;
    private List<BannerItem> mBanners;

    Context getContext() {
        return this.contextRef.get();
    }

    public BannerPagerAdapter(Context context, List<BannerItem> banners) {
        contextRef = new WeakReference<>(context);
        mBanners = banners;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        if (getContext() == null)
            return null;
        final BannerItem bannerItem = mBanners.get(position);
        LayoutInflater inflater = LayoutInflater.from(contextRef.get());
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_banner, collection, false);
        TextView tvBannerTitle = ButterKnife.findById(layout, R.id.tvBannerTitle);
        TextView tvBannerDescription = ButterKnife.findById(layout, R.id.tvBannerDescription);
        ImageView ivBannerImage = ButterKnife.findById(layout, R.id.ivBannerImage);
        if (bannerItem.getTitle() != null && bannerItem.getTitle().trim().length() > 0)
            tvBannerTitle.setText(bannerItem.getTitle());
        else
            tvBannerTitle.setVisibility(View.GONE);
        if (bannerItem.getDescription() != null && bannerItem.getDescription().trim().length() > 0)
            tvBannerDescription.setText(bannerItem.getDescription());
        else
            tvBannerDescription.setVisibility(View.GONE);
        if (bannerItem.getImageUrl() != null && bannerItem.getImageUrl().trim().length() > 0) {
            Picasso.with(getContext())
                    .load(bannerItem.getImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(ivBannerImage);
        } else {
            ivBannerImage.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        collection.addView(layout);

        // Clicks on items are not intended to be.
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getHandler().onClicked(data);
                UtilLogging.toast(getContext(), bannerItem.getTitle());
            }
        });

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mBanners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}