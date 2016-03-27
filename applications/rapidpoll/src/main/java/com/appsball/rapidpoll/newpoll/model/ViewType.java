package com.appsball.rapidpoll.newpoll.model;

public enum ViewType {
    QUESTION(0),
    ANSWER(1),
    ADD_QUESTION(2),
    ADD_ANSWER(3),
    COMMENT(4),
    USER(5);

    public int value;

    ViewType(int value) {
        this.value = value;
    }

    public static ViewType fromValue(int value){
        switch (value){
            case 1:
                return ANSWER;
            case 2:
                return ADD_QUESTION;
            case 3:
                return ADD_ANSWER;
            case 4:
                return COMMENT;
            case 5:
                return USER;
            case 0:
            default:
                return QUESTION;
        }
    }
}
