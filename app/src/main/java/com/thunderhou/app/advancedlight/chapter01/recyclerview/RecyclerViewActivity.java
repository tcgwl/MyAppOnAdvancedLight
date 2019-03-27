package com.thunderhou.app.advancedlight.chapter01.recyclerview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.thunderhou.app.advancedlight.R;
import com.thunderhou.app.advancedlight.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {

    private List<String> mList;
    private HomeAdapter mHomeAdaper;
    private RecyclerView mRecyclerView;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            mList.add(i + "");
        }
    }

    @Override
    protected void initView() {
        mRecyclerView= findViewById(R.id.id_recyclerview);
        //设置GridView
        //setGridView();
        //设置ListView
        //setListView();
        //设置瀑布流
        setWaterfallView();
    }

    public void setListView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(RecyclerViewActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdaper = new HomeAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mHomeAdaper);
    }
    public void setGridView(){
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdaper = new HomeAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mHomeAdaper);
    }

    public void setWaterfallView(){
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mStaggeredHomeAdapter = new StaggeredHomeAdapter(this, mList);
        mRecyclerView.setAdapter(mStaggeredHomeAdapter);
    }

    private void setLister(){
        mHomeAdaper.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击第" + (position + 1) + "条", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(RecyclerViewActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mHomeAdaper.removeData(position);
                            }
                        })
                        .show();
            }
        });
    }
}
