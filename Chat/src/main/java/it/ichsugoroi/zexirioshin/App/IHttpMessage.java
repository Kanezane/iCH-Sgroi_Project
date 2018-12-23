package it.ichsugoroi.zexirioshin.app;

import it.ichsugoroi.zexirioshin.web.Message;

import java.util.List;

public interface IHttpMessage {
    void send(Message msg);
    void delete(Message msg);
    List<Message> search(String mittente, String destinatario);

}
