package ru.developsdm.amproject.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import ru.developsdm.amproject.R;
import ru.developsdm.amproject.app.App;

/**
 * Divider fragment to show in UI.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class DividerFragment extends Fragment {

    public static DividerFragment newInstance() {
        return new DividerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_divider, container, false);
    }

}
