package com.bawei.week_0112.adapter;

import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }
}
