package com.appsball.rapidpoll.newpoll.listadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.AdapterAnswerViewsUpdater;
import com.appsball.rapidpoll.newpoll.AdapterItemViewRemover;
import com.appsball.rapidpoll.newpoll.NewQuestionCreator;
import com.appsball.rapidpoll.newpoll.PollAnswerToAdapterAdder;
import com.appsball.rapidpoll.newpoll.PollQuestionToAdapterAdder;
import com.appsball.rapidpoll.newpoll.listviewholder.AddAnswerNewPollViewHolder;
import com.appsball.rapidpoll.newpoll.listviewholder.AddQuestionNewPollViewHolder;
import com.appsball.rapidpoll.newpoll.listviewholder.AnswerNewPollViewHolder;
import com.appsball.rapidpoll.newpoll.listviewholder.NewPollViewHolderParent;
import com.appsball.rapidpoll.newpoll.listviewholder.QuestionNewPollViewHolder;
import com.appsball.rapidpoll.newpoll.model.NewPollAddAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.google.common.base.Optional;

import java.util.List;

import static com.appsball.rapidpoll.newpoll.model.ViewType.fromValue;

public class NewPollQuestionsAdapter extends RecyclerView.Adapter<NewPollViewHolderParent> implements PollQuestionToAdapterAdder, AdapterAnswerViewsUpdater, AdapterItemViewRemover, PollAnswerToAdapterAdder {

    private List<NewPollListItem> pollListItems;
    private List<NewPollQuestion> questions;
    private NewQuestionCreator newQuestionCreator;

    public NewPollQuestionsAdapter(List<NewPollQuestion> questions, NewQuestionCreator newQuestionCreator) {
        this.questions = questions;
        this.pollListItems = newQuestionCreator.createItemsFromQuestions(questions);
        this.newQuestionCreator = newQuestionCreator;
    }

    @Override
    public void addAnswerToAdapter(NewPollAnswer newAnswer, NewPollAddAnswer newPollAddAnswer) {
        int location = getLocationOfItem(newPollAddAnswer);
        insertItem(newAnswer, location);
    }

    @Override
    public void addNewQuestion() {
        NewPollQuestion question = newQuestionCreator.createNewQuestion(questions.size() + 1);
        questions.add(question);
        List<NewPollListItem> pollListItems = newQuestionCreator.createItemsFromQuestion(question);
        int position = NewPollQuestionsAdapter.this.pollListItems.size() - 1;
        insertLastItems(pollListItems, position);
    }

    @Override
    public void updateAnswerViews(List<NewPollAnswer> answers) {
        for (NewPollAnswer answer : answers) {
            int location = getLocationOfItem(answer);
            notifyItemChanged(location);
        }
    }

    @Override
    public void removeView(NewPollListItem newPollListItem, View v) {
        int location = getLocationOfItem(newPollListItem);
        pollListItems.remove(location);
        this.notifyItemRemoved(location);
    }


    private Optional<Integer> getOptionalLocationOfItem(NewPollListItem newPollListItem) {
        for (int i = 0; i < pollListItems.size(); i++) {
            if (pollListItems.get(i).equals(newPollListItem)) {
                return Optional.of(i);
            }
        }
        return Optional.absent();
    }

    private int getLocationOfItem(NewPollListItem newPollListItem) {
        for (int i = 0; i < pollListItems.size(); i++) {
            if (pollListItems.get(i).equals(newPollListItem)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return pollListItems.get(position).getViewType().value;
    }

    @Override
    public int getItemCount() {
        return pollListItems.size();
    }

    @Override
    public NewPollViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewType type = fromValue(viewType);
        switch (type) {
            case QUESTION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_question, parent, false);
                return new QuestionNewPollViewHolder(view);
            case ANSWER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_answer, parent, false);
                return new AnswerNewPollViewHolder(view, this, this);
            case ADD_QUESTION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_add_question, parent, false);
                return new AddQuestionNewPollViewHolder(view, this);
            case ADD_ANSWER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_add_answer, parent, false);
                return new AddAnswerNewPollViewHolder(view, this, this);
        }
        return new QuestionNewPollViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(NewPollViewHolderParent holder, int position) {
        holder.bindView(pollListItems.get(position));
    }

    public void insertItem(NewPollListItem item, int position) {
        pollListItems.add(position, item);
        this.notifyItemInserted(position);
    }

    public void insertLastItems(List<NewPollListItem> items, int position) {
        pollListItems.addAll(position, items);
        this.notifyItemRangeInserted(position, items.size());
    }

}