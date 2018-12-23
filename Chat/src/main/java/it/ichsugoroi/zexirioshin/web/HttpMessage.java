package it.ichsugoroi.zexirioshin.web;

import it.ichsugoroi.zexirioshin.app.IHttpMessage;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.utils.Constant;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpMessage implements IHttpMessage {

    @Override
    public void send(Message msg) {
        doOperations(ApplicationUtils.getCompleteUrlWithParameters(Constant.SENDERLINK, msg.getUrlParams()));
    }

    @Override
    public void delete(Message msg) {
        doOperations(ApplicationUtils.getCompleteUrlWithParameters(Constant.DELETERLINK, msg.getUrlParams()));
    }

    @Override
    public void search(String mittente, String destinatario) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("mittente", mittente);
        params.put("destinatario", destinatario);
        doOperations(ApplicationUtils.getCompleteUrlWithParameters(Constant.SEARCHERLINK, params));
    }

    private void doOperations(String url) {
        HttpURLConnection conn = null;
        try {
            conn = setConnection(url);
            test(getResponse(conn).toString());
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

    private void test(String t) {
        for(Message m : ApplicationUtils.getMessageListFromSearcherLinkResponse(t)) {
            System.out.println(m);
        }
    }
}
