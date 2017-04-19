package ru.developsdm.amproject.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.App;
import ru.developsdm.amproject.model.NewsItem;
import ru.developsdm.amproject.presenter.adapter.NewsAdapter;
import ru.developsdm.amproject.view.contract.IMainView;
import ru.developsdm.amproject.view.contract.IMainViewHost;
import ru.developsdm.amproject.view.widget.RecyclerDividers.DividerItemDecoration;
import ru.developsdm.amproject.view.widget.RecyclerDividers.EndOffsetItemDecoration;
import ru.developsdm.amproject.view.widget.RecyclerDividers.StartOffsetItemDecoration;

/**
 * Thematic sets and news fragment to show in UI.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(String title, List<NewsItem> newsItems) {
        NewsFragment myFragment = new NewsFragment();

        if (!(newsItems instanceof Serializable))
            throw new RuntimeException("Items list must implement Serializable interface to pass it to fragment's bundle.");

        Bundle args = new Bundle();
        args.putString(App.EXTRA_TITLE_KEY, title);
        args.putSerializable(App.EXTRA_NEWS_KEY, (ArrayList) newsItems);
        myFragment.setArguments(args);

        return myFragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    NewsAdapter adapter;
    @BindView(R.id.tvSetsTitle)
    TextView tvSetsTitle;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() instanceof IMainView) {

            // TODO check for empty array and use own.

            Bundle arguments = getArguments();
            List<NewsItem> newsItems = (ArrayList<NewsItem>) arguments.getSerializable(App.EXTRA_NEWS_KEY);
            String title = arguments.getString(App.EXTRA_TITLE_KEY);
            View view = inflater.inflate(R.layout.fragment_catalogue_news, container, false);
            unbinder = ButterKnife.bind(this, view);

            // fragment's title
            tvSetsTitle.setText((title != null) ? title : App.EMPTY);

            // your RecyclerView
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            adapter = new NewsAdapter(getActivity(), newsItems, (IMainViewHost) getActivity());
            recyclerView.setAdapter(adapter);

            RecyclerView.ItemDecoration dividerItemDecorationStart = new StartOffsetItemDecoration(getActivity().getDrawable(R.drawable.item_decorator));
            recyclerView.addItemDecoration(dividerItemDecorationStart);

            RecyclerView.ItemDecoration dividerItemDecorationMiddle = new DividerItemDecoration(getActivity().getDrawable(R.drawable.item_decorator));
            recyclerView.addItemDecoration(dividerItemDecorationMiddle);

            RecyclerView.ItemDecoration dividerItemDecorationEnd = new EndOffsetItemDecoration(getActivity().getDrawable(R.drawable.item_decorator));
            recyclerView.addItemDecoration(dividerItemDecorationEnd);

            return view;
        } else {
            throw new RuntimeException("Host activity must implement IMainView interface.");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
