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
        this.userId = notNull("userId must not be null", userId);
        this.listType = notNull("listType must not be null", listType);
        this.orderKey = notNull("orderKey must not be null", orderKey);
        this.orderType = notNull("orderType must not be null", orderType);
        this.pageSize = notNull("pageSize must not be null", pageSize);
        this.page = notNull("page must not be null", page);
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
