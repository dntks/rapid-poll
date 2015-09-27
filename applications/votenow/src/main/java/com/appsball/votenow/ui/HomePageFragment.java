package com.appsball.votenow.ui;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.appsball.votenow.R;
import com.appsball.votenow.communication.Communication;
import com.appsball.votenow.communication.OutputAncestor;
import com.appsball.votenow.communication.OutputGetQuestion;
import com.appsball.votenow.model.QuestionModel;
import com.appsball.votenow.util.DeviceUtil;
import com.appsball.votenow.util.GAnalyticsHelper;
import com.appsball.votenow.util.GAnalyticsHelper.SimpleAction;
import com.appsball.votenow.util.GUIUtil;

public class HomePageFragment extends BaseFragment {

	private View rootView;

	private Calendar selectedStartTime, selectedEndTime;

	public static HomePageFragment newInstance() {
		return new HomePageFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.homepage, container, false);
		EditText questionEditText = ((EditText) rootView.findViewById(R.id.edit_question));
		questionEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			boolean lostFocus = false;

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					lostFocus = true;
				} else if (lostFocus && hasFocus) {
					GAnalyticsHelper.trackEvent(getActivity(), SimpleAction.QuestionEdited);
				}
			}
		});
		((EditText) rootView.findViewById(R.id.edit_email)).setText(getEmail());

		Button question = (Button) rootView.findViewById(R.id.button_question_add);
		question.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkDate()) {
					createQuestion();
				}
			}
		});

		Button rate = (Button) rootView.findViewById(R.id.button_rate_now);
		rate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startRating();
			}

		});

		Button startDatePicker = (Button) rootView.findViewById(R.id.starttime_datepicker);
		startDatePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showStartDatePickerDialog((Button) v);
			}
		});
		Button startTimePicker = (Button) rootView.findViewById(R.id.starttime_timepicker);
		startTimePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showStartTimePickerDialog((Button) v);
			}
		});
		Button endDatePicker = (Button) rootView.findViewById(R.id.endtime_datepicker);
		endDatePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showEndDatePickerDialog((Button) v);
			}
		});
		Button endTimePicker = (Button) rootView.findViewById(R.id.endtime_timepicker);
		endTimePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showEndTimePickerDialog((Button) v);
			}
		});
		selectedStartTime = Calendar.getInstance();
		selectedEndTime = Calendar.getInstance();
		selectedStartTime.set(Calendar.SECOND, 0);
		selectedEndTime.add(Calendar.MINUTE, 3);
		selectedEndTime.set(Calendar.SECOND, 59);
		startTimePicker.setText(createTimeTextFromCalendar(selectedStartTime));
		endTimePicker.setText(createTimeTextFromCalendar(selectedEndTime));

		startDatePicker.setText(createDateTextFromCalendar(selectedStartTime, getActivity()));
		endDatePicker.setText(createDateTextFromCalendar(selectedEndTime, getActivity()));

		answerCreator = new AnswerCreator((LinearLayout) rootView.findViewById(R.id.answersLayout));
		answerCreator.setupListeners();
		return rootView;
	}

	protected boolean checkDate() {
		if (selectedStartTime.before(selectedEndTime) && selectedEndTime.after(Calendar.getInstance())) {
			return true;
		} else if (!selectedStartTime.before(selectedEndTime)) {
			GUIUtil.showAlert(getActivity(), "End time must be after start time!");

		} else if (!selectedEndTime.after(Calendar.getInstance())) {
			GUIUtil.showAlert(getActivity(), "End time must be in the future!");
		}
		return false;
	}

	private static CharSequence createTimeTextFromCalendar(Calendar time) {
		String timeString = String.format("%02d", time.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", time.get(Calendar.MINUTE));
		return timeString;
	}

	private static CharSequence createDateTextFromCalendar(Calendar time, Context context) {
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
		String timeString = dateFormat.format(time.getTime());
		return timeString;
	}

	private void startRating() {
		new AsyncTask<Void, Void, OutputGetQuestion>() {
			private String code;

			@Override
			protected void onPreExecute() {
				code = ((EditText) rootView.findViewById(R.id.edit_code)).getText().toString();

				EditText myEditText = (EditText) rootView.findViewById(R.id.edit_code);
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

				GUIUtil.showProgressDialog(getActivity());
			};

			@Override
			protected OutputGetQuestion doInBackground(Void... params) {
				return Communication.getQuestion(getActivity().getApplicationContext(), code);
			}

			@Override
			protected void onPostExecute(OutputGetQuestion answer) {
				GUIUtil.hideProgressDialog();
				if (answer.isError()) {
					GUIUtil.showAlert(getActivity(), answer.getErrorString());
				} else {
					((FeedbackActivity) getActivity()).changeFragment(VoteFragment.newInstance(answer, code));
				}
			};
		}.execute();
	}

	public void createQuestion() {
		new AsyncTask<Void, Void, OutputAncestor>() {
			private String question;
			private String email;

			@Override
			protected void onPreExecute() {
				question = ((EditText) rootView.findViewById(R.id.edit_question)).getText().toString();
				email = ((EditText) rootView.findViewById(R.id.edit_email)).getText().toString();

				EditText myEditText = (EditText) rootView.findViewById(R.id.edit_email);
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

				GUIUtil.showProgressDialog(getActivity());
			};

			@Override
			protected OutputAncestor doInBackground(Void... params) {
				return Communication.createQuestion(getActivity().getApplicationContext(), createQuestionModel().toJsonString());
			}

			@Override
			protected void onPostExecute(OutputAncestor answer) {
				GUIUtil.hideProgressDialog();
				if (answer.isError()) {
					GUIUtil.showAlert(getActivity(), answer.getErrorString());
				} else {
					saveEmail(email);

					String msg = getActivity().getResources().getString(R.string.questionanswer, answer.getAnswer());
					GUIUtil.showAlert(getActivity(), Html.fromHtml(msg));

					((EditText) rootView.findViewById(R.id.edit_question)).setText("");
					answerCreator.clearAnswerEditTexts();
				}
			}
		}.execute();
	}

	private final static String EMAIL_STR = "EMAIL_STR";

	private AnswerCreator answerCreator;

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(HomePageFragment.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	private void saveEmail(String emailStr) {
		getPreferences(getActivity().getApplicationContext()).edit().putString(EMAIL_STR, emailStr).commit();
	};

	private String getEmail() {
		return getPreferences(getActivity().getApplicationContext()).getString(EMAIL_STR, "");
	}

	public void showStartDatePickerDialog(final Button button) {
		DatePickerFragment newFragment = new DatePickerFragment(selectedStartTime, button);
		newFragment.setMaxDate(selectedEndTime);
		newFragment.show(getFragmentManager(), "startDatePicker");
	}

	public void showStartTimePickerDialog(final Button button) {
		TimePickerFragment newFragment = new TimePickerFragment(selectedStartTime, button);
		newFragment.setMaxDate(selectedEndTime);
		newFragment.show(getFragmentManager(), "startTimePicker");
	}

	public void showEndDatePickerDialog(final Button button) {
		DatePickerFragment newFragment = new DatePickerFragment(selectedEndTime, button);
		newFragment.setMinDate(selectedStartTime);
		newFragment.show(getFragmentManager(), "endDatePicker");
	}

	public void showEndTimePickerDialog(final Button button) {
		TimePickerFragment newFragment = new TimePickerFragment(selectedEndTime, button);
		newFragment.setMinDate(selectedStartTime);
		newFragment.show(getFragmentManager(), "endTimePicker");
	}

	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		Calendar timepickerTime, minDate, maxDate;
		Button button;

		public TimePickerFragment(Calendar selectedTime, Button button) {
			this.timepickerTime = selectedTime;
			this.button = button;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			int hour = timepickerTime.get(Calendar.HOUR_OF_DAY);
			int minute = timepickerTime.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

			return dialog;
		}

		public void setMaxDate(Calendar maxDate) {
			this.maxDate = maxDate;
			// if (maxDate.before(minDate)) {
			// maxDate = minDate;
			// }
		}

		public void setMinDate(Calendar minDate) {
			this.minDate = minDate;
			// if (minDate.after(maxDate)) {
			// minDate = maxDate;
			// }
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			timepickerTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			timepickerTime.set(Calendar.MINUTE, minute);
			button.setText(createTimeTextFromCalendar(timepickerTime));

		}
	}

	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		Calendar datePickerDate, minDate, maxDate;
		Button button;

		public DatePickerFragment(Calendar selectedTime, Button button) {
			this.datePickerDate = selectedTime;
			this.button = button;
		}

		public void setMaxDate(Calendar maxDate) {
			this.maxDate = maxDate;
			// if (maxDate.before(minDate)) {
			// maxDate.setTimeInMillis(minDate.getTimeInMillis() + 1l);
			// }
		}

		public void setMinDate(Calendar minDate) {
			this.minDate = minDate;
			// if (minDate.after(maxDate)) {
			// minDate.setTimeInMillis(maxDate.getTimeInMillis() - 1l);
			// }
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			int year = datePickerDate.get(Calendar.YEAR);
			int month = datePickerDate.get(Calendar.MONTH);
			int day = datePickerDate.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
			// if (minDate != null) {
			// dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
			// } else if (maxDate != null) {
			// dialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
			// }
			return dialog;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			datePickerDate.set(Calendar.YEAR, year);
			datePickerDate.set(Calendar.MONTH, month);
			datePickerDate.set(Calendar.DAY_OF_MONTH, day);
			button.setText(createDateTextFromCalendar(datePickerDate, getActivity()));
		}
	}

	private QuestionModel createQuestionModel() {
		String question = ((EditText) rootView.findViewById(R.id.edit_question)).getText().toString();
		String email = ((EditText) rootView.findViewById(R.id.edit_email)).getText().toString();
		boolean anonymus = ((CheckBox) rootView.findViewById(R.id.anonym_checkbox)).isChecked();
		boolean multichoice = ((CheckBox) rootView.findViewById(R.id.multichoice_checkbox)).isChecked();
		List<String> answers = answerCreator.getAsnwers();

		QuestionModel questionModel = new QuestionModel();
		questionModel.setAnonymus(anonymus);

		questionModel.setAnswers(answers);

		questionModel.setEmail(email);

		questionModel.setEndTime(selectedEndTime);
		questionModel.setStartTime(selectedStartTime);
		questionModel.setMultichoice(multichoice);
		questionModel.setQuestion(question);
		questionModel.setDeviceType(DeviceUtil.getDeviceType(getActivity()));
		questionModel.setDeviceId(DeviceUtil.getDeviceId(getActivity()));
		return questionModel;
	}
}
