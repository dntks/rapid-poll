package com.appsball.rapidpoll.commons.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appsball.rapidpoll.R;
import com.paging.listview.PagingBaseAdapter;

import java.util.List;

public class PageAwarePagingListView extends ListView {

    public interface Pagingable {
        void onLoadMoreItems();
    }

    private boolean isLoading;
    private boolean hasMoreItems;
    private Pagingable pagingableListener;
    private CircleLoadingView loadingView;

    private OnScrollListener onScrollListener;

    public PageAwarePagingListView(Context context) {
        super(context);
        init();
    }

    public PageAwarePagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageAwarePagingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPagingableListener(Pagingable pagingableListener) {
        this.pagingableListener = pagingableListener;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
        if (!this.hasMoreItems) {
            removeFooterView(loadingView);
        } else if (findViewById(R.id.loading_view) == null) {
            addFooterView(loadingView);
            ListAdapter adapter = ((HeaderViewListAdapter) getAdapter()).getWrappedAdapter();
            setAdapter(adapter);
        }
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }

    private void init() {
        isLoading = false;
        loadingView = new CircleLoadingView(getContext());
        addFooterView(loadingView);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                // Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                    if (pagingableListener != null) {
                        isLoading = true;
                        pagingableListener.onLoadMoreItems();
                    }

                }
            }
        });
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        onScrollListener = listener;
    }

    private int actualPage = 0;

    public void onFinishLoading(boolean hasMoreItems, List<? extends Object> newItems) {
        actualPage++;

        setHasMoreItems(hasMoreItems);
        setIsLoading(false);
        if (newItems != null && newItems.size() > 0) {
            ListAdapter adapter = ((HeaderViewListAdapter) getAdapter()).getWrappedAdapter();
            if (adapter instanceof PagingBaseAdapter) {
                ((PagingBaseAdapter) adapter).addMoreItems(newItems);
            }
        }
    }

    public int getActualPage() {
        return actualPage;
    }
}