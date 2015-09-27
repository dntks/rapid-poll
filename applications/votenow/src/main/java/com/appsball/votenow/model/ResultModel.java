package com.appsball.votenow.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultModel {
	public enum ResultFields {
		anonym, choices, multichoice, numberOfRates, title, choice, comments, comment, name, number, percentage;
	}

	private boolean anonym;
	private boolean multiChoice;
	private int numberOfRates;
	private String title;
	private List<Choice> choices;
	private List<Comment> comments;

	public ResultModel(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			anonym = jsonObject.getBoolean(ResultFields.anonym.toString());
			title = jsonObject.getString(ResultFields.title.toString());
			multiChoice = jsonObject.getBoolean(ResultFields.multichoice.toString());
			numberOfRates = jsonObject.getInt(ResultFields.numberOfRates.toString());
			JSONArray jsonArrayChoices = jsonObject.getJSONArray(ResultFields.choices.toString());
			choices = new ArrayList<Choice>();
			for (int i = 0; i < jsonArrayChoices.length(); i++) {
				Choice choice = new Choice(jsonArrayChoices.getJSONObject(i));
				choices.add(choice);
			}
			comments = new ArrayList<Comment>();
			String commentsField = ResultFields.comments.toString();
			if (jsonObject.has(commentsField)) {
				JSONArray jsonArrayComments = jsonObject.getJSONArray(commentsField);
				for (int i = 0; i < jsonArrayComments.length(); i++) {
					Comment comment = new Comment(jsonArrayComments.getJSONObject(i));
					if (comment.getComment() != null && !"".equals(comment.getComment())) {
						comments.add(comment);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean isAnonym() {
		return anonym;
	}

	public boolean isMultiChoice() {
		return multiChoice;
	}

	public int getNumberOfRates() {
		return numberOfRates;
	}

	public String getTitle() {
		return title;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public int countChosenAnswers() {
		int count = 0;
		for (Choice choice : choices) {
			if (choice.getComments().size() > 0) {
				count++;
			}
		}
		return count;
	}

}