package com.appsball.votenow.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsball.votenow.R;
import com.appsball.votenow.model.Choice;
import com.appsball.votenow.model.Comment;
import com.appsball.votenow.model.ResultModel;

public class ResultsPageAnswersLayout {

	private final LayoutInflater layoutInflater;
	private final LinearLayout answersLayout;
	private final ResultModel resultModel;

	public ResultsPageAnswersLayout(LayoutInflater layoutInflater, LinearLayout answersLayout, ResultModel resultModel) {
		this.layoutInflater = layoutInflater;
		this.answersLayout = answersLayout;
		this.resultModel = resultModel;
	}

	public void fillLayout() {
		createAnswersLayouts();
	}

	public void createAnswersLayouts() {

		boolean anonym = resultModel.isAnonym();
		if (resultModel.isMultiChoice()) {

			for (Choice choice : resultModel.getChoices()) {
				createAnswerLayout(choice, anonym, true);
			}
			if (resultModel.getComments().size() > 0) {
				createCommentHeaderRow();
			}
			for (Comment comment : resultModel.getComments()) {
				createCommentRow(comment);
			}
		} else {
			for (Choice choice : resultModel.getChoices()) {
				createAnswerLayout(choice, anonym, false);
			}
		}
	}

	public void createAnswerLayout(Choice choice, boolean anonym, boolean multiChoice) {
		addHorizontalLine();
		createAnswerRow(choice);
		if (!multiChoice) {
			for (Comment comment : choice.getComments()) {
				createCommentRow(comment);
			}
		} else if (!anonym) {
			String names = choice.createNamesForChoice();
			createUserNamesRow(names);
		}

	}

	private void addHorizontalLine() {
		View line = layoutInflater.inflate(R.layout.horizontalline, answersLayout, false);

		answersLayout.addView(line);
	}

	public void createAnswerRow(Choice choice) {
		LinearLayout answerRow = (LinearLayout) layoutInflater.inflate(R.layout.voterow, answersLayout, false);
		TextView answerTextView = (TextView) answerRow.findViewById(R.id.answer_text);
		TextView percentTextView = (TextView) answerRow.findViewById(R.id.percent_text);
		TextView summaTextView = (TextView) answerRow.findViewById(R.id.summa_text);

		answerTextView.setText(choice.getName());
		summaTextView.setText("" + choice.getNumber());
		percentTextView.setText(choice.getPercentage());
		answersLayout.addView(answerRow);
	}

	public void createCommentRow(Comment comment) {
		LinearLayout commentView = (LinearLayout) layoutInflater.inflate(R.layout.comment_row, answersLayout, false);
		TextView nameTextView = (TextView) commentView.findViewById(R.id.name_text);
		TextView commentTextView = (TextView) commentView.findViewById(R.id.comment_text);

		String text = "";
		if (comment != null) {
			if (comment.getName() != null) {
				text = comment.getName() + ": ";
			}
			if (comment.getComment() != null) {
				text += comment.getComment();
			}
		}
		nameTextView.setText(text);
		commentTextView.setText("");
		answersLayout.addView(commentView);
	}

	public void createCommentHeaderRow() {
		addHorizontalLine();
		LinearLayout commentsHeaderRow = (LinearLayout) layoutInflater.inflate(R.layout.names_row, answersLayout, false);
		TextView namesText = (TextView) commentsHeaderRow.findViewById(R.id.names_text);
		namesText.setText("Comments");
		answersLayout.addView(commentsHeaderRow);
	}

	public void createUserNamesRow(String names) {
		LinearLayout namesRow = (LinearLayout) layoutInflater.inflate(R.layout.names_row, answersLayout, false);
		TextView namesText = (TextView) namesRow.findViewById(R.id.names_text);
		namesText.setText(names);
		answersLayout.addView(namesRow);
	}
}
