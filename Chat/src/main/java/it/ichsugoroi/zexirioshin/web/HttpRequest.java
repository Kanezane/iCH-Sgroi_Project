package it.ichsugoroi.zexirioshin.web;

import it.ichsugoroi.zexirioshin.utils.ApplicationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpRequest {
    private String url;

    public HttpRequest(String url, String params) {
        this.url = getCompleteUrl(url, params);
    }

    private String getCompleteUrl(String url, String params) {
        String res;
        try {
            res = url + "?msg=" + URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(e);
        }
        return res;
    }

    public void sendPost() {
        HttpURLConnection conn = null;
        try {
            conn = setConnection(url);
            System.out.println(getResponse(conn).toString());
        } catch (MalformedURLException e) {
            throw new ApplicationException(e);
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
    }

    private StringBuffer getResponse(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append("\r\n");
        }
        rd.close();
        return response;
    }

    private HttpURLConnection setConnection(String url) throws IOException {
        URL realURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "Application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Language", "it-IT");

        conn.setUseCaches (false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }
}
