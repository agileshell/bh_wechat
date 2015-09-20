package com.bh.wechat.response;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午2:27:23
 */
public class AddressListResponse extends BaseResponse {

    private static final long serialVersionUID = -4165771171814087147L;

    private List<Address> list;

    public List<Address> getList() {
        return list;
    }

    public void setList(List<Address> list) {
        this.list = list;
    }
}
