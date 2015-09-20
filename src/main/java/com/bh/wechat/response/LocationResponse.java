package com.bh.wechat.response;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年9月5日 下午10:03:16
 */
public class LocationResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;
    
    private List<Location> list;

    public List<Location> getList() {
        return list;
    }

    public void setList(List<Location> list) {
        this.list = list;
    }
}