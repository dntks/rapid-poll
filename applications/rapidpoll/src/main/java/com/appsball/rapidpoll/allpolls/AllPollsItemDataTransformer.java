package com.appsball.rapidpoll.allpolls;

import android.content.res.Resources;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class AllPollsItemDataTransformer {

    private DateStringFormatter dateStringFormatter;
    private Resources resources;

    public AllPollsItemDataTransformer(DateStringFormatter dateStringFormatter, Resources resources) {
        this.dateStringFormatter = dateStringFormatter;
        this.resources = resources;
    }

    public List<SearchPollsItemData> transformAll(List<PollsResponse> pollsResponses) {
        return Lists.transform(pollsResponses, new Function<PollsResponse, SearchPollsItemData>() {
            @Override
            public SearchPollsItemData apply(PollsResponse input) {
                return transform(input);
            }
        });
    }

    private SearchPollsItemData transform(PollsResponse input) {
        SearchPollsItemData.Builder builder = SearchPollsItemData.builder();
        builder.withAllowComment(input.allow_comment == 1);
        builder.withAnonymous(input.anonymous == 1);
        builder.withAnsweredQuestionsByUser(input.number_of_answered_questions_by_the_user);
        builder.withExpirationDate(input.expiration_date);
        builder.withId(String.valueOf(input.id));
        builder.withIsPublic(input.isPublic == 1);
        builder.withName(input.name);
        builder.withNumberOfQuestions(input.number_of_questions);
        builder.withNumberOfVotes(input.number_of_votes);
        builder.withOwnerId(input.owner_id);
        builder.withPublicationDate(input.publication_date);
        builder.withState(PollState.valueOf(input.state));
        int answeredQuestionsRatioFor100 = calculateAnsweredQuestionRatioFor100(input.number_of_answered_questions_by_the_user, input.number_of_questions);
        builder.withAnsweredQuestionsRatioFor100(answeredQuestionsRatioFor100);
        builder.withPublicatedDaysAgoText(dateStringFormatter.createDaysAgoFormatFromPublishDate(input.publication_date));
        builder.withVotesText(resources.getString(R.string.x_votes, input.number_of_votes));
        return builder.build();
    }

    private int calculateAnsweredQuestionRatioFor100(double answeredQuestions, double allQuestions) {
        double realRatio = 100d * answeredQuestions / allQuestions;
        return (int) realRatio;
    }
}
