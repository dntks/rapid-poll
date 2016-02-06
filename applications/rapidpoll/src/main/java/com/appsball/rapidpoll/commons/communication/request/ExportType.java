package com.appsball.rapidpoll.commons.communication.request;

public enum ExportType {
    PDF("pdf"), XLS("csv");

    public String fileFormatString;

    ExportType(String fileFormatString) {
        this.fileFormatString = fileFormatString;
    }
}
