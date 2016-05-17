package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsAlternative;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsQuestion;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

public class PollDetailsQuestionsTransformer {
    private final PollDetailsAnswersTransformer answersTransformer;

    public PollDetailsQuestionsTransformer(PollDetailsAnswersTransformer answersTransformer) {
        this.answersTransformer = answersTransformer;
    }

    public List<FillPollQuestion> transformQuestions(List<PollDetailsQuestion> questions) {
        List<FillPollQuestion> fillPollQuestions = Lists.newArrayList();
        for (int i = 0; i < questions.size(); i++) {
            fillPollQuestions.add(transformQuestion(questions.get(i), String.valueOf(i+1)));
        }
        return fillPollQuestions;
    }

    private FillPollQuestion transformQuestion(PollDetailsQuestion input, String orderNumber) {
        FillPollQuestion.Builder builder = FillPollQuestion.builder();
        List<FillPollAlternative> allAnswers = answersTransformer.transformAnswers(input.alternatives);
        builder.addAllAnswers(allAnswers);
        builder.withId(String.valueOf(input.question_id));
        builder.withQuestion(input.question);
        builder.withMultiChoice(input.multichoice == 1);
        builder.withOrderNumber(orderNumber);
        FillPollQuestion fillPollQuestion = builder.build();
        for (FillPollAlternative alternative : fillPollQuestion.allAnswers) {
            alternative.setQuestion(fillPollQuestion);
        }
        List<FillPollAlternative> checkedAnswers = getCheckedAlternatives(input.alternatives, fillPollQuestion.allAnswers);
        fillPollQuestion.getCheckedAnswers().addAll(checkedAnswers);
        return fillPollQuestion;
    }

    private List<FillPollAlternative> getCheckedAlternatives(List<PollDetailsAlternative> alternatives, List<FillPollAlternative> allAnswers) {
        Collection<PollDetailsAlternative> checkedAlternatives = filter(alternatives, new Predicate<PollDetailsAlternative>() {
            @Override
            public boolean apply(PollDetailsAlternative input) {
                return input.current_user_answer.equals("1");
            }
        });
        final Collection<String> checkedIds = transform(checkedAlternatives, new Function<PollDetailsAlternative, String>() {
            @Override
            public String apply(PollDetailsAlternative input) {
                return String.valueOf(input.alternative_id);
            }
        });
        return Lists.newArrayList(filter(allAnswers, new Predicate<FillPollAlternative>() {
            @Override
            public boolean apply(FillPollAlternative input) {
                return checkedIds.contains(input.id);
            }
        }));
    }
}
