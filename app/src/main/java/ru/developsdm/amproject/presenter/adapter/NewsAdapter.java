package ru.developsdm.amproject.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.App;
import ru.developsdm.amproject.model.NewsItem;
import ru.developsdm.amproject.utils.UtilLogging;
import ru.developsdm.amproject.view.contract.IMainViewHost;

/**
 * Adapter for thematic sets and news recycler view.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private WeakReference<Context> contextRef;
    private List<NewsItem> news;
    private WeakReference<IMainViewHost> hostRef;

    Context getContext() {
        return this.contextRef.get();
    }

    IMainViewHost getHandler() {
        return this.hostRef.get();
    }

    public NewsAdapter(Context context, List<NewsItem> contentBlocks, IMainViewHost host) {
        this.contextRef = new WeakReference<>(context);
        this.hostRef = new WeakReference<>(host);
        this.news = contentBlocks;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layNewsRoot)
        View newsRoot;

        @BindView(R.id.ivNewsImage)
        ImageView ivNewsImage;
        @BindView(R.id.tvNewsDescription)
        TextView tvNewsDescription;

        NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        final NewsItem newsItem = news.get(position);

        String url = newsItem.getImageUrl();
        String description = newsItem.getDescription();

        if (url != null && url.trim().length() > 0) {
            holder.ivNewsImage.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            Picasso.with(getContext())
                    .load(url.trim())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.ivNewsImage);
        } else {
            holder.ivNewsImage.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        }

        if (description != null && description.trim().length() > 0) {
            holder.tvNewsDescription.setText(description.trim());
        } else {
            holder.tvNewsDescription.setText(App.EMPTY);
        }

        // Clicks on items are not intended to be.
        holder.newsRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getHandler().onClicked(data);
                UtilLogging.toast(getContext(), newsItem.getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


}
