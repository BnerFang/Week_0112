package com.bawei.week_0112.mvp.view;

/**
 * @author : FangShiKang
 * @date : 2019/01/12.
 * email : fangshikang@outlook.com
 * desc :   view    层
 */
public interface MyView<T> {
    void onMySuccess(T data);
    void onMyFailed(String error);
}
