package it.ichsugoroi.zexirioshin.GUI;

import it.ichsugoroi.zexirioshin.web.HttpMessage;
import it.ichsugoroi.zexirioshin.web.Message;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import static java.lang.Thread.sleep;


public class MainFrame extends JFrame implements Runnable{

    private HistoryPanel textarea;
    private TextPanel textBoxArea;
    private ButtonPanel buttonarea;

    private String senderUsername;
    private String receiverUsername;

    public MainFrame(String senderUsername, String receiverUsername) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        init();
    }

    private void init() {
        setTitle("Chat");

        textarea = new HistoryPanel(this);
        add(textarea);

        textBoxArea = new TextPanel();
        add(textBoxArea);

        buttonarea = new ButtonPanel(this, senderUsername, receiverUsername);
        add(buttonarea);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new GridLayout(3,1));
        pack();
        setVisible(true);
    }

    public String getTextFromTextField() { return textBoxArea.getTextFromTextField(); }
    public void addNewRowToHistory(String newRow) { textarea.addNewRowToHistory(newRow); }

    @Override
    public void run() {
        boolean shouldDie = false;
        HttpMessage httpMessage = new HttpMessage();
        List<Message> msgs;
        while(!shouldDie) {
            try {
                sleep(5000);
                System.out.println("poll()...");
                msgs = httpMessage.search(senderUsername, receiverUsername);
                if(msgs.size()!=0) {
                    for(Message m : msgs) {
                        textarea.addNewRowToHistory(m.getContenuto());
                        httpMessage.delete(m);
                    }
                    shouldDie = true;
                } else {
                    shouldDie = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
