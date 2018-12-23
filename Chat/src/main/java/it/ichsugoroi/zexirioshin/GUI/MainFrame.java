package it.ichsugoroi.zexirioshin.GUI;

import it.ichsugoroi.zexirioshin.app.IHttpRequest;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.Constant;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import it.ichsugoroi.zexirioshin.web.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import static java.lang.Thread.sleep;


public class MainFrame extends JFrame{

    private HistoryPanel history;
    private TextPanel textBoxArea;
    private ButtonPanel buttonarea;

    private String senderUsername;
    private String receiverUsername;

    private String receiverStatus;

    private IHttpRequest httpRequest = new HttpRequest();

    public MainFrame(String senderUsername, String receiverUsername) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        init();
    }

    private void init() {
        httpRequest.updateStatus(senderUsername, Constant.ONLINESTATUS);
        checkStatus();
        checkForIncomingMessage();

        setTitle("Chat");

        history = new HistoryPanel(this);
        add(history);

        textBoxArea = new TextPanel();
        add(textBoxArea);

        buttonarea = new ButtonPanel(this, senderUsername, receiverUsername);
        add(buttonarea);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                httpRequest.updateStatus(senderUsername, Constant.OFFLINESTATUS);
                System.exit(0);
            }
        });

        setLayout(new GridLayout(3,1));
        pack();
        setVisible(true);
    }

    public String getTextFromTextField() { return textBoxArea.getTextFromTextField(); }
    public void addNewRowToHistory(String newRow) { history.addNewRowToHistory(newRow); }
    public void removeTextFromTextField() { textBoxArea.removeTextFromTextField(); }

    private void checkForIncomingMessage() {
        Thread t = new Thread(()-> {
            boolean shouldDie = false;
            List<Message> msgs;
            while(!shouldDie) {
                try {
                    sleep(1000);
                    System.out.println("poll()...");
                    msgs = httpRequest.search(receiverUsername, senderUsername);
                    if(msgs.size()!=0) {
                        for(Message m : msgs) {
                            history.addNewRowToHistory(m.getMittente() + ": " + m.getContenuto());
                            httpRequest.delete(m);
                        }
                    }
                } catch (InterruptedException e) {
                    shouldDie = true;
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void checkStatus() {
        Thread t = new Thread(() -> {
            try {
                receiverStatus = httpRequest.checkStatus(receiverUsername);
                System.out.println(receiverStatus);
                sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new ApplicationException(e);
            }
        });
        t.start();
    }
}
