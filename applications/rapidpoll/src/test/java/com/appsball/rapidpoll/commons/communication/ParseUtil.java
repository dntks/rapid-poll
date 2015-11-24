package com.appsball.rapidpoll.commons.communication;

import com.google.common.io.CharStreams;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

public class ParseUtil {

    public static String getFileContentAsString(Object obj, String fileName) throws IOException {
        File file = getFileFromPath(obj, fileName);

        FileInputStream in = new FileInputStream(file);
        return CharStreams.toString(new InputStreamReader(in, "UTF-8"));
    }

    public static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource("res/" + fileName);
        return new File(resource.getPath());
    }

    public static void assertJsonsEqual(String expectedJsonAsString, String actualJsonAsString) {
        JsonParser parser = new JsonParser();
        assertEquals(parser.parse(expectedJsonAsString), parser.parse(actualJsonAsString));
    }
}
