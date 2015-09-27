package com.appsball.votenow.communication;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.appsball.votenow.R;

public class OutputAncestor {
	private final static String errorStart = "ERROR:";
	private boolean isError = false;
	protected String answer;
	private String errorString;

	public OutputAncestor(Context context, InputStream stream) {
		if (stream == null) {
			isError = true;
			errorString = context.getResources().getString(R.string.communicationerror);
			return;
		}
		String str = getAnswerFromStream(stream);
		if (str.startsWith(errorStart)) {
			isError = true;
			errorString = str.substring(errorStart.length());
		}

		answer = str;
	}

	private static String getAnswerFromStream(InputStream stream) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(stream);
			String value = "";
			NodeList nodes = doc.getElementsByTagName("return");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				value = node.getLastChild().getTextContent();
			}
			return value;
		} catch (Exception e) {
			return errorStart + "Communication error";
		}
	}

	private static String getAnswerFromString(String s) {
		try {
			int i1 = s.indexOf("<return>") + "<return>".length();
			int i2 = s.indexOf("</return>");
			String answer = s.substring(i1, i2);
			return answer;
		} catch (Exception e) {
			return errorStart + "Communication error";
		}
	}

	public String getAnswer() {
		return answer;
	}

	public String getErrorString() {
		return errorString;
	}

	public boolean isError() {
		return isError;
	}
}
