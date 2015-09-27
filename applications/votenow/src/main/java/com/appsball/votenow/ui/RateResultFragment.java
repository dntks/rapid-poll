package com.appsball.votenow.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsball.votenow.R;
import com.appsball.votenow.communication.Communication;
import com.appsball.votenow.communication.OutputGetQuestionResult;
import com.appsball.votenow.model.ResultModel;
import com.appsball.votenow.util.GUIUtil;

public class RateResultFragment extends BaseFragment {
	private static String RATE_CODE = "RATE_CODE";

	private View rootView;

	public static RateResultFragment newInstance(String code) {
		RateResultFragment instance = new RateResultFragment();

		Bundle args = new Bundle();
		args.putString(RATE_CODE, code);
		instance.setArguments(args);

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.rateresult, container, false);

		createBGAsyncTask();

		return rootView;
	}

	private void printData(OutputGetQuestionResult result) {
		ResultModel resultModel = new ResultModel(result.getAnswer());
		((TextView) rootView.findViewById(R.id.result_title)).setText(resultModel.getTitle());
		((TextView) rootView.findViewById(R.id.result_number)).setText("" + resultModel.getNumberOfRates());

		setupListHeader();
		setupAnswersList(resultModel);
		setupTypeText(resultModel.isAnonym(), resultModel.isMultiChoice());
	}

	private void setupTypeText(boolean anonym, boolean multiChoice) {
		TextView typeText = (TextView) rootView.findViewById(R.id.type_text);
		String anonymText = anonym ? "Anonymous" : "Non-anonymous";
		String multiChoiceText = multiChoice ? "Multichoice" : "Single choice";
		typeText.setText("Type: " + anonymText + ", " + multiChoiceText);
	}

	private void setupListHeader() {
		LinearLayout alphabetLayout = (LinearLayout) rootView.findViewById(R.id.list_header);
		for (int i = 0; i < alphabetLayout.getChildCount(); i++) {
			TextView answer = (TextView) alphabetLayout.findViewById(R.id.answer_text);
			answer.setText("");
			TextView summatext = (TextView) alphabetLayout.findViewById(R.id.summa_text);
			summatext.setText("Summa");
			TextView percenttext = (TextView) alphabetLayout.findViewById(R.id.percent_text);
			percenttext.setText("%");
		}
	}

	private void setupAnswersList(ResultModel resultModel) {
		LinearLayout answersList = (LinearLayout) rootView.findViewById(R.id.answersLayout);

		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ResultsPageAnswersLayout resultsPageAnswersLayout = new ResultsPageAnswersLayout(layoutInflater, answersList, resultModel);

		resultsPageAnswersLayout.fillLayout();
		// AnswersListAdapter adapter = new AnswersListAdapter(resultModel,
		// layoutInflater);
		//
		// answersList.setAdapter(adapter);
	}

	private void createBGAsyncTask() {
		new AsyncTask<Void, Void, OutputGetQuestionResult>() {

			@Override
			protected void onPreExecute() {
				GUIUtil.showProgressDialog(getActivity());
			}

			@Override
			protected OutputGetQuestionResult doInBackground(Void... params) {
				OutputGetQuestionResult result = Communication.getQuestionResult(getActivity(), getArguments().getString(RATE_CODE));
				return result;
			}

			@Override
			protected void onPostExecute(OutputGetQuestionResult result) {
				GUIUtil.hideProgressDialog();

				if (result.isError()) {
					GUIUtil.showAlert(getActivity(), result.getErrorString());
				} else {
					printData(result);
				}
			}
		}.execute();
	}

	@Override
	public boolean doBackButtonAction() {
		((FeedbackActivity) getActivity()).changeFragment(HomePageFragment.newInstance());
		return true;
	}

}
