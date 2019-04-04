package com.thunderhou.app.advancedlight.chapter03.stickynavlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thunderhou.app.advancedlight.R;

public class TabFragmentWithScrollView extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_with_scrollview, container, false);
        mTextView = view.findViewById(R.id.id_info);
        mTextView.setText(mTitle);
        return view;
    }

    public static TabFragmentWithScrollView newInstance(String title) {
        TabFragmentWithScrollView tabFragment = new TabFragmentWithScrollView();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}
