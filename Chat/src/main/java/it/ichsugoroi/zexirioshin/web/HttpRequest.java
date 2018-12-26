package it.ichsugoroi.zexirioshin.web;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import it.ichsugoroi.zexirioshin.app.IHttpRequest;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.utils.CloseableUtils;
import it.ichsugoroi.zexirioshin.utils.Constant;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest implements IHttpRequest {

    @Override
    public String register(String username, String password) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        return getResponseFromUrl(params, Constant.REGISTERLINK);
    }

    @Override
    public String login(String username, String password) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        return getResponseFromUrl(params, Constant.LOGINLINK);
    }

    @Override
    public void send(Message msg) {
        System.out.println("sending()...");
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(Constant.SENDERLINK, ApplicationUtils.getUrlParamsFromMessage(msg)));
    }

    @Override
    public void delete(Message msg) {
        System.out.println("delete()...");
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(Constant.DELETERLINK, ApplicationUtils.getUrlParamsFromMessage(msg)));
    }

    @Override
    public List<Message> search(String mittente, String destinatario) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("mittente", mittente);
        params.put("destinatario", destinatario);
        return getMessageListFromUrl(ApplicationUtils.getCompleteUrlWithParameters(Constant.SEARCHERLINK, params));
    }

    @Override
    public void updateStatus(String username, String status) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("status", status);
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(Constant.SETSTATUSLINK, params));
    }

    @Override
    public String checkStatus(String username) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        return getStatusFromUrl(ApplicationUtils.getCompleteUrlWithParameters(Constant.CHECKSTATUSLINK, params));
    }

    private String getResponseFromUrl(Map<String, String> params, String url) {
        HttpURLConnection conn = setConnection(ApplicationUtils.getCompleteUrlWithParameters(url, params));
        String response = getResponseStringFromUrl(conn).toString();
        conn.disconnect();
        return ApplicationUtils.getTrimmedResponse(response);
    }

    private void printResponseFromUrl(String url) {
        HttpURLConnection conn = setConnection(url);
        System.out.println(getResponseStringFromUrl(conn));
        conn.disconnect();
    }

    private List<Message> getMessageListFromUrl(String url) {
        HttpURLConnection conn = setConnection(url);
        String response = getResponseStringFromUrl(conn).toString();
        conn.disconnect();
        return ApplicationUtils.getMessageListFromSearcherLinkResponse(response);
    }

    private String getStatusFromUrl(String url) {
        HttpURLConnection conn = setConnection(url);
        String response = getResponseStringFromUrl(conn).toString();
        conn.disconnect();
        return ApplicationUtils.getTrimmedResponse(response);
    }

    private HttpURLConnection setConnection(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "Application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Language", "it-IT");

            conn.setUseCaches (false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            return conn;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private StringBuffer getResponseStringFromUrl(HttpURLConnection conn) {
        InputStream is = null;
        try {
            is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append("\r\n");
            }
            rd.close();
            return response;
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(is);
        }
    }
}
