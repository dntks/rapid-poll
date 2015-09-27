package com.appsball.votenow.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsball.votenow.R;

public class AnswerCreator {
	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int MAX_ANSWERS = 20;
	private final LinearLayout answersLayout;

	public AnswerCreator(LinearLayout answersLayout) {
		this.answersLayout = answersLayout;
	}

	public void setupListeners() {
		Button addAnswer = (Button) answersLayout.findViewById(R.id.add_answer_button);
		addAnswer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createNewAnswer();
			}
		});

		Button firstAnswerDelete = (Button) answersLayout.findViewById(R.id.firstAnswer).findViewById(R.id.delete_answer_button);
		firstAnswerDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteRow(answersLayout.findViewById(R.id.firstAnswer));
			}
		});

		Button secondAnswerDelete = (Button) answersLayout.findViewById(R.id.secondAnswer).findViewById(R.id.delete_answer_button);
		secondAnswerDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteRow(answersLayout.findViewById(R.id.secondAnswer));
			}
		});
		renameChoiceIds();
	}

	@SuppressLint("InflateParams")
	private void createNewAnswer() {
		LayoutInflater inflater = (LayoutInflater) answersLayout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout newAnswerLayout = (LinearLayout) inflater.inflate(R.layout.answer_edit_row, null);
		final int indexOfRow = answersLayout.getChildCount() - 1;
		answersLayout.addView(newAnswerLayout, indexOfRow);

		View deleteButton = newAnswerLayout.findViewById(R.id.delete_answer_button);
		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteRow((View) v.getParent());
			}
		});
		checkNumberOfAnswers();
		renameChoiceIds();
	}

	private void checkNumberOfAnswers() {
		int childCount = answersLayout.getChildCount();
		if (childCount <= 3) {
			hideDeleteButtons();
		} else {
			showDeleteButtons();
		}
		if (childCount >= MAX_ANSWERS) {
			answersLayout.findViewById(R.id.add_answer_button).setVisibility(View.GONE);
		} else {
			answersLayout.findViewById(R.id.add_answer_button).setVisibility(View.VISIBLE);
		}
	}

	private void showDeleteButtons() {
		for (int i = 0; i < answersLayout.getChildCount(); i++) {
			View row = answersLayout.getChildAt(i);
			View deleteButton = row.findViewById(R.id.delete_answer_button);
			if (deleteButton != null) {
				deleteButton.setVisibility(View.VISIBLE);
			}
		}
	}

	private void hideDeleteButtons() {
		for (int i = 0; i < answersLayout.getChildCount(); i++) {
			View row = answersLayout.getChildAt(i);
			View deleteButton = row.findViewById(R.id.delete_answer_button);
			if (deleteButton != null) {
				deleteButton.setVisibility(View.INVISIBLE);
			}
		}
	}

	private void renameChoiceIds() {
		final int childCount = answersLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View row = answersLayout.getChildAt(i);
			View choiceIdText = row.findViewById(R.id.choice_id_text);
			if (choiceIdText != null) {
				int charOrder = i % ALPHABET.length();
				char letter = ALPHABET.charAt(charOrder);
				((TextView) choiceIdText).setText(String.valueOf(letter));
			}
			View answerEditText = row.findViewById(R.id.answer_edit_text);
			if (answerEditText != null) {
				final int order = i + 1;
				String hintText = String.format(answersLayout.getResources().getString(R.string.alternative_text), String.valueOf(order));

				EditText editText = (EditText) answerEditText;
				editText.setHint(hintText);
				// action done stb.
				/*
				 * editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
				 * editText.setOnEditorActionListener(new
				 * OnEditorActionListener() {
				 * 
				 * @Override public boolean onEditorAction(TextView v, int
				 * actionId, KeyEvent event) { if (order >= childCount - 1) {
				 * createNewAnswer(); } else {
				 * 
				 * } return false; } });
				 */
			}
		}
	}

	private void deleteRow(View view) {
		answersLayout.removeView(view);
		checkNumberOfAnswers();
		renameChoiceIds();
	}

	public List<String> getAsnwers() {
		List<String> answers = new ArrayList<>();
		for (int i = 0; i < answersLayout.getChildCount(); i++) {
			View row = answersLayout.getChildAt(i);

			View answerEditText = row.findViewById(R.id.answer_edit_text);
			if (answerEditText != null) {
				EditText editText = (EditText) answerEditText;

				TextView choiceId = (TextView) row.findViewById(R.id.choice_id_text);
				answers.add(choiceId.getText() + ") " + editText.getText().toString());
			}
		}
		return answers;
	}

	public void clearAnswerEditTexts() {

		for (int i = 0; i < answersLayout.getChildCount(); i++) {
			View row = answersLayout.getChildAt(i);

			View answerEditText = row.findViewById(R.id.answer_edit_text);
			if (answerEditText != null) {
				EditText editText = (EditText) answerEditText;
				editText.setText("");
			}
		}
	}
}