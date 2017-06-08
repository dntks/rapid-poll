package com.appsball.rapidpoll.fillpoll;

import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollAlternativesToDoPollAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollDetailsToDoPollRequestTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollQuestionsToDoPollQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsResponseTransformer;

import dagger.Module;
import dagger.Provides;

@Module
public class FillPollModule {

    @Provides
    RequestCreator provideRequestCreator(){
        return new RequestCreator();
    }

    @Provides
    FillPollDetailsToDoPollRequestTransformer provideFillPollDetailsToDoPollRequestTransformer(){
        return new FillPollDetailsToDoPollRequestTransformer(provideFillPollQuestionsToDoPollQuestionsTransformer());
    }

    @Provides
    FillPollQuestionsToDoPollQuestionsTransformer provideFillPollQuestionsToDoPollQuestionsTransformer(){
        return new FillPollQuestionsToDoPollQuestionsTransformer(provideFillPollAlternativesToDoPollAnswersTransformer());

    }

    @Provides
    FillPollAlternativesToDoPollAnswersTransformer provideFillPollAlternativesToDoPollAnswersTransformer(){
        return new FillPollAlternativesToDoPollAnswersTransformer();
    }

    @Provides
    PollDetailsResponseTransformer providePollDetailsResponseTransformer(){
        return new PollDetailsResponseTransformer(providePollDetailsQuestionsTransformer());
    }

    @Provides
    PollDetailsQuestionsTransformer providePollDetailsQuestionsTransformer(){
        return new PollDetailsQuestionsTransformer(providePollDetailsAnswersTransformer());

    }

    @Provides
    PollDetailsAnswersTransformer providePollDetailsAnswersTransformer(){
        return new PollDetailsAnswersTransformer();
    }
}
