package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultAlternative;
import com.appsball.rapidpoll.pollresult.model.PollResultAnswer;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class PollResultAnswerTransformer {

    public List<PollResultAnswer> transformAnswers(List<PollResultAlternative> alternatives) {
        final long countOfAllVotes = calculatedAllVotesCount(alternatives);
        return Lists.transform(alternatives, new Function<PollResultAlternative, PollResultAnswer>() {
            @Override
            public PollResultAnswer apply(PollResultAlternative input) {
                return transformAnswer(input, countOfAllVotes);
            }
        });
    }

    private long calculatedAllVotesCount(List<PollResultAlternative> alternatives) {
        long sumVotes = 0;
        for (PollResultAlternative alternative : alternatives) {
            sumVotes += alternative.count;
        }
        return sumVotes;
    }

    private PollResultAnswer transformAnswer(PollResultAlternative pollResultAlternative, long countOfAllVotes) {
        PollResultAnswer.Builder builder = PollResultAnswer.builder();
        builder.withAlternativeId(pollResultAlternative.alternative_id);
        builder.withAlternativeName(pollResultAlternative.alternative_name);
        builder.withPercentageValue(calculatePercentage(countOfAllVotes, pollResultAlternative.count));
        builder.addEmails(pollResultAlternative.emails);
        return builder.build();
    }

    private float calculatePercentage(long countOfAllVotes, long count) {
        if (count == 0 || countOfAllVotes == 0) {
            return 0;
        } else {
            return (float) count / (float) countOfAllVotes;
        }
    }
}
