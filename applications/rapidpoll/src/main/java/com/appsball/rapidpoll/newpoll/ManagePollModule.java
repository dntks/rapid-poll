package com.appsball.rapidpoll.newpoll;

import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.newpoll.model.PollSettings;
import com.appsball.rapidpoll.newpoll.transformer.ManagePollQuestionAlternativeTransformer;
import com.appsball.rapidpoll.newpoll.transformer.ManagePollQuestionTransformer;
import com.appsball.rapidpoll.newpoll.transformer.NewPollQuestionsTransformer;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagePollModule {

    @Provides
    NewQuestionCreator provideNewQuestionCreator(){
    return new NewQuestionCreator();
    }

    @Provides
    RequestCreator provideRequestCreator(){
    return new RequestCreator();
    }

    @Provides
    PollSettings providePollSettings(){
    return new PollSettings();
    }

    @Provides
    ManagePollQuestionTransformer provideManagePollQuestionTransformer(){
    return new ManagePollQuestionTransformer(provideManagePollQuestionAlternativeTransformer());
    }


    @Provides
    ManagePollQuestionAlternativeTransformer provideManagePollQuestionAlternativeTransformer(){
    return new ManagePollQuestionAlternativeTransformer();
    }


    @Provides
    NewPollQuestionsTransformer provideNewPollQuestionsTransformer(){
    return new NewPollQuestionsTransformer();
    }

}
