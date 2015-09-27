package com.appsball.votenow.ui;

import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appsball.votenow.R;
import com.appsball.votenow.communication.Communication;
import com.appsball.votenow.communication.OutputAncestor;
import com.appsball.votenow.communication.OutputGetQuestion;
import com.appsball.votenow.model.QuestionModel;
import com.appsball.votenow.util.GUIUtil;

public class VoteFragment extends BaseFragment {
	private final static String RATE_TIME_LEFT = "RATE_TIME_LEFT";
	private final static String RATE_QUESTION = "RATE_QUESTION";
	private final static String RATE_CODE = "RATE_CODE";
	private final static String CURRENT_RATING = "CURRENT_RATING";

	private View rootView;

	private QuestionModel questionModel;

	public static VoteFragment newInstance(OutputGetQuestion output, String code) {
		VoteFragment instance = new VoteFragment();

		Bundle args = new Bundle();
		if (output != null) {
			args.putString(RATE_QUESTION, output.getAnswer());
		}
		args.putString(RATE_CODE, code);
		args.putInt(CURRENT_RATING, 2);
		instance.setArguments(args);

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.votepage, container, false);

		String jsonString = getArguments().getString(RATE_QUESTION);

		questionModel = new QuestionModel(jsonString);
		long timeLeft = getArguments().getInt(RATE_TIME_LEFT);
		timeLeft = (questionModel.getEndTime().getTime().getTime() - (new Date()).getTime()) / 1000;
		String timeLeftString = String.format(Locale.ENGLISH, "%d:%02d", timeLeft / 60, timeLeft % 60);
		timeLeftString = getActivity().getString(R.string.rate_timeleft, timeLeftString);
		((TextView) rootView.findViewById(R.id.text_rate_timeleft)).setText(timeLeftString);

		((TextView) rootView.findViewById(R.id.text_question)).setText(questionModel.getQuestion());

		final EditText nameEditText = (EditText) rootView.findViewById(R.id.edit_email);
		setTextWatcher(nameEditText);
		rootView.findViewById(R.id.button_send_vote).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkNameIsGiven(nameEditText)) {
					sendAnswer();
				}
			}
		});

		Button cancel = (Button) rootView.findViewById(R.id.button_cancel_vote);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBackButtonAction();
			}

		});
		createVotableAnswers(rootView);

		return rootView;
	}

	private boolean checkNameIsGiven(EditText nameEditText) {
		if (!questionModel.isAnonymus()) {
			if ("".equals(nameEditText.getText().toString())) {
				nameEditText.setError("Please give a name or e-mail.");
				return false;
			}
		}
		return true;
	}

	private void setTextWatcher(final EditText text) {
		text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
			}

			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
			}

			@Override
			public void afterTextChanged(final Editable s) {
				if (text.getText().toString().length() > 0) {
					text.setError(null);
				}
			}
		});
	}

	public void createVotableAnswers(View rootView) {
		LinearLayout choiceGroup;
		if (questionModel.isMultichoice()) {
			choiceGroup = (LinearLayout) rootView.findViewById(R.id.linearlayout_vote_answers);
		} else {
			choiceGroup = (RadioGroup) rootView.findViewById(R.id.vote_radio_group);
		}
		for (String answer : questionModel.getAnswers()) {
			addAnswerView(choiceGroup, answer, questionModel.isMultichoice());
		}
		if (questionModel.isAnonymus()) {
			rootView.findViewById(R.id.edit_email).setVisibility(View.GONE);
		}
	}

	public void addAnswerView(LinearLayout radioGroup, String answer, boolean isMulti) {
		LayoutInflater inflater = (LayoutInflater) radioGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		CompoundButton answerLayout;
		if (isMulti) {
			answerLayout = (CheckBox) inflater.inflate(R.layout.answer_checkbox_vote_row, radioGroup, false);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.bottomMargin = radioGroup.getResources().getDimensionPixelSize(R.dimen.answers_margin);
			answerLayout.setLayoutParams(params);
		} else {
			answerLayout = (RadioButton) inflater.inflate(R.layout.answer_radio_vote_row, radioGroup, false);
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
			params.bottomMargin = radioGroup.getResources().getDimensionPixelSize(R.dimen.answers_margin);
			answerLayout.setLayoutParams(params);
		}
		answerLayout.setText(answer);
		radioGroup.addView(answerLayout);
	}

	private String getAnswersStringForCommunication() {
		String answerString = "";
		LinearLayout choiceGroup;
		if (questionModel.isMultichoice()) {
			choiceGroup = (LinearLayout) rootView.findViewById(R.id.linearlayout_vote_answers);
		} else {
			choiceGroup = (RadioGroup) rootView.findViewById(R.id.vote_radio_group);
		}
		for (int i = 0; i < choiceGroup.getChildCount(); i++) {
			View actualChild = choiceGroup.getChildAt(i);
			if (actualChild instanceof CompoundButton) {
				if (((CompoundButton) actualChild).isChecked()) {
					answerString += "1";
				} else {
					answerString += "0";
				}
			}
		}
		return answerString;
	}

	private void sendAnswer() {
		new AsyncTask<Void, Void, OutputAncestor>() {
			private String commentString;
			private String nameString;
			private String answerChoices;

			@Override
			protected void onPreExecute() {
				commentString = ((EditText) rootView.findViewById(R.id.edit_answer)).getText().toString();
				nameString = ((EditText) rootView.findViewById(R.id.edit_email)).getText().toString();
				answerChoices = getAnswersStringForCommunication();
				EditText myEditText = (EditText) rootView.findViewById(R.id.edit_answer);
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

				GUIUtil.showProgressDialog(getActivity());
			};

			@Override
			protected OutputAncestor doInBackground(Void... params) {
				return Communication.addAnswer(getActivity().getApplicationContext(), getArguments().getString(RATE_CODE), answerChoices, nameString,
						commentString);
			}

			@Override
			protected void onPostExecute(OutputAncestor answer) {
				GUIUtil.hideProgressDialog();
				if (answer.isError()) {
					GUIUtil.showAlert(getActivity(), answer.getErrorString());
				} else {
					GUIUtil.showAlert(getActivity(), answer.getAnswer());
					((FeedbackActivity) getActivity()).changeFragment(HomePageFragment.newInstance());
				}
			};
		}.execute();
	}

	@Override
	public boolean doBackButtonAction() {
		((FeedbackActivity) getActivity()).changeFragment(HomePageFragment.newInstance());
		return true;
	}
}
