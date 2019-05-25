package com.example.tianqiyubao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianqiyubao.R;
import com.example.tianqiyubao.WeatherActivity;
import com.example.tianqiyubao.database.GreenDaoManager;
import com.example.tianqiyubao.database.entity.CityEntity;
import com.example.tianqiyubao.database.gen.CityEntityDao;
import com.example.tianqiyubao.util.SharePreferenceUtil;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        listView = (ListView) findViewById(R.id.list_view);
        textView = (TextView) findViewById(R.id.tv_no_data);
        initData();
    }

    private void initData() {
        Query<CityEntity> query = GreenDaoManager.getInstance().getSession().getCityEntityDao()
                .queryBuilder()
                //.where(CityEntityDao.Properties.UserName.notEq(null))
                .where(CityEntityDao.Properties.UserName.eq(SharePreferenceUtil.getString("userName", "")))
                .orderDesc(CityEntityDao.Properties.Id)
                .build();
        final List<CityEntity> cityEntityList =  query.list();
        if (cityEntityList.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            for (CityEntity entity : cityEntityList) {
                if (entity.getCityName()==null) continue;
                dataList.add(entity.getCityName());
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("weatherId", cityEntityList.get(position).getWeatherId());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        //Toast.makeText(FavoriteListActivity.this, "list==" + cityEntityList.size(), Toast.LENGTH_SHORT).show();
    }
}
