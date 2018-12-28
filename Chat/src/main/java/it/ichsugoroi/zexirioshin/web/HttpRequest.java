package it.ichsugoroi.zexirioshin.web;

import it.ichsugoroi.zexirioshin.app.IHttpRequest;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.utils.CloseableUtils;
import it.ichsugoroi.zexirioshin.utils.StringReferences;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest implements IHttpRequest {

    @Override
    public List<String> getAcceptedFriendsList(String username) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        return getFriendListFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.FRIENDLISTLINK, params));
    }

    @Override
    public String register(String username, String password) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        return getResponseFromUrl(params, StringReferences.REGISTERLINK);
    }

    @Override
    public String login(String username, String password) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        return getResponseFromUrl(params, StringReferences.LOGINLINK);
    }

    @Override
    public void send(Message msg) {
        System.out.println("sending()...");
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.SENDERLINK, ApplicationUtils.getUrlParamsFromMessage(msg)));
    }

    @Override
    public void delete(Message msg) {
        System.out.println("delete()...");
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.DELETERLINK, ApplicationUtils.getUrlParamsFromMessage(msg)));
    }

    @Override
    public List<Message> searchIncomingMessage(String mittente, String destinatario) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("mittente", mittente);
        params.put("destinatario", destinatario);
        return getMessageListFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.SEARCHERLINK, params));
    }

    @Override
    public void updateStatus(String username, String status) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("status", status);
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.SETSTATUSLINK, params));
    }

    @Override
    public String checkStatus(String username) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        return getStatusFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.CHECKSTATUSLINK, params));
    }

    @Override
    public String checkIfUserExists(String username) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        return getExistingFriendResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.CHECKIFUSEREXISTSLINK, params));
    }

    @Override
    public void addNewFriend(String usernameAdder, String usernameAdded, String status) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("usernameAdder", usernameAdder);
        params.put("usernameAdded", usernameAdded);
        params.put("friendship_status", status);
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.ADDINGNEWFRIENDLINK, params));
    }

    @Override
    public void removeFriend(String clientUsername, String friendUsername) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("clientUsername", clientUsername);
        params.put("friendUsername", friendUsername);
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.REMOVELINK, params));
    }

    @Override
    public List<String> checkForIncomingNewFriend(String clientUsername) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("clientUsername", clientUsername);
        return getFriendListFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.CHECKINCOMINGNEWFRIEND, params));
    }

    @Override
    public void updateFriendStatus(String clientUsername, String newFriend, String relationship_status) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("clientUsername", clientUsername);
        params.put("newFriend", newFriend);
        params.put("relationship_status", relationship_status);
        printResponseFromUrl(ApplicationUtils.getCompleteUrlWithParameters(StringReferences.UPDATENEWFRIENDSTATUSLINK, params));
    }

    private String getExistingFriendResponseFromUrl(String url) {
        HttpURLConnection conn = setConnection(url);
        String response = getResponseStringFromUrl(conn).toString();
        conn.disconnect();
        return ApplicationUtils.getTrimmedResponse(response);
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

    private List<String> getFriendListFromUrl(String url) {
        HttpURLConnection conn = setConnection(url);
        String response = getResponseStringFromUrl(conn).toString();
        conn.disconnect();
        return ApplicationUtils.getFriendListFromFriendLinkResponse(response);
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
