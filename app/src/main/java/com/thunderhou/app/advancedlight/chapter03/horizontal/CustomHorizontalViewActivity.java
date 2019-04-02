package com.thunderhou.app.advancedlight.chapter03.horizontal;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thunderhou.app.advancedlight.R;
import com.thunderhou.app.advancedlight.base.BaseActivity;

public class CustomHorizontalViewActivity extends BaseActivity {

    private ListView lv_one;
    private ListView lv_two;
    private ListView lv_three;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_horizontal_view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        lv_one = findViewById(R.id.lv_one);
        lv_two = findViewById(R.id.lv_two);
        lv_three = findViewById(R.id.lv_three);
        String[] strs1 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,strs1);
        lv_one.setAdapter(adapter1);

        String[] strs2 = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,strs2);
        lv_two.setAdapter(adapter2);

        String[] strs3 = {"Android","Java","Kotlin","Flutter","RxJava","Retrofit","OkHttp","MVP","Glide","iOS","Objective C","Swift","UITableView","Xib","Python","Xcode"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,strs3);
        lv_three.setAdapter(adapter3);
    }
}
