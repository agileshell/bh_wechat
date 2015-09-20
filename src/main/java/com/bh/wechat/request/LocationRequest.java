package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.PathVariable;
import com.bh.wechat.gateway.URI;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年9月5日 下午10:00:06
 */
@URI(uri = ApiUri.GET_LOCATION)
public class LocationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @PathVariable
    private int parentId;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}