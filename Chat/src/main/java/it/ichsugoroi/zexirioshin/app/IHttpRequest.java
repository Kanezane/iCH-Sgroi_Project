package it.ichsugoroi.zexirioshin.app;

import it.ichsugoroi.zexirioshin.web.Message;

import java.util.List;

public interface IHttpRequest {
    void send(Message msg);
    void delete(Message msg);
    List<Message> search(String mittente, String destinatario);
    void updateStatus(String username, String status);
    String checkStatus(String username);
    String register(String username, String password);
    String login(String username, String password);
}
