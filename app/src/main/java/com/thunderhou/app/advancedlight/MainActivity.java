package com.thunderhou.app.advancedlight;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.thunderhou.app.advancedlight.base.BaseActivity;
import com.thunderhou.app.advancedlight.chapter01.recyclerview.DividerItemDecoration;
import com.thunderhou.app.advancedlight.chapter01.recyclerview.HomeAdapter;
import com.thunderhou.app.advancedlight.chapter01.recyclerview.RecyclerViewActivity;
import com.thunderhou.app.advancedlight.chapter03.CustomViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<String> mList;
    private HomeAdapter mHomeAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add("chapter01-RecyclerView");
        mList.add("chapter03-CustomView");
    }

    @Override
    protected void initView() {
        mRecyclerView= findViewById(R.id.id_recyclerview);
        setListView();
    }

    public void setListView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    private void setLister(){
        mHomeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                handleItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        });
    }

    private void handleItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(mContext, RecyclerViewActivity.class);
                break;
            case 1:
                intent = new Intent(mContext, CustomViewActivity.class);
                break;
        }
        mContext.startActivity(intent);
    }

}
