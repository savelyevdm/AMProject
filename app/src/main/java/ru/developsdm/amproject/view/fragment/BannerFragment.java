package ru.developsdm.amproject.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.App;
import ru.developsdm.amproject.model.BannerItem;
import ru.developsdm.amproject.presenter.adapter.BannerPagerAdapter;
import ru.developsdm.amproject.view.widget.PagerBullet;

/**
 * Banner fragment to show in UI.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class BannerFragment extends Fragment {

    public static BannerFragment newInstance(List<BannerItem> bannerItems) {
        BannerFragment myFragment = new BannerFragment();

        if (!(bannerItems instanceof ArrayList))
            throw new RuntimeException("Daniil has been mistaken.");

        Bundle args = new Bundle();
        args.putSerializable(App.EXTRA_BANNERS_KEY, (ArrayList) bannerItems);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        List<BannerItem> banners = (ArrayList<BannerItem>)arguments.getSerializable(App.EXTRA_BANNERS_KEY);
        View view = inflater.inflate(R.layout.fragment_catalogue_banners, container, false);
        PagerBullet pagerBullet = (PagerBullet) ButterKnife.findById(view, R.id.pagerBullet);
        pagerBullet.setAdapter(new BannerPagerAdapter(getActivity(), banners));
        pagerBullet.invalidateBullets();
        pagerBullet.invalidate();
//        TextView tvFragmentTitle = ButterKnife.findById(view, R.id.tvFragmentTitle);
//        tvFragmentTitle.setText(text);
        return view;
    }

}
