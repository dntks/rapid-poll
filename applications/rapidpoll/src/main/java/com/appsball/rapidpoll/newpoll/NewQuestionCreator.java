package com.appsball.rapidpoll.newpoll;

import com.appsball.rapidpoll.newpoll.model.NewPollAddAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollAddQuestion;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.google.common.collect.Lists;

import java.util.List;

public class NewQuestionCreator {

    public NewPollQuestion createNewQuestion(int questionNumber){
        NewPollQuestion question = new NewPollQuestion("Question "+questionNumber);
        List<NewPollAnswer> answers = question.getAnswers();
        answers.add(new NewPollAnswer("Alternative 1", question));
        answers.add(new NewPollAnswer("Alternative 2", question));
        return question;
    }

    public List<NewPollListItem> createItemsFromQuestions(List<NewPollQuestion> questions) {
        List<NewPollListItem> listItems = Lists.newArrayList();
        for(NewPollQuestion question : questions){
            listItems.addAll(createItemsFromQuestion(question));
        }
        listItems.add(new NewPollAddQuestion(""));
        return listItems;
    }

    public List<NewPollListItem> createItemsFromQuestion(NewPollQuestion question) {
        List<NewPollListItem> listItems = Lists.newArrayList();
        List<NewPollAnswer> answers = question.getAnswers();
        listItems.add(question);
        listItems.addAll(answers);
        listItems.add(new NewPollAddAnswer("", question));
        return listItems;
    }
}
