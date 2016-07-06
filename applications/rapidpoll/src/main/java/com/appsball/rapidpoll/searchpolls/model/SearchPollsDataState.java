package com.appsball.rapidpoll.searchpolls.model;

public class SearchPollsDataState {
    public static int numberOfRequestedPolls = 25;
    public SortType chosenSortType = SortType.DATE;
    public int actualPage = 1;

    public SearchPollsDataState(SortType chosenSortType) {
        this.chosenSortType = chosenSortType;
    }

    public void setSort(SortType chosenSortType){
        this.chosenSortType = chosenSortType;
    }

}
