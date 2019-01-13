package com.bawei.week_0112.mvp.presenter;

import com.bawei.week_0112.mvp.MyCallBack;
import com.bawei.week_0112.mvp.model.MyModel;
import com.bawei.week_0112.mvp.view.MyView;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyPresenter {
    private MyView mMyView;
    private MyModel mMyModel;

    public MyPresenter(MyView myView) {
        mMyView = myView;
        mMyModel = new MyModel();
    }

    //get方法
    public void onGetDatas(String url, Class clazz) {
        mMyModel.onGetData(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mMyView.onMySuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mMyView.onMyFailed(error);
            }
        });
    }

    public void onDetached() {
        if (mMyModel != null) {
            mMyModel = null;
        }
        if (mMyView != null) {
            mMyView = null;
        }
    }
}
