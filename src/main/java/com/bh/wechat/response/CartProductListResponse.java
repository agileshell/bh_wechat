package com.bh.wechat.response;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午2:02:10
 */
public class CartProductListResponse extends BaseResponse {

    private static final long serialVersionUID = -1459295682116649935L;

    private List<CartProduct> list;

    public List<CartProduct> getList() {
        return list;
    }

    public void setList(List<CartProduct> list) {
        this.list = list;
    }
}
