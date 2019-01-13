package com.bawei.week_0112.customer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.week_0112.R;
import com.bawei.week_0112.adapter.MyShopChilenAdapter;
import com.bawei.week_0112.bean.CartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :
 */
public class CustomerView extends RelativeLayout implements View.OnClickListener{

    private TextView editCar;

    public CustomerView(Context context) {
        super(context);
        init(context);
    }

    public CustomerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context;
        //获取展示视图
        View view = View.inflate(context, R.layout.add_sub_view, null);
        //获取资源ID
        ImageView addIamge = (ImageView) view.findViewById(R.id.add_car);
        ImageView jianIamge = (ImageView) view.findViewById(R.id.jian_car);
        editCar = (TextView) view.findViewById(R.id.edit_shop_car);
        //加号、减号的点击事件
        addIamge.setOnClickListener(this);
        jianIamge.setOnClickListener(this);
        addView(view);
        //数量的改变监听
        editCar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:改变数量
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_car:
                //改变数量，设置数量，改变对象内容，回调，局部刷新
                num++;
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                mMyShopAdapter.notifyItemChanged(position);
                break;
            case R.id.jian_car:
                if (num > 1) {
                    num--;
                } else {
                    toast("亲，不能再少了哦！！！");
                }
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                mMyShopAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    //传递的数据
    private List<CartBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private MyShopChilenAdapter mMyShopAdapter;

    public void setData(MyShopChilenAdapter mMyShopAdapter, List<CartBean.DataBean.ListBean> list, int i) {
        this.list = list;
        this.mMyShopAdapter = mMyShopAdapter;
        position = i;
        num = list.get(i).getNum();
        editCar.setText(num + "");
    }


    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBack();
    }
}
