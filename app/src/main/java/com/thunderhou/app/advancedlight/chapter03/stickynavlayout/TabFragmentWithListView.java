package com.thunderhou.app.advancedlight.chapter03.stickynavlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thunderhou.app.advancedlight.R;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentWithListView extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private ListView mListView;
    private List<String> mDatas = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_with_listview, container, false);
        mListView = view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        for (int i = 0; i < 50; i++) {
            mDatas.add("ListView -> " + mTitle + " -> " + i);
        }
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.id_info, mDatas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.e("tag", "convertView = " + convertView);
                return super.getView(position, convertView, parent);
            }
        });
        return view;

    }

    public static TabFragmentWithListView newInstance(String title) {
        TabFragmentWithListView tabFragment = new TabFragmentWithListView();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}
