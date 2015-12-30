package com.appsball.rapidpoll.newpoll;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;

import java.util.List;

import static com.appsball.rapidpoll.newpoll.ViewType.fromValue;

public class NewPollQuestionsAdapter extends RecyclerView.Adapter<NewPollQuestionsAdapter.ViewHolderParent> {

    private List<NewPollListItem> items;
    private List<NewPollQuestion> questions;
    private NewQuestionCreator newQuestionCreator;

    public NewPollQuestionsAdapter(List<NewPollQuestion> questions, NewQuestionCreator newQuestionCreator) {
        this.questions = questions;
        this.items = newQuestionCreator.createItemsFromQuestions(questions);
        this.newQuestionCreator = newQuestionCreator;
    }

    abstract class ViewHolderParent extends RecyclerView.ViewHolder {
        public ViewHolderParent(View parent) {
            super(parent);
        }

        public abstract void bindView(NewPollListItem newPollListItem);
    }

    class QuestionViewHolder extends ViewHolderParent {
        EditText editText;

        public QuestionViewHolder(View parent) {
            super(parent);
            editText = (EditText) parent.findViewById(R.id.question_edit_text);
        }

        @Override
        public void bindView(final NewPollListItem newPollListItem) {
            NewPollQuestion newPollQuestion = (NewPollQuestion) newPollListItem;
            editText.setText(newPollQuestion.text);
        }

    }

    private void removeView(NewPollListItem newPollListItem, View v) {

        int location = getLocation(newPollListItem);
        items.remove(location);
        this.notifyItemRemoved(location);
    }

    class AddQuestionViewHolder extends ViewHolderParent {

        public AddQuestionViewHolder(View parent) {
            super(parent);
        }

        @Override
        public void bindView(final NewPollListItem newPollListItem) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPollQuestion question = newQuestionCreator.createNewQuestion(questions.size()+1);
                    questions.add(question);
                    List<NewPollListItem> pollListItems = newQuestionCreator.createItemsFromQuestion(question);
                    List<NewPollAnswer> answers = question.getAnswers();
                    NewPollAnswer answer1 = new NewPollAnswer("", question);
                    NewPollAnswer answer2 = new NewPollAnswer("", question);
                    answers.add(answer1);
                    answers.add(answer2);
                    insertItem(question, items.size() - 1);
                    insertItem(answer1, items.size() - 1);
                    insertItem(answer2, items.size() - 1);
                    insertItem(new NewPollAddAnswer("", question), items.size() - 1);
                }
            });
        }
    }

    class AnswerViewHolder extends ViewHolderParent {
        EditText editText;
        ImageView deleteButton;

        public AnswerViewHolder(View parent) {
            super(parent);
            editText = (EditText) itemView.findViewById(R.id.answer_edit_text);
            deleteButton = (ImageView) parent.findViewById(R.id.delete_button);
        }

        @Override
        public void bindView(NewPollListItem newPollListItem) {
            final NewPollAnswer newPollAnswer = (NewPollAnswer) newPollListItem;
            editText.setText(newPollListItem.text);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeView(newPollAnswer, v);
                }
            });
        }
    }

    class AddAnswerViewHolder extends ViewHolderParent {

        public AddAnswerViewHolder(View parent) {
            super(parent);
        }

        @Override
        public void bindView(final NewPollListItem newPollListItem) {
            final NewPollAddAnswer newPollAddAnswer = (NewPollAddAnswer) newPollListItem;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int location = getLocation(newPollListItem);
                    insertItem(new NewPollAnswer("", newPollAddAnswer.question), location);
                }
            });
        }
    }

    private int getLocation(NewPollListItem newPollListItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(newPollListItem)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType().value;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewType type = fromValue(viewType);
        switch (type) {
            case QUESTION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_question, parent, false);
                return new QuestionViewHolder(view);
            case ANSWER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_answer, parent, false);
                return new AnswerViewHolder(view);
            case ADD_QUESTION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_add_question, parent, false);
                return new AddQuestionViewHolder(view);
            case ADD_ANSWER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_poll_add_answer, parent, false);
                return new AddAnswerViewHolder(view);
        }
        return new QuestionViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolderParent holder, int position) {
        holder.bindView(items.get(position));
    }

    public void insertItem(NewPollListItem item, int position) {
        items.add(position, item);
        this.notifyItemInserted(position);
    }

    public void insertItems(List<NewPollListItem> items, int position) {
        items.addAll(position, items);
        this.notifyItemRangeInserted(position, items.size());
    }
}