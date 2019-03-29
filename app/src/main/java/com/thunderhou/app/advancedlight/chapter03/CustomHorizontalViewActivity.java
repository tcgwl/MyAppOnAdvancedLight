package com.thunderhou.app.advancedlight.chapter03;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thunderhou.app.advancedlight.R;
import com.thunderhou.app.advancedlight.base.BaseActivity;

public class CustomHorizontalViewActivity extends BaseActivity {

    private ListView lv_one;
    private ListView lv_two;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_horizontal_view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        lv_one=(ListView)this.findViewById(R.id.lv_one);
        lv_two=(ListView)this.findViewById(R.id.lv_two);
        String[] strs1 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,strs1);
        lv_one.setAdapter(adapter1);

        String[] strs2 = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,strs2);
        lv_two.setAdapter(adapter2);
    }
}
