package com.bawei.week_0112.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.week_0112.R;
import com.bawei.week_0112.activity.CustomerActivity;
import com.bawei.week_0112.adapter.MyShopAdapter;
import com.bawei.week_0112.api.Apis;
import com.bawei.week_0112.bean.CartBean;
import com.bawei.week_0112.mvp.presenter.MyPresenter;
import com.bawei.week_0112.mvp.view.MyView;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :   购物车
 */
public class CartFragment extends Fragment implements MyView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.iv_cricle)
    CheckBox mIvCricle;
    @BindView(R.id.txt_all)
    TextView mTxtAll;
    @BindView(R.id.all_price)
    TextView mAllPrice;
    @BindView(R.id.sum_price_txt)
    TextView mSumPriceTxt;
    @BindView(R.id.layout_top)
    RelativeLayout mLayoutTop;
    @BindView(R.id.layout_all)
    RelativeLayout mLayoutAll;
    @BindView(R.id.sum_price)
    RelativeLayout mSumPrice;
    @BindView(R.id.layout_buttom)
    RelativeLayout mLayoutButtom;
    @BindView(R.id.main_bj)
    TextView mMainBj;
    private Unbinder unbinder;
    private MyPresenter mMyPresenter;
    private MyShopAdapter mMyShopAdapter;
    private List<CartBean.DataBean> mBeanData;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMyPresenter = new MyPresenter(this);
        mMyPresenter.onGetDatas(Apis.CART_GET_URL, CartBean.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        //RecyclerView的布局格式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        GroupListener groupListener = new GroupListener() {
            @Override
            public String getGroupName(int position) {
                //获取分组名
                return mBeanData.get(position).getSellerName();
            }
        };
        StickyDecoration decoration = StickyDecoration.Builder
                .init(groupListener)
                //重置span（使用GridLayoutManager时必须调用）
                //.resetSpan(mRecyclerView, (GridLayoutManager) manager)
                .build();

        //设置适配器
        mMyShopAdapter = new MyShopAdapter(getContext());
        mRecyclerview.setAdapter(mMyShopAdapter);
        mRecyclerview.addItemDecoration(decoration);

        /**
         * 选中全部商家，全选/全不选按钮选中
         * 根据选中的商品数量和所有的商品数量比较判断
         */
        //*****1.回调商家适配器里的接口
        mMyShopAdapter.setListener(new MyShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<CartBean.DataBean> list) {
                //在这里重新遍历已经改状态后的数据，
                // 这里不能break跳出，因为还需要计算后面点击商品的价格和数目，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int a = 0; a < list.size(); a++) {
                    //获取商家里商品
                    List<CartBean.DataBean.ListBean> listAll = list.get(a).getList();
                    //*****2.循环商品集合
                    for (int i = 0; i < listAll.size(); i++) {
                        //***3.得到所有商品的总数
                        totalNum = totalNum + listAll.get(i).getNum();
                        //****4.如果有商品选中----取选中的状态
                        if (listAll.get(i).isChecked()) {
                            //选中的商品价格
                            totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                            //选中商品的数量
                            num = num + listAll.get(i).getNum();
                        }
                    }
                }
                //****5.如果选中商品的数量<商品的总数量
                if (num < totalNum) {
                    //不是全部选中
                    mIvCricle.setChecked(false);
                } else {
                    //是全部选中
                    mIvCricle.setChecked(true);
                }
                //*****6.将值设置
                mAllPrice.setText("合计：" + totalPrice);
                mSumPriceTxt.setText("去结算(" + num + ")");
            }
        });
    }

    @Override
    public void onMySuccess(Object data) {
        if (data instanceof CartBean) {
            CartBean bean = (CartBean) data;
            if (bean.getCode().equals("0")) {
                mBeanData = bean.getData();
                if (mBeanData != null) {
                    mBeanData.remove(0);
                    mMyShopAdapter.setList(mBeanData);
                    mMyShopAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onMyFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.iv_cricle, R.id.main_bj})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_cricle:
                checkSeller(mIvCricle.isChecked());
                //刷新商家适配器
                mMyShopAdapter.notifyDataSetChanged();
                break;
            case R.id.main_bj:
                startActivity(new Intent(getActivity(), CustomerActivity.class));
                break;
        }
    }

    /**
     * 全选/全不选复选框选中
     * 1.所有商家的复选框选中
     * 2.所有的商品复选框选中
     * 修改选中状态，获取价格和数量
     */
    private void checkSeller(boolean bool) {
        double totalPrice = 0;
        int num = 0;
        for (int a = 0; a < mBeanData.size(); a++) {
            //****1.遍历商家，改变状态---设置商家状态为全选中
            CartBean.DataBean dataBean = mBeanData.get(a);
            dataBean.setChecked(bool);
            //得到所有的商品
            List<CartBean.DataBean.ListBean> listAll = mBeanData.get(a).getList();
            for (int i = 0; i < listAll.size(); i++) {
                //****2.遍历商品，改变状态---设置商家的商品全部选中
                listAll.get(i).setChecked(bool);
                //计算总价
                totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                //计算总数量
                num = num + listAll.get(i).getNum();
            }
        }

        if (bool) {
            mAllPrice.setText("合计：" + totalPrice);
            mSumPriceTxt.setText("去结算(" + num + ")");
        } else {
            mAllPrice.setText("合计：0.00");
            mSumPriceTxt.setText("去结算(0)");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mMyPresenter.onDetached();
    }
}
