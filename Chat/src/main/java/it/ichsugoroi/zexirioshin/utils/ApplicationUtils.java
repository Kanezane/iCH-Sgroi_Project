package it.ichsugoroi.zexirioshin.utils;

import it.ichsugoroi.zexirioshin.web.Message;
import it.ichsugoroi.zexirioshin.web.MessageNotification;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApplicationUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String getCompleteUrlWithParameters(String url, Map<String,String> params) {
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

    private static List<String> getResponseWithCarriageReturn(String response) {
        List<String> res = new ArrayList<>();
        String[] x = response.split("<body>");
        String[] y = x[1].split("</body>");
        String[] z = y[0].split("<br>");
        Collections.addAll(res, z);
        return res;
    }

    public static List<MessageNotification> getIncomingMessageNotifyFromCheckerLinkResponse(String response) {
        List<MessageNotification> res = new ArrayList<>();
        for(String s : getResponseWithCarriageReturn(response)) {
            if(!s.trim().equalsIgnoreCase("")) {
                String[] x = s.split(StringReferences.MESSAGESEPARATORPHP);
                MessageNotification mn = new MessageNotification();
                mn.setFriendUsername(x[0]);
                mn.setnMsg(x[1]);
                res.add(mn);
            }
        }
        return res;
    }

    public static List<Message> getMessageListFromSearcherLinkResponse(String response) {
        List<Message> res = new ArrayList<>();
        for(String s : getResponseWithCarriageReturn(response)) {
            if(!s.trim().equalsIgnoreCase("")) {
                System.out.println(s);
                String[] x = s.split(StringReferences.MESSAGESEPARATORPHP);
                Message m = new Message();
                System.out.println(x.length);
                m.setId(x[0].trim());
                m.setContenuto(x[1].trim());
                m.setMittente(x[2].trim());
                m.setDestinatario(x[3].trim());
                m.setDataInvio(x[4].trim());
                m.setOraInvio(x[5].trim());
                res.add(m);
            }
        }
        return res;
    }

    public static List<String> getFriendListFromFriendLinkResponse(String response) {
        List<String> res = new ArrayList<>();
        for(String s : getResponseWithCarriageReturn(response)) {
            if(!s.trim().equalsIgnoreCase("")) {
                res.add(s.trim());
            }
        }
        return res;
    }

    public static String getTrimmedResponse(String response) {
        String[] x = response.split("<body>");
        String[] y = x[1].split("</body>");
        return y[0].trim();
    }

    public static Map<String, String> getUrlParamsFromMessage(Message m) {
        Map<String, String> res = new LinkedHashMap<>();
        res.put("id", m.getId());
        res.put("contenuto", m.getContenuto());
        res.put("mittente", m.getMittente());
        res.put("destinatario", m.getDestinatario());
        res.put("data_invio", m.getDataInvio());
        res.put("ora_invio", m.getOraInvio());
        return res;
    }
}
