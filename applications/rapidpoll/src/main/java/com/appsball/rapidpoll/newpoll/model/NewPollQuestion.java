package com.appsball.rapidpoll.newpoll.model;

import java.util.ArrayList;
import java.util.List;

public class NewPollQuestion extends NewPollListItem{

    private boolean isMultichoice;
    private List<NewPollAnswer> answers;

    public NewPollQuestion(String text) {
        super(text);
        answers= new ArrayList<>();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.QUESTION;
    }

    public boolean isMultichoice() {
        return isMultichoice;
    }

    public void setMultichoice(boolean isMultichoice) {
        this.isMultichoice = isMultichoice;
    }

    public List<NewPollAnswer> getAnswers() {
        return answers;
    }

    public void addAnswer(int location, NewPollAnswer answer) {
        this.answers.add(location, answer);
    }

    public void removeAnswer(NewPollAnswer answer){
        answers.remove(answer);
    }

}
