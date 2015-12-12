package com.appsball.rapidpoll.commons.view;

import com.paging.listview.PagingBaseAdapter;

import java.util.List;

public abstract class PagingListAdapter<T> extends PagingBaseAdapter<T> {

	public PagingListAdapter() {
		super();
	}

	public PagingListAdapter(List<T> items) {
		super(items);
	}

	public void removeItem(T item) {
		this.items.remove(item);
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		this.items.remove(position);
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public T getItem(int position) {
		return items.get(position);
	}

	public boolean hasAnyItems() {
		return !this.items.isEmpty();
	}
}
