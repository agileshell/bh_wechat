package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午2:49:42
 */
public class SecondCategory implements Serializable {

    private static final long serialVersionUID = -5499694894135355621L;

    private int categoryId;

    private String name;

    private List<ThirdCategory> child;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ThirdCategory> getChild() {
        return child;
    }

    public void setChild(List<ThirdCategory> child) {
        this.child = child;
    }
}
