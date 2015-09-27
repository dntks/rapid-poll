package com.appsball.votenow.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsball.votenow.model.ResultModel.ResultFields;
import com.appsball.votenow.util.JSONHelper;

public class Choice {
	private final String name;
	private final List<Comment> comments;
	private final int number;
	private final String percentage;

	public Choice(JSONObject jsonObject) throws JSONException {
		name = JSONHelper.getFieldIfExist(jsonObject, ResultFields.choice.toString());
		number = jsonObject.getInt(ResultFields.number.toString());
		percentage = JSONHelper.getFieldIfExist(jsonObject, ResultFields.percentage.toString());

		String commentsField = ResultFields.comments.toString();
		comments = new ArrayList<Comment>();
		if (jsonObject.has(commentsField)) {
			JSONArray jsonArrayComments = jsonObject.getJSONArray(commentsField);

			for (int i = 0; i < jsonArrayComments.length(); i++) {
				Comment comment = new Comment(jsonArrayComments.getJSONObject(i));
				comments.add(comment);

			}
		}
	}

	public String getName() {
		return name;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public int getNumber() {
		return number;
	}

	public String getPercentage() {
		return percentage;
	}

	public String createNamesForChoice() {
		StringBuilder namesBuilder = new StringBuilder();
		for (Comment comment : comments) {
			if (comment.getName() != null) {
				namesBuilder.append(comment.getName());
				namesBuilder.append(", ");
			}
		}
		namesBuilder.delete(namesBuilder.length() - 2, namesBuilder.length());
		return namesBuilder.toString();
	}
}
