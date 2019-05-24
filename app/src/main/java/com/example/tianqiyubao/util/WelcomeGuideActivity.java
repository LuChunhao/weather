package com.example.tianqiyubao.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tianqiyubao.MainActivity;
import com.example.tianqiyubao.R;
import com.example.tianqiyubao.map.MapActivity;
import com.example.tianqiyubao.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends AppCompatActivity {
    private Button btn;
    private ViewPager pager;
    private List<View> list;

    private ViewPager vp_guide;
    private List<ImageView> mImgList;//导航图集合
    private LinearLayout ll_container;//小圆点容器
    private int mCurrentIndex = 0;//当前小圆点的位置
    private int[] imgArray = {R.drawable.page, R.drawable.page, R.drawable.page, R.drawable.page};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);

        // 判断用户是否登陆
        boolean isLogin = SharePreferenceUtil.getBoolean("isLogin", false);
        if (!isLogin) { // 未登录先登陆
            startActivity(new Intent(WelcomeGuideActivity.this, LoginActivity.class));
            finish();
        } else {

            int count = SharePreferenceUtil.getInt("count", 0);
            // 如果不是第一次运行，直接跳转到MainActivity页面
            if (count == 1) {
                Intent intent = new Intent();
                intent.setClass(WelcomeGuideActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                //判断程序与第几次运行，如果是第一次运行则显示引导页面
                SharePreferenceUtil.putInt("count", 1);
            }

            ll_container = (LinearLayout) findViewById(R.id.guide_container);
            mImgList = new ArrayList<>();
            for (int i = 0; i < imgArray.length; i++) {
                //获取4个圆点
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(imgArray[i]);
                mImgList.add(imageView);
                ImageView dot = new ImageView(this);
                if (i == mCurrentIndex) {
                    dot.setImageResource(R.drawable.page_now); //设置当前页的圆点
                } else {
                    dot.setImageResource(R.drawable.page); //其余页的圆点
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    params.leftMargin = 10;//设置圆点边距
                }
                dot.setLayoutParams(params);
                ll_container.addView(dot);//将圆点添加到容器中
            }

            init();
            initViewPager();
        }

    }

    public void click() {
        //页面的跳转
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    //初始化
    public void init() {
        list = new ArrayList<View>();
        btn = (Button) findViewById(R.id.welcome_guide_btn);
        pager = (ViewPager) findViewById(R.id.welcome_pager);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                click();
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(WelcomeGuideActivity.this);
//                dialog.setTitle("温馨提示");
//                dialog.setMessage("是否定位到当前城市？");
//                dialog.setCancelable(false);
//                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent i = new Intent();
//                        i.setClass(WelcomeGuideActivity.this, MapActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                });
//                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        click();
//                    }
//                });
//                dialog.create().show();
//            }
//        });

    }

    //初始化ViewPager的方法
    public void initViewPager() {
        ImageView iv1 = new ImageView(this);
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv1.setImageResource(R.drawable.welcome1);

        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.welcome2);
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView iv3 = new ImageView(this);
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv3.setImageResource(R.drawable.welcome3);
        ImageView iv4 = new ImageView(this);
        iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv4.setImageResource(R.drawable.welcome4);
        list.add(iv1);
        list.add(iv2);
        list.add(iv3);
        list.add(iv4);

        pager.setAdapter(new MyPagerAdapter());
        //监听ViewPager滑动效果
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页卡被选中的方法
            @Override
            public void onPageSelected(int arg0) {
                //如果是第四个页面
                if (arg0 == 3) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.GONE);
                }

                //根据监听的页面改变当前页对应的小圆点
                mCurrentIndex = arg0;
                for (int i = 0; i < ll_container.getChildCount(); i++) {
                    ImageView imageView = (ImageView) ll_container.getChildAt(i);
                    if (i == arg0) {
                        imageView.setImageResource(R.drawable.page_now);
                    } else {
                        imageView.setImageResource(R.drawable.page);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    //定义ViewPager的适配器
    class MyPagerAdapter extends PagerAdapter {
        //计算需要多少item显示
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //初始化item实例方法
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        //item销毁的方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 注销父类销毁item的方法，因为此方法并不是使用此方法
//          super.destroyItem(container, position, object);
            container.removeView(list.get(position));
        }
    }
}
