package it.ichsugoroi.zexirioshin.app;

import it.ichsugoroi.zexirioshin.web.Message;

public interface IHttpMessage {
    void send(Message msg);
    void delete(Message msg);
    void search(String mittente, String destinatario);

}
