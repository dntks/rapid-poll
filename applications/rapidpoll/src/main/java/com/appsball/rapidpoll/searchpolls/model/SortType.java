package com.appsball.rapidpoll.searchpolls.model;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;

public enum SortType {
    DATE(OrderKey.DATE, OrderType.DESC, R.id.sort_by_date_button),
    TITLE(OrderKey.TITLE, OrderType.ASC, R.id.sort_by_title_button),
    STATUS(OrderKey.STATUS, OrderType.ASC, R.id.sort_by_status_button),
    VOTES(OrderKey.VOTES, OrderType.DESC, R.id.sort_by_vote_button),
    PUBLICITY(OrderKey.PUBLIC, OrderType.DESC, R.id.sort_by_publicity_button);

    public final OrderKey orderKey;
    public final OrderType orderType;
    public final int viewId;

    SortType(OrderKey orderKey, OrderType orderType, int viewId) {
        this.orderKey = orderKey;
        this.orderType = orderType;
        this.viewId = viewId;
    }
}
