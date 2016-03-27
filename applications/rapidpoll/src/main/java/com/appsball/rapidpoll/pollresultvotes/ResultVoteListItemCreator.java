package com.appsball.rapidpoll.pollresultvotes;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultEmail;
import com.appsball.rapidpoll.pollresult.model.PollResultAnswer;
import com.appsball.rapidpoll.pollresultvotes.model.AnswerListItem;
import com.appsball.rapidpoll.pollresultvotes.model.ResultVotesListItem;
import com.appsball.rapidpoll.pollresultvotes.model.UserListItem;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ResultVoteListItemCreator {

    public ImmutableList<ResultVotesListItem> createResultVoteListItems(List<PollResultAnswer> pollResultAnswers) {
        ImmutableList.Builder<ResultVotesListItem> resultVotesListItems = ImmutableList.builder();
        for (PollResultAnswer resultAnswer : pollResultAnswers) {
            resultVotesListItems.add(new AnswerListItem(resultAnswer.name, resultAnswer.getPercentageString()));
            resultVotesListItems.addAll(createUserElementList(resultAnswer.pollResultEmailList));
        }
        return resultVotesListItems.build();
    }

    private List<ResultVotesListItem> createUserElementList(ImmutableList<PollResultEmail> pollResultEmailList) {
        List<ResultVotesListItem> resultVotesListItems = Lists.newArrayList();
        for(PollResultEmail pollResultEmail : pollResultEmailList){
            String email = pollResultEmail.email;
            if(!StringUtils.isEmpty(email)){
                resultVotesListItems.add(new UserListItem(email));
            }
        }
        return resultVotesListItems;
    }
}
