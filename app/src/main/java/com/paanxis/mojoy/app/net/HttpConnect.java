package com.paanxis.mojoy.app.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import com.paanxis.mojoy.app.net.account.Login;
import com.paanxis.mojoy.app.util.Encryption;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * HttpConnect.
 * Created by zhengxiaoyao0716 on 2016/2/24.
 */
public class HttpConnect {
    private static final String HOST = "http://114.215.107.179:8080/Mojoy";

    private static String cookie;
    public static JSONObject get(String uri) throws IOException
    {
        /* Connect */
        HttpURLConnection connection = openConnection(uri);

        /* Get */
        connection.setRequestMethod("GET");

        /* Response */
        return getResponse(connection);
    }

    /**
     * POST请求.
     * @param uri Api地址
     * @param content Post参数内容
     * @return 响应内容
     * @throws IOException 错误
     */
    public static JSONObject post(String uri, JSONObject content) throws IOException {
        /* Connect */
        HttpURLConnection connection = openConnection(uri);

        /* Post */
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        JSONObject packedContent = null;
        try {
            packedContent = packContent(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (packedContent == null) return null;
        else connection.getOutputStream().write(packedContent.toString().getBytes());

        /* Response */
        return getResponse(connection);
    }
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static JSONObject packContent(JSONObject content) throws JSONException {
        return new JSONObject(
        ).put(
                "msgHeader", new JSONObject(
                ).put(
                        "version", Build.VERSION.SDK_INT
                ).put(
                        "type", 1
                ).put(
                        "timeStamp", format.format(System.currentTimeMillis())
                ).put(
                        "sign", Encryption.INSTANCE.sign(content)
                ).put(
                        "token", Login.token
                )
        ).put(
                "msgBody", new JSONObject(
                ).put(
                        "content", content
                )
        );
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
    private static JSONObject getResponse(HttpURLConnection connection) throws IOException {
        /* Response */
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            //获取Cookie
            String cookie = connection.getHeaderField("Set-Cookie");
            if(cookie != null && cookie.length() > 0) HttpConnect.cookie = cookie;
            //获取Response
            String responseStr = readStream(connection.getInputStream());
            try {
                return new JSONObject(responseStr);
            } catch (JSONException e) {
                //响应不是Json序列
                try {
                    return new JSONObject().put("responseStr", responseStr);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    //响应是Json不支持的类型
                    return null;
                }
            }
        } else {
            Log.e("HTTP status error!",
                    "error code: " + responseCode +
                    "\nerror message: " + readStream(connection.getErrorStream()));
            return null;
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

    public static boolean checkNet(Activity activity)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}