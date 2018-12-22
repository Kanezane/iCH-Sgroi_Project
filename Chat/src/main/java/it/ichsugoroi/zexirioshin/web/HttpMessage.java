package it.ichsugoroi.zexirioshin.web;

import it.ichsugoroi.zexirioshin.App.IHttpMessage;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpMessage implements IHttpMessage {

    @Override
    public void send(String url, Map<String,String> parameters) {
        doOperations(getCompleteUrl(url, parameters));
    }

    @Override
    public void delete(String url, Map<String,String> parameters) {
        doOperations(getCompleteUrl(url, parameters));
    }

    @Override
    public void search(String url) {
        doOperations(url);
    }

    private String getCompleteUrl(String url, Map<String,String> params) {
        StringBuilder res = new StringBuilder();
        try {
            for(Map.Entry<String, String> param : params.entrySet()) {
                if(res.length()!=0) { res.append('&'); }
                res.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                res.append('=');
                res.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(e);
        }
        return url + '?' + res.toString();
    }

    private void doOperations(String url) {
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
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "Application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Language", "it-IT");

        conn.setUseCaches (false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }
}
