package com.bawei.week_0112.mvp.model;

import com.bawei.week_0112.mvp.MyCallBack;
import com.bawei.week_0112.utils.RetrofitManager;
import com.google.gson.Gson;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :  MyModel 层
 */
public class MyModel {

    public void onGetData(String url, final Class clazz, final MyCallBack callBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callBack != null) {
                    callBack.onFailed(error);
                }
            }
        });
    }
}
