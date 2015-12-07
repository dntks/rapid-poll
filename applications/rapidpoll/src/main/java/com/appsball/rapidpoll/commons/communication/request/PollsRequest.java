package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;

import static org.apache.commons.lang3.Validate.notNull;

public class PollsRequest {
    public final String userId;
    public final String listType;
    public final String orderKey;
    public final String orderType;
    public final String pageSize;
    public final String page;

    public PollsRequest(String userId,
                        String listType,
                        String orderKey,
                        String orderType,
                        String pageSize,
                        String page) {
        this.userId = notNull(userId, "userId must not be null" );
        this.listType = notNull(listType, "listType must not be null");
        this.orderKey = notNull(orderKey, "orderKey must not be null");
        this.orderType = notNull(orderType, "orderType must not be null");
        this.pageSize = notNull(pageSize, "pageSize must not be null");
        this.page = notNull(page, "page must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private ListType listType;
        private OrderKey orderKey;
        private OrderType orderType;
        private String pageSize;
        private String page;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withListType(ListType listType) {
            this.listType = listType;
            return this;
        }

        public Builder withOrderKey(OrderKey orderKey) {
            this.orderKey = orderKey;
            return this;
        }

        public Builder withOrderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder withPageSize(String pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder withPage(String page) {
            this.page = page;
            return this;
        }

        public PollsRequest build() {
            return new PollsRequest(userId,
                    listType.name(),
                    orderKey.name(),
                    orderType.name(),
                    pageSize,
                    page);
        }
    }
}
