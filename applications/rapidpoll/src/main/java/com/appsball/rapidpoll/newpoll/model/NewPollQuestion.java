package com.appsball.rapidpoll.newpoll.model;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

public class NewPollQuestion extends NewPollListItem{

    private boolean isMultichoice;
    private String question;
    private Optional<String> id = Optional.absent();
    private List<NewPollAnswer> answers;

    public NewPollQuestion(String question) {
        this.question = question;
        answers= new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void textChanged(String text) {
        this.question = text;
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

    public void setId(String id) {
        this.id = Optional.fromNullable(id);
    }

    public Optional<String> getId() {
        return id;
    }
}
