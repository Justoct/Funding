package com.hualxx.crowd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一整个项目中ajax请求返回的结果（也可以用于分布式架构各个模块间调用时返回统一类型）
 *
 * @author hual
 * @create 2020-11-11 23:26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";

    public static final String FAILED = "FAILED";

    //用来封装当前处理的结果是成功还是失败
    private String result;

    //请求处理失败时返回的错误信息
    private String message;

    //要返回的数据
    private T data;

    /**
     * 请求处理成功不需要返回数据时使用的工具方法
     *
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData() {
        return new ResultEntity<>(SUCCESS, null, null);
    }

    /**
     * 请求处理成功需要返回数据时使用的方法
     *
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<>(SUCCESS, null, data);
    }

    public static <Type> ResultEntity<Type> failed(String message) {
        return new ResultEntity<>(FAILED, message, null);
    }


}
