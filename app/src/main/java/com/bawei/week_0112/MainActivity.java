package com.bawei.week_0112;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bawei.week_0112.activity.CustomerActivity;
import com.bawei.week_0112.adapter.TabFragmentPagerAdapter;
import com.bawei.week_0112.fragment.CartFragment;
import com.bawei.week_0112.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_vp)
    ViewPager mMainVp;
    @BindView(R.id.main_btn_cart)
    RadioButton mMainBtnCart;
    @BindView(R.id.main_btn_my)
    RadioButton mMainBtnMy;
    @BindView(R.id.main_grp)
    RadioGroup mMainGrp;
    private List<Fragment> list;
    private TabFragmentPagerAdapter mTabFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new CartFragment());
        list.add(new MyFragment());
        //滑动监听
        mMainVp.setOnPageChangeListener(new MyPagerChangeListener());
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        mMainVp.setAdapter(mTabFragmentPagerAdapter);
        mMainVp.setCurrentItem(0);  //初始化显示第一个页面
    }

    @OnClick({R.id.main_btn_cart, R.id.main_btn_my})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_btn_cart:
                mMainVp.setCurrentItem(0);
                break;
            case R.id.main_btn_my:
                mMainVp.setCurrentItem(1);
                break;
        }
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    mMainBtnCart.setChecked(true);
                    mMainBtnMy.setChecked(false);
                    break;
                case 1:
                    mMainBtnCart.setChecked(false);
                    mMainBtnMy.setChecked(true);
                    break;
            }
        }
    }
}
