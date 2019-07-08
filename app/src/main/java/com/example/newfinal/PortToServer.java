package com.example.newfinal;

import android.util.Log;

import com.mongodb.util.JSON;
/*
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
*/
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class PortToServer {
    String url;
    Map<String, String> cookies;
    String TAG = "PortToServer";
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PortToServer(String url, Map<String, String> cookies) {
        this.url = url;
        this.cookies = cookies;
    }
    /*
        public JSONObject postToServer(String query){

            HttpClient httpClient = new DefaultHttpClient();

            String urlString = getUrl();

            try {

                URI url = new URI(urlString);



                HttpPost httpPost = new HttpPost();

                httpPost.setURI(url);



                List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("userId", "saltfactory"));

                nameValuePairs.add(new BasicNameValuePair("password", "password"));

                StringEntity entity = new StringEntity(query);

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                Log.d("PortToServer", responseString);

                try {
                    return new JSONObject(responseString);
                } catch (JSONException e){
                    e.printStackTrace();
                    return null;
                }






            } catch (URISyntaxException e) {

                Log.e(TAG, e.getLocalizedMessage());

                e.printStackTrace();

                return null;

            } catch (ClientProtocolException e) {

                Log.e(TAG, e.getLocalizedMessage());

                e.printStackTrace();

                return null;

            } catch (IOException e) {

                Log.e(TAG, e.getLocalizedMessage());

                e.printStackTrace();

                return null;

            }
        }
    */

    public JSONObject postToServerV2(final QueryToServer query) throws IOException {
        final URL url = new URL(getUrl() + query.getTarget());
        //URL url = new URL("http://143.248.36.38:3000");
        final int TIMEOUT_VALUE = 100000;
        // HTTP 접속 구하기
        final List<JSONObject> result = new ArrayList<>();
        Thread thread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        String prevCookie = cookies.getOrDefault(getUrl(), "");
                        //System.out.println(conn.getHeaderFields());
/*
                        if (conn.getHeaderField("Set-Cookie")!=null){
                            System.out.println("cookie was provided");
                            prevCookie = prevCookie+conn.getHeaderField("Set-Cookie");
                            cookies.put(getUrl(), prevCookie);
                        }
*/
                        if (!prevCookie.equals("")){
                            conn.setRequestProperty("Cookie", prevCookie);
                        }
                        conn.setRequestProperty("Content-Type", "application/json");

                        conn.setConnectTimeout(TIMEOUT_VALUE);

                        conn.setReadTimeout(TIMEOUT_VALUE);

                        // 리퀘스트 메소드를 POST로 설정
                        conn.setRequestMethod("POST");

                        // 연결된 connection 에서 출력도 하도록 설정
                        conn.setDoOutput(true);

                        System.out.println(url);
                        // 요청 파라미터 출력
                        // - 파라미터는 쿼리 문자열의 형식으로 지정 (ex) 이름=값&이름=값 형식&...
                        // - 파라미터의 값으로 한국어 등을 송신하는 경우는 URL 인코딩을 해야 함.
                        OutputStream out = conn.getOutputStream();
                        out.write(query.toString().getBytes());
                        String res;
                        // 응답 내용(BODY) 구하기
                        InputStream in = conn.getInputStream();
                        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024 * 8];
                        int length = 0;
                        while ((length = in.read(buf)) != -1) {
                            byteout.write(buf, 0, length);
                        }
                        res = new String(byteout.toByteArray(), "UTF-8");
                        System.out.println(res);
                        // 접속 해제
                        if (conn.getHeaderField("Set-Cookie")!=null){
                            System.out.println("cookie was provided");
                            cookies.put(getUrl(), prevCookie+conn.getHeaderField("Set-Cookie"));
                        }
                        conn.disconnect();
                        JSONObject obj = null;
                        obj = new JSONObject(res);
                        result.add(obj);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    notify();
                }
            }
        };
        thread.start();
        synchronized (thread){
            try{
                thread.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        if (result.size() == 0)
            return null;
        return result.get(0);
    }
}
