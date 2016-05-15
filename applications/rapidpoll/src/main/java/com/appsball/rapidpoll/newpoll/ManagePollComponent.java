package com.appsball.rapidpoll.newpoll;

import dagger.Component;

@Component(
        modules = {
                ManagePollModule.class
        }
)
public interface ManagePollComponent {
    void inject(ManagePollFragment managePollFragment);
}
