package com.appsball.rapidpoll.commons.communication.request;

public class PollsRequest {
    public final String userId;
    public final String orderKey;
    public final String orderType;
    public final String pageSize;
    public final String page;

    public PollsRequest(String userId, String orderKey, String orderType, String pageSize, String page) {
        this.userId = userId;
        this.orderKey = orderKey;
        this.orderType = orderType;
        this.pageSize = pageSize;
        this.page = page;
    }

    public static Builder builder(){
        return new Builder();
    }
    
    public static class Builder{
        private String userId;
        private String orderKey;
        private String orderType;
        private String pageSize;
        private String page;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withOrderKey(String orderKey) {
            this.orderKey = orderKey;
            return this;
        }

        public Builder withOrderType(String orderType) {
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
            return new PollsRequest(userId, orderKey, orderType, pageSize, page);
        }
    }
}
