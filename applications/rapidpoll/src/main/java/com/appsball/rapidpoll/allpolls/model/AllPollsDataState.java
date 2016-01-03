package com.appsball.rapidpoll.allpolls.model;

import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;

public class AllPollsDataState {
    public static int numberOfRequestedPolls = 10;
    public OrderType chosenOrderType = OrderType.DESC;
    public OrderKey chosenOrderKey = OrderKey.DATE;
    public int actualPage = 1;

    public void setDateSort(){
        chosenOrderType = OrderType.DESC;
        chosenOrderKey = OrderKey.DATE;
    }

    public void setTitleSort(){
        chosenOrderType = OrderType.ASC;
        chosenOrderKey = OrderKey.TITLE;
    }

    public void setStatusSort(){
        chosenOrderType = OrderType.ASC;
        chosenOrderKey = OrderKey.STATUS;
    }

    public void setVotesSort(){
        chosenOrderType = OrderType.DESC;
        chosenOrderKey = OrderKey.VOTES;
    }

    public void setPublicitySort(){
        chosenOrderType = OrderType.DESC;
        chosenOrderKey = OrderKey.PUBLIC;
    }


}
