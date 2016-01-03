package com.appsball.rapidpoll.fillpoll.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Set;

public class FillPollQuestion extends FillPollListItem {
    public final String id;
    public final String question;
    public final boolean isMultiChoice;
    public final ImmutableList<FillPollAlternative> allAnswers;
    private Set<FillPollAlternative> checkedAnswers;

    private FillPollQuestion(String id,
                             String question,
                             boolean isMultiChoice,
                             ImmutableList<FillPollAlternative> allAnswers,
                             Set<FillPollAlternative> checkedAnswers) {
        this.isMultiChoice = isMultiChoice;
        this.id = Validate.notNull(id, "id must not be null!");
        this.question = Validate.notNull(question,"question must not be null!");
        this.allAnswers = allAnswers;
        this.checkedAnswers = checkedAnswers;
    }

    public Set<FillPollAlternative> getCheckedAnswers() {
        return checkedAnswers;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.QUESTION;
    }

    public static class Builder {

        private String id;
        private String question;
        private boolean isMultiChoice;
        private ImmutableList.Builder<FillPollAlternative> allAnswersBuilder = ImmutableList.builder();
        private Set<FillPollAlternative> checkedAnswers = Sets.newHashSet();

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withQuestion(String question) {
            this.question = question;
            return this;
        }


        public Builder withMultChoice(boolean isMultiChoice) {
            this.isMultiChoice = isMultiChoice;
            return this;
        }

        public Builder addAllAnswers(Iterable<FillPollAlternative> allAnswers) {
            allAnswersBuilder.addAll(allAnswers);
            return this;
        }

        public Builder addAllAnswers(FillPollAlternative... allAnswers) {
            allAnswersBuilder.add(allAnswers);
            return this;
        }

        public Builder addCheckedAnswers(Collection<FillPollAlternative> checkedAnswers) {
            this.checkedAnswers.addAll(checkedAnswers);
            return this;
        }

        public FillPollQuestion build() {
            return new FillPollQuestion(id, question, isMultiChoice, allAnswersBuilder.build(), checkedAnswers);
        }
    }
}
