package com.paanxis.mojoy.app.net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpConnect.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public class HttpConnect {
    static final String HOST = "http://mojoy.paanxis.com/v1";

    private static String cookie;
    public static JSONObject get(String uri) throws IOException, JSONException
    {
        /* Connect */
        HttpURLConnection connection = openConnection(uri);

        /* Get */
        connection.setRequestMethod("GET");

        /* Response */
        return getResonse(connection);
    }

    /**
     * POST请求.
     * @param uri Api地址
     * @param content Post参数内容
     * @return 响应内容
     * @throws IOException 错误
     */
    public static JSONObject post(String uri, JSONObject content) throws IOException, JSONException {
        /* Connect */
        HttpURLConnection connection = openConnection(uri);

        /* Post */
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(content.toString().getBytes());

        /* Response */
        return getResonse(connection);
    }
    private static HttpURLConnection openConnection(String uri) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(HOST + uri).openConnection();
        connection.setRequestProperty("content-type", "application/json");
        connection.setConnectTimeout(6000);
        connection.setReadTimeout(6000);
        //设置cookie
        connection.setRequestProperty("Cookie", cookie);

        return connection;
    }
    private static JSONObject getResonse(HttpURLConnection connection) throws JSONException, IOException {
        /* Response */
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            //获取Cookie
            String cookie = connection.getHeaderField("Set-Cookie");
            if(cookie != null && cookie.length() > 0) HttpConnect.cookie = cookie;
            //获取Response
            String responseStr = readStream(connection.getInputStream());
            //System.out.println("response = " + response);
            if (responseStr.length() == 0) return null;
            else return new JSONObject(responseStr);
        } else {
            throw new IOException(
                    "HTTP status error!" +
                            "\n        error code: " + responseCode +
                            "\n        error message: " + readStream(connection.getErrorStream())
            );
        }
    }
    /**
     * 读取数据流.
     * @param inputStream 输入流
     * @return 数据内容
     * @throws IOException 错误
     */
    private static String readStream(InputStream inputStream) throws IOException {
        int cacheLength;
        byte[] cacheBytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((cacheLength = inputStream.read(cacheBytes)) > 0 ) { outputStream.write(cacheBytes, 0, cacheLength); }
        outputStream.close();
        inputStream.close();
        return outputStream.toString();
    }
}