package it.ichsugoroi.zexirioshin.App;

import java.util.Map;

public interface IHttpMessage {
    void send(String url, Map<String,String> parameters);
    void delete(String url, Map<String,String> parameters);
    void search(String url);

}
