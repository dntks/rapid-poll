package com.appsball.rapidpoll.newpoll.listadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.NewQuestionCreator;
import com.appsball.rapidpoll.newpoll.adapterhelper.AdapterAnswerViewsUpdater;
import com.appsball.rapidpoll.newpoll.adapterhelper.AdapterItemViewRemover;
import com.appsball.rapidpoll.newpoll.adapterhelper.PollAnswerToAdapterAdder;
import com.appsball.rapidpoll.newpoll.adapterhelper.PollQuestionToAdapterAdder;
import com.appsball.rapidpoll.newpoll.adapterhelper.QuestionItemRemover;
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
import static com.google.common.collect.Lists.newArrayList;

public class NewPollQuestionsAdapter extends RecyclerView.Adapter<NewPollViewHolderParent> implements PollQuestionToAdapterAdder, AdapterAnswerViewsUpdater, AdapterItemViewRemover, PollAnswerToAdapterAdder, QuestionItemRemover {

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
        NewPollQuestion question = newQuestionCreator.createNewQuestion();
        questions.add(question);
        List<NewPollListItem> pollListItems = newQuestionCreator.createItemsFromQuestion(question);
        int position = NewPollQuestionsAdapter.this.pollListItems.size() - 1;
        insertLastItems(pollListItems, position);
        if(questions.size()==2){
            notifyNewPollItemChanged(questions.get(0));
            notifyNewPollItemChanged(questions.get(1));
        }
    }

    @Override
    public void updateAnswerViews(List<NewPollAnswer> answers) {
        for (NewPollAnswer answer : answers) {
            notifyNewPollItemChanged(answer);
        }
    }

    private void notifyNewPollItemChanged(NewPollListItem newPollListItem) {
        int location = getLocationOfItem(newPollListItem);
        notifyItemChanged(location);
    }

    @Override
    public void removeView(NewPollListItem newPollListItem) {
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

    private List<Integer> getLocationsOfItems(List<? extends NewPollListItem> newPollListItems) {
        List<Integer> locations = newArrayList();
        for (int i = 0; i < pollListItems.size(); i++) {
            if (newPollListItems.contains(pollListItems.get(i))) {
                locations.add(i);
            }
        }
        return locations;
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
                return new QuestionNewPollViewHolder(view, this);
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
        return new QuestionNewPollViewHolder(parent, this);
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

    @Override
    public void removeQuestion(NewPollQuestion newPollQuestion) {
        List<NewPollAnswer> answers = newPollQuestion.getAnswers();
        int location = getLocationOfItem(newPollQuestion);
        List<Integer> locationsOfItems = getLocationsOfItems(answers);
        pollListItems.remove(location + answers.size() + 1);
        pollListItems.remove(location);
        pollListItems.removeAll(answers);
        this.notifyItemRangeRemoved(location, answers.size() + 2);
        newPollQuestion.getAnswers().clear();
        questions.remove(newPollQuestion);
        notifyIfLastQuestion();
    }

    @Override
    public boolean isOnlyQuestionRemaining(NewPollQuestion newPollQuestion) {
        return questions.size() == 1 && questions.get(0).equals(newPollQuestion);
    }

    private void notifyIfLastQuestion() {
        if (questions.size() == 1) {
            notifyNewPollItemChanged(questions.get(0));
        }
    }
}