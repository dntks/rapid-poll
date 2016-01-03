package com.appsball.rapidpoll.fillpoll;

import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;
import com.google.common.collect.Lists;

import java.util.List;

public class FillPollListItemCreator {

    public List<FillPollListItem> createItemsFromDetails(FillPollDetails fillPollDetails) {
        List<FillPollListItem> listItems = Lists.newArrayList();
        for (FillPollQuestion question : fillPollDetails.questions) {
            listItems.addAll(createItemsFromQuestion(question));
        }
        if (fillPollDetails.comment.isPresent()) {
            listItems.add(fillPollDetails.comment.get());
        }
        return listItems;
    }

    public List<FillPollListItem> createItemsFromQuestion(FillPollQuestion question) {
        List<FillPollListItem> listItems = Lists.newArrayList();
        List<FillPollAlternative> answers = question.allAnswers;
        listItems.add(question);
        listItems.addAll(answers);
        return listItems;
    }
}
