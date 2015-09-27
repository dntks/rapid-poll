package com.appsball.votenow.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.appsball.votenow.model.ResultModel.ResultFields;

public class Comment {
	private String name;
	private String comment;

	public Comment(JSONObject jsonObject) throws JSONException {
		String commentField = ResultFields.comment.toString();
		String nameField = ResultFields.name.toString();
		if (jsonObject.has(commentField)) {
			comment = jsonObject.getString(commentField);
		}
		if (jsonObject.has(nameField)) {
			name = jsonObject.getString(nameField);
		}

	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

}
