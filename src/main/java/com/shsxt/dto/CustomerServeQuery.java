package com.shsxt.dto;

import com.shsxt.base.BaseQuery;
import com.shsxt.constant.CustomerServeStatus;

/**
 * Created by Tony on 2016/8/25.
 */
public class CustomerServeQuery extends BaseQuery {
    private String customer;
    private String overview;
    private String serveType;
    private Integer state;
    private String createTimefrom;
    private String createTimeto;
    private String stateStr;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
        this.setStateStr(CustomerServeStatus.findByStatus(state));
    }

    public String getCreateTimefrom() {
        return createTimefrom;
    }

    public void setCreateTimefrom(String createTimefrom) {
        this.createTimefrom = createTimefrom;
    }

    public String getCreateTimeto() {
        return createTimeto;
    }

    public void setCreateTimeto(String createTimeto) {
        this.createTimeto = createTimeto;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }
}
