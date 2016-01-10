package com.appsball.rapidpoll.searchpolls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.marshalchen.ultimaterecyclerview.SwipeableUltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;


public abstract class SimpleAdapter<T, IH extends UltimateRecyclerviewViewHolder> extends SwipeableUltimateViewAdapter {
    protected List<T> items;

    public SimpleAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public int getAdapterItemCount() {
        return items.size();
    }


    public void insert(T item, int position) {
        insert(items, item, position);
    }

    public void insertAll(List<T> items, int position) {
        this.items.addAll(position, items);
        if (customHeaderView != null) {
            position++;
        }
        notifyItemRangeInserted(position, items.size());

    }

    public void remove(int position) {
        remove(items, position);
    }

    public void clear() {
        clear(items);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    public void swapPositions(int from, int to) {
        swapPositions(items, from, to);
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.allpolls_layout, viewGroup, false);
        return new RecyclerView.ViewHolder(new View(viewGroup.getContext())) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        swapPositions(fromPosition, toPosition);
        super.onItemMove(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        remove(position);
        super.onItemDismiss(position);
    }

    public class SimpleAdapterViewHolder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                textViewSample = (TextView) itemView.findViewById(
                        R.id.pollitem_name);
            }

        }

        @Override
        public void onItemSelected() {
        }

        @Override
        public void onItemClear() {
        }
    }

    public void removeAllItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public abstract T getItem(int position);
/*
        if (customHeaderView != null)
            position--;
        if (position < items.size())
            return items.get(position);
        else return "";
 */
}
