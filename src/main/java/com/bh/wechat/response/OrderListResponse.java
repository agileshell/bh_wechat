package com.bh.wechat.response;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午1:59:11
 */
public class OrderListResponse extends BaseResponse {

    private static final long serialVersionUID = 5566856238066448262L;

    private List<Order> list;

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }
}
