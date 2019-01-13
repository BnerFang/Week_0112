package com.bawei.week_0112.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bawei.week_0112.R;
import com.bawei.week_0112.bean.CartBean;
import com.bawei.week_0112.customer.CustomerView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :   商品  适配器
 */
public class MyShopChilenAdapter extends RecyclerView.Adapter<MyShopChilenAdapter.MyViewHolder> {

    private Context mContext;
    private List<CartBean.DataBean.ListBean> mList = new ArrayList<>();

    /**
     * 商品的有参构造
     *
     * @param context
     * @param list
     */
    public MyShopChilenAdapter(Context context, List<CartBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //              商品的展示视图：复选框、标题、价格、自定义View(加减号、数量)
        View view = View.inflate(mContext, R.layout.shop_car_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        //商品的图片
        String url = mList.get(i).getImages().split("\\|")[0].replace("https", "http");
        Uri uri = Uri.parse(url);
        myViewHolder.mImage.setImageURI(uri);
        //商品的标题
        myViewHolder.mTitle.setText(mList.get(i).getTitle());
        //商品的价钱
        myViewHolder.mPrice.setText("￥"+mList.get(i).getPrice() + "");
        //商品的复选框
        //根据我记录的状态，改变勾选
        myViewHolder.mCheckBox.setChecked(mList.get(i).isChecked());
        /**
         * ***************商品的复选框状态监听****************
         *
         */
        //商品的跟商家的有所不同，商品添加了选中改变的监听
        myViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //****1.优先改变自己的状态 (运行一)
                mList.get(i).setChecked(isChecked);
                //回调，目的是告诉activity，有人选中状态被改变
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack();
                }
            }
        });

        //------------------------------------------------------------------
        //设置自定义View里的Edit
        myViewHolder.mCustomShopCarPrice.setData(this, mList, i);
        myViewHolder.mCustomShopCarPrice.setOnCallBack(new CustomerView.CallBackListener() {
            @Override
            public void callBack() {
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomerView mCustomShopCarPrice;
        TextView mTitle, mPrice;
        SimpleDraweeView mImage;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_product);
            mTitle = (TextView) itemView.findViewById(R.id.tv_product_title);
            mPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_product);
            mCustomShopCarPrice = (CustomerView) itemView.findViewById(R.id.custom_product_counter);
        }
    }

    /**
     * ****************商品里定义个方法：**************************
     * *****************商家的所有商品状态改变**********************
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param isSelectAll
     */
    public void selectOrRemoveAll(boolean isSelectAll) {
        //循环商品
        for (CartBean.DataBean.ListBean listBean : mList) {
            listBean.setChecked(isSelectAll);
        }
        //刷新
        notifyDataSetChanged();
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();
    }
}
