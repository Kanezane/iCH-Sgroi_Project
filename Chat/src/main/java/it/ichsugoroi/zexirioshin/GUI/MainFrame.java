package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.app.IHttpRequest;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.utils.Constant;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import it.ichsugoroi.zexirioshin.web.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainFrame extends JFrame{
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea historyArea;
    private JLabel statusLabel;
    private JPanel principalPanel;

    private String senderUsername;
    private String receiverUsername;
    private String receiverStatus;

    private List<String> history = new ArrayList<>();

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


        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
        sendButton.addActionListener(e -> {
            if(e.getActionCommand().equalsIgnoreCase("Send")) {
                sendMessage();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                httpRequest.updateStatus(senderUsername, Constant.OFFLINESTATUS);
                System.exit(0);
            }
        });

        setContentPane(principalPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getTextFromTextField() { return messageField.getText(); }
    public void addNewRowToHistory(String newRow) {
        history.add(newRow);
        repaintHistory();
    }
    public void removeTextFromTextField() { messageField.setText(""); }

    public void setStatusToJLabel(String statusToSet) {
        if(statusToSet.equalsIgnoreCase("OFFLINE")) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(Color.GREEN);
        }
        statusLabel.setText(statusToSet);
        statusLabel.repaint();
    }

    private void repaintHistory() {
        historyArea.setText("");
        int count = 0;
        for(String s : history) {
            historyArea.append(s);
            historyArea.append("\n");
            count++;
        }
        if(count==10) {
            history.clear();
        }
        historyArea.repaint();
    }

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
                            addNewRowToHistory(m.getMittente() + ": " + m.getContenuto());
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
                setStatusToJLabel(receiverStatus);
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new ApplicationException(e);
            }
        });
        t.start();
    }

    public void sendMessage() {
        Message message = new Message();
        message.setId(ApplicationUtils.getUUID());
        message.setMittente(senderUsername);
        message.setDestinatario(receiverUsername);
        message.setContenuto(getTextFromTextField());
        message.setDataInvio(ApplicationUtils.getCurrentDate());
        message.setOraInvio(ApplicationUtils.getCurrentTime());
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.send(message);
        addNewRowToHistory(message.getMittente() + ": " + message.getContenuto());
        removeTextFromTextField();
    }
}
