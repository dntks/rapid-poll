package com.appsball.votenow.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.content.res.AssetManager;

import com.appsball.votenow.util.DeviceUtil;

public class Communication {
	private final static String WS_URL = "http://votenow-appsball2.rhcloud.com/votenowWSDL";

	// private final static String WS_URL =
	// "http://ratenow-appsball.rhcloud.com/feedbackWSDL";

	public static OutputAncestor createQuestion(Context context, String questionModelJson) {
		String sm = getSoapMessage(context, "soap-question");
		String soapMessage = String.format(sm, questionModelJson);
		InputStream dataFromUrlAsInputStream = getDataFromUrlAsInputStream(WS_URL, soapMessage);
		// String answer = inputStreamToString(dataFromUrlAsInputStream);
		OutputAncestor output = new OutputAncestor(context, dataFromUrlAsInputStream);
		return output;
	}

	public static OutputGetQuestion getQuestion(Context context, String code) {
		String sm = getSoapMessage(context, "soap-getquestion");
		String soapMessage = String.format(sm, code, DeviceUtil.getDeviceType(context), DeviceUtil.getDeviceId(context));
		InputStream dataFromUrlAsInputStream = getDataFromUrlAsInputStream(WS_URL, soapMessage);
		// String answer = inputStreamToString(dataFromUrlAsInputStream);
		OutputGetQuestion output = new OutputGetQuestion(context, dataFromUrlAsInputStream);
		return output;
	}

	public static OutputGetQuestionResult getQuestionResult(Context context, String code) {
		String sm = getSoapMessage(context, "soap-getquestionresult");
		String soapMessage = String.format(sm, code, DeviceUtil.getDeviceId(context));
		InputStream dataFromUrlAsInputStream = getDataFromUrlAsInputStream(WS_URL, soapMessage);
		// String answer = inputStreamToString(dataFromUrlAsInputStream);
		OutputGetQuestionResult output = new OutputGetQuestionResult(context, dataFromUrlAsInputStream);
		return output;
	}

	public static OutputAncestor addAnswer(Context context, String code, String answerChoices, String name, String message) {
		String sm = getSoapMessage(context, "soap-answer");
		String soapMessage = String.format(sm, code, answerChoices, name, message, DeviceUtil.getDeviceType(context), DeviceUtil.getDeviceId(context));
		InputStream dataFromUrlAsInputStream = getDataFromUrlAsInputStream(WS_URL, soapMessage);
		// String answer = inputStreamToString(dataFromUrlAsInputStream);
		OutputAncestor output = new OutputAncestor(context, dataFromUrlAsInputStream);
		return output;
	}

	private static InputStream getDataFromUrlAsInputStream(String url, String soapMessage) {
		try {
			HttpParams parameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(parameters, 20000);
			HttpConnectionParams.setSoTimeout(parameters, 20000);
			HttpClient client = new DefaultHttpClient(parameters);

			HttpPost httppost = new HttpPost(url.toString());

			httppost.addHeader("Content-Type", "text/xml; charset=UTF-8");

			if (soapMessage != null)
				httppost.setEntity(new StringEntity(soapMessage, "UTF-8"));

			HttpResponse response = client.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode < 200 || statusCode > 299) {
				System.out.println("Status code error for url (" + url.toString() + "): " + statusCode + " (" + response.getStatusLine().getReasonPhrase()
						+ ")");
			}
			InputStream is = response.getEntity().getContent();

			return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getSoapMessage(Context context, String messageName) {
		try {
			AssetManager assetManager = context.getAssets();
			InputStream is = assetManager.open(messageName + ".xml");
			return inputStreamToString(is);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Error:Reading file";
	}

	public static String inputStreamToString(InputStream is) {
		try {
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is), 8192);
			String line = br.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			return sb.toString();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

}
