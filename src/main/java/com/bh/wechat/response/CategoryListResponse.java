package com.bh.wechat.response;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午2:53:10
 */
public class CategoryListResponse extends BaseResponse {

    private static final long serialVersionUID = -1880243040911147637L;

    private List<FirstCategory> list;

    public List<FirstCategory> getList() {
        return list;
    }

    public void setList(List<FirstCategory> list) {
        this.list = list;
    }
}
