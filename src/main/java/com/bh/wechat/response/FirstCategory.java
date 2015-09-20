package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午2:48:40
 */
public class FirstCategory implements Serializable {

    private static final long serialVersionUID = 2770796110788318340L;

    private int categoryId;

    private String name;

    private List<SecondCategory> child;

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

    public List<SecondCategory> getChild() {
        return child;
    }

    public void setChild(List<SecondCategory> child) {
        this.child = child;
    }
}
