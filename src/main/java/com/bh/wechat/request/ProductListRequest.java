package com.bh.wechat.request;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.GET_PRODUCTS)
public class ProductListRequest extends PaginationRequest {

    private static final long serialVersionUID = 7159225614464754642L;

    @Parameter(required = false)
    private String keyword;

    @Parameter(required = false)
    private String tag;

    @Parameter(required = false)
    private Integer categoryId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
