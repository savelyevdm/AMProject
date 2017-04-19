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
 * Mock fragment to show in UI.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

public class MockFragment extends Fragment {

    public static MockFragment newInstance(String title) {
        MockFragment myFragment = new MockFragment();

        Bundle args = new Bundle();
        args.putString(App.EXTRA_TEXT_KEY, title);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String text = arguments.getString(App.EXTRA_TEXT_KEY);
        View view = inflater.inflate(R.layout.fragment_mock, container, false);
        TextView tvFragmentTitle = ButterKnife.findById(view, R.id.tvFragmentTitle);
        tvFragmentTitle.setText(text);
        return view;
    }

}
