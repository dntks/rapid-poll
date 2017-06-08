package com.appsball.rapidpoll.fillpoll;

import dagger.Component;

@Component(
        modules = {
                FillPollModule.class
        }
)
public interface FillPollComponent {
    void inject(FillPollFragment fillPollFragment);
}