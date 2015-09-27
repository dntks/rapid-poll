package com.appsball.votenow.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsball.votenow.communication.Fields;

public class QuestionModel {
	private boolean anonymus;
	private boolean multichoice;
	private String question;
	private List<String> answers;
	private String email;
	private Calendar endTime, startTime;

	private String deviceId;
	private String deviceType;

	public QuestionModel() {
	}

	public QuestionModel(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			anonymus = jsonObject.getBoolean(Fields.ANONYMOUS.toString());
			multichoice = jsonObject.getBoolean(Fields.MULTICHOICE.toString());
			question = jsonObject.getString(Fields.QUESTION.toString());
			JSONArray jsonArrayAnswers = jsonObject.getJSONArray(Fields.CHOICES.toString());
			answers = new ArrayList<String>();
			for (int i = 0; i < jsonArrayAnswers.length(); i++) {
				answers.add(jsonArrayAnswers.getString(i));
			}
			endTime = Calendar.getInstance();
			endTime.add(Calendar.SECOND, jsonObject.getInt(Fields.TIME_FN.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean isAnonymus() {
		return anonymus;
	}

	public void setAnonymus(boolean anonymus) {
		this.anonymus = anonymus;
	}

	public boolean isMultichoice() {
		return multichoice;
	}

	public void setMultichoice(boolean multichoice) {
		this.multichoice = multichoice;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String toJsonString() {
		JSONObject object = new JSONObject();
		JSONArray choices = new JSONArray(answers);
		try {
			object.put(Fields.DEVICE_ID.toString(), deviceId);
			object.put(Fields.DEVICE_TYPE.toString(), deviceType);
			object.put(Fields.EMAIL.toString(), email);
			object.put(Fields.QUESTION.toString(), question);
			object.put(Fields.CHOICES.toString(), choices);
			object.put(Fields.MULTICHOICE.toString(), multichoice);
			object.put(Fields.ANONYMOUS.toString(), anonymus);
			long actualTime = new Date().getTime();
			long endTimeMillisFromNow = endTime.getTime().getTime() - actualTime;
			long startTimeMillisFromNow = startTime.getTime().getTime() - actualTime;
			object.put(Fields.TIME_FN_START.toString(), startTimeMillisFromNow / 1000);
			if (endTimeMillisFromNow < 0) {
				endTimeMillisFromNow = 10000;
			}
			object.put(Fields.TIME_FN.toString(), endTimeMillisFromNow / 1000);
			return object.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
}
