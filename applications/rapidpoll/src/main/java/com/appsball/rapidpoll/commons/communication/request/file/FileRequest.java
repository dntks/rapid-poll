package com.appsball.rapidpoll.commons.communication.request.file;

import android.content.Context;
import android.os.Environment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class FileRequest extends Request<File> {
    private final Response.Listener<File> listener;
    Context context;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     */
    public FileRequest(String url,
                       Context context,
                       Response.Listener<File> listener,
                       Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
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
            return Response.success(
                    null,
                    HttpHeaderParser.parseCacheHeaders(response));
        }
        try {
            File file = new File(new File(Environment.getExternalStorageDirectory(), "Pictures"), "dummy.pdf");
            File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "dummy2.pdf");
            File file3 = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "dummy3.pdf");
            String fileContent = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            String filename = "myfile";
            String string = "Hello world!";
            OutputStream outputStream;
            OutputStream outputStream2;
            OutputStream outputStream3;

            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(response.data);
            outputStream.close();

            outputStream2 = new BufferedOutputStream(new FileOutputStream(file2));
            outputStream2.write(response.data);
            outputStream2.close();

            outputStream3 = new BufferedOutputStream(new FileOutputStream(file3));
            outputStream3.write(response.data);
            outputStream3.close();

            return Response.success(
                    file,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
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