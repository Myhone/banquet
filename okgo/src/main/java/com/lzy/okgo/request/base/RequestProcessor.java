package com.lzy.okgo.request.base;

/**
 * Created by _hxb on 2020/7/10.
 */
public interface RequestProcessor<T, R extends Request> {
    void process(Request<T, R> request);
}
