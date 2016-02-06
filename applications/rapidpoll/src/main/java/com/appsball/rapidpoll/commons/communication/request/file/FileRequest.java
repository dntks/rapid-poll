package com.appsball.rapidpoll.commons.communication.request.file;

import android.content.Context;
import android.os.Environment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.appsball.rapidpoll.commons.communication.request.ExportPollResultRequest;
import com.orhanobut.logger.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.appsball.rapidpoll.commons.communication.service.RapidPollRestService.SERVER_ADDRESS;

public class FileRequest extends Request<File> {
    private final Response.Listener<File> listener;
    private final Context context;
    private final ExportPollResultRequest request;

    public FileRequest(ExportPollResultRequest request,
                       Context context,
                       Response.Listener<File> listener,
                       Response.ErrorListener errorListener) {
        super(Method.GET,
                SERVER_ADDRESS + "/pollresultexport/" + request.userId + "/" + request.pollId + "/" + request.exportType.name() + "/" + request.code,
                errorListener);
        String url = SERVER_ADDRESS + "/pollresultexport/" + request.userId + "/" + request.pollId + "/" + request.exportType.name() + "/" + request.code;
        Logger.e("URL: " + url);
        this.request = request;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    protected void deliverResponse(File response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<File> parseNetworkResponse(NetworkResponse response) {
        if (!isExternalStorageWritable()) {
            return Response.error(new VolleyError("storage is not writable"));
        }
        try {
            String fileName = request.pollId + "." + request.exportType.fileFormatString;
            File file = new File(context.getFilesDir(), fileName);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(response.data);
            outputStream.close();

            return Response.success(file, HttpHeaderParser.parseCacheHeaders(response));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Logger.e("Directory not created");
        }
        return file;
    }
}