package com.appsball.rapidpoll.commons.communication.request;

public class ExportPollResultRequest {
    public final String userId;
    public final String pollId;
    public final ExportType exportType;
    public final String code;

    private ExportPollResultRequest(String userId, String pollId, ExportType exportType, String code) {
        this.userId = userId;
        this.pollId = pollId;
        this.exportType = exportType;
        this.code = code;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String userId;
        private String pollId;
        private ExportType exportType;
        private String code;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withExportType(ExportType exportType) {
            this.exportType = exportType;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public ExportPollResultRequest build() {
            return new ExportPollResultRequest(userId, pollId, exportType, code);
        }
    }
}
