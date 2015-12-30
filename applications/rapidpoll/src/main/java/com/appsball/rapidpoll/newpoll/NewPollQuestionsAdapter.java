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

    private List<NewPollListItem> pollListItems;
    private List<NewPollQuestion> questions;
    private NewQuestionCreator newQuestionCreator;

    public NewPollQuestionsAdapter(List<NewPollQuestion> questions, NewQuestionCreator newQuestionCreator) {
        this.questions = questions;
        this.pollListItems = newQuestionCreator.createItemsFromQuestions(questions);
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

    class AddQuestionViewHolder extends ViewHolderParent {

        public AddQuestionViewHolder(View parent) {
            super(parent);
        }

        @Override
        public void bindView(final NewPollListItem newPollListItem) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPollQuestion question = newQuestionCreator.createNewQuestion(questions.size() + 1);
                    questions.add(question);
                    List<NewPollListItem> pollListItems = newQuestionCreator.createItemsFromQuestion(question);
                    int position = NewPollQuestionsAdapter.this.pollListItems.size() - 1;
                    insertLastItems(pollListItems, position);
                }
            });
        }
    }

    class AnswerViewHolder extends ViewHolderParent {
        EditText editText;
        ImageView deleteButton;
        View listitemAnswerSeparator;

        public AnswerViewHolder(View parent) {
            super(parent);
            editText = (EditText) itemView.findViewById(R.id.answer_edit_text);
            deleteButton = (ImageView) parent.findViewById(R.id.delete_button);
            listitemAnswerSeparator = parent.findViewById(R.id.listitem_answer_separator);
        }

        @Override
        public void bindView(NewPollListItem newPollListItem) {
            final NewPollAnswer newPollAnswer = (NewPollAnswer) newPollListItem;
            editText.setText(newPollListItem.text);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newPollAnswer.question.removeAnswer(newPollAnswer);
                    removeView(newPollAnswer, v);
                    checkForSiblingAnswerViews(newPollAnswer);
                }
            });
            setSeparatorVisibility(newPollAnswer);
            setDeleteButtonVisibility(newPollAnswer);
        }

        private void checkForSiblingAnswerViews(NewPollAnswer newPollAnswer) {
            if (!hasMoreThan2Items(newPollAnswer)) {
                for (NewPollAnswer answer : newPollAnswer.question.getAnswers()) {
                    int location = getLocation(answer);
                    notifyItemChanged(location);
                }
            }
        }

        private void setDeleteButtonVisibility(NewPollAnswer newPollAnswer) {
            deleteButton.setVisibility(hasMoreThan2Items(newPollAnswer) ? View.VISIBLE : View.INVISIBLE);
        }

        private void setSeparatorVisibility(NewPollAnswer newPollAnswer) {
            listitemAnswerSeparator.setVisibility(isFirstAnswerView(newPollAnswer) ? View.GONE : View.VISIBLE);
        }

        private boolean isFirstAnswerView(NewPollAnswer newPollAnswer) {
            return newPollAnswer.question.getAnswers().get(0).equals(newPollAnswer);
        }

        private boolean hasMoreThan2Items(NewPollAnswer newPollAnswer) {
            return newPollAnswer.question.getAnswers().size() > 2;
        }
    }

    class AddAnswerViewHolder extends ViewHolderParent {

        public AddAnswerViewHolder(View parent) {
            super(parent);
        }

        @Override
        public void bindView(NewPollListItem newPollListItem) {
            final NewPollAddAnswer newPollAddAnswer = (NewPollAddAnswer) newPollListItem;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPollQuestion question = newPollAddAnswer.question;
                    NewPollAnswer newAnswer = new NewPollAnswer("", question);
                    question.getAnswers().add(newAnswer);
                    int location = getLocation(newPollAddAnswer);
                    insertItem(newAnswer, location);
                    checkForSiblingAnswerViews(newPollAddAnswer);
                }
            });
        }

        private void checkForSiblingAnswerViews(NewPollAnswer newPollAnswer) {
            if (has3Items(newPollAnswer)) {
                for (NewPollAnswer answer : newPollAnswer.question.getAnswers()) {
                    int location = getLocation(answer);
                    notifyItemChanged(location);
                }
            }
        }

        private boolean has3Items(NewPollAnswer newPollAnswer) {
            return newPollAnswer.question.getAnswers().size() == 3;
        }
    }


    private int getLocation(NewPollListItem newPollListItem) {
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

    private void removeView(NewPollListItem newPollListItem, View v) {
        int location = getLocation(newPollListItem);
        pollListItems.remove(location);
        this.notifyItemRemoved(location);
    }
}