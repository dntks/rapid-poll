package com.appsball.votenow.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

	public static String getFieldIfExist(JSONObject object, String fieldName) throws JSONException {
		if (object.has(fieldName)) {
			return object.getString(fieldName);
		} else {
			return "";
		}
	}

}
