package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.app.IHttpRequest;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.utils.Constant;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import it.ichsugoroi.zexirioshin.web.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class ChatFrame extends JFrame implements ActionListener {
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea historyArea;
    private JLabel statusLabel;
    private JPanel principalPanel;
    private JLabel receiverLabel;

    private String senderUsername;
    private String receiverUsername;
    private String receiverStatus;

    private int row;
    private FriendFrame summoner;

    private boolean isFrameMinimized;

    private List<String> history = new ArrayList<>();

    private IHttpRequest httpRequest = new HttpRequest();


    public ChatFrame( String senderUsername
                    , String receiverUsername
                    , int row
                    , FriendFrame summoner) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.row = row;
        this.summoner = summoner;
        init();
    }

    private void removeThisWindowFromOpenedWindow() {
        summoner.removeRowFromOpenedRowList(row);
    }

    private void init() {
        receiverLabel.setText(receiverUsername + ":   ");

        Thread statusCheckerThread = checkStatus();
        statusCheckerThread.start();
        Thread incomincMessageThread = checkForIncomingMessage();
        incomincMessageThread.start();

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
                System.out.println("Shutting down " + receiverUsername + " chat window()...");
                statusCheckerThread.interrupt();
                incomincMessageThread.interrupt();
                removeThisWindowFromOpenedWindow();
                setVisible(false);
            }
        });

        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                int oldState = e.getOldState();
                int newState = e.getNewState();
                if ((oldState & Frame.ICONIFIED) == 0 && (newState & Frame.ICONIFIED) != 0) {
                    isFrameMinimized = true;
                } else if ((oldState & Frame.ICONIFIED) != 0 && (newState & Frame.ICONIFIED) == 0) {
                    isFrameMinimized = false;
                }
            }
        });
        setContentPane(principalPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



    private String getTextFromTextField() { return messageField.getText(); }
    private void addNewRowToHistory(String newRow) {
        history.add(newRow);
        repaintHistory();
    }
    private void removeTextFromTextField() { messageField.setText(""); }

    private void setStatusToJLabel(String statusToSet) {
        statusLabel.setText("");
        if(statusToSet.equalsIgnoreCase("OFFLINE")) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(new Color(65, 163, 40));
        }
        statusLabel.setText(statusToSet);
        statusLabel.repaint();
    }

    private void repaintHistory() {
        historyArea.setText("");
        for(String s : history) {
            historyArea.append(s);
            historyArea.append("\n");
        }
        historyArea.repaint();
    }

    private Thread checkForIncomingMessage() {
        return new Thread(() -> {
            boolean shouldDie = false;
            List<Message> msgs;
            while(!shouldDie) {
                try {
                    sleep(1000);
                    System.out.println("check incoming message()...");
                    msgs = httpRequest.search(receiverUsername, senderUsername);
                    if(msgs.size()!=0) {
                        for(Message m : msgs) {
                            addNewRowToHistory(m.getMittente() + ": " + m.getContenuto());
                            if(isFrameMinimized || !isFocused()) { notifyUser(m); }
                            httpRequest.delete(m);
                        }
                    }
                } catch (InterruptedException e) {
                    shouldDie = true;
                }
            }
        });
    }

    private void notifyUser(Message m) {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "test");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        trayIcon.displayMessage(m.getMittente(), m.getContenuto(), TrayIcon.MessageType.INFO);
    }

    private Thread checkStatus() {
        return new Thread(() -> {
            boolean shouldDie = false;
            while(!shouldDie) {
                try {
                    receiverStatus = httpRequest.checkStatus(receiverUsername);
                    System.out.println("check " + receiverUsername + " status()...");
                    setStatusToJLabel(receiverStatus);
                    sleep(5000);
                } catch (InterruptedException e) {
                    shouldDie = true;
                }
            }
        });
    }

    private void sendMessage() {
        String content = getTextFromTextField();
        Message message = new Message();
        message.setId(ApplicationUtils.getUUID());
        message.setMittente(senderUsername);
        message.setDestinatario(receiverUsername);
        message.setContenuto(checkIfThereIsAQuoteInNewMsg(content));
        message.setDataInvio(ApplicationUtils.getCurrentDate());
        message.setOraInvio(ApplicationUtils.getCurrentTime());
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.send(message);
        addNewRowToHistory(message.getMittente() + ": " + content);
        removeTextFromTextField();
    }

    private String checkIfThereIsAQuoteInNewMsg(String content) {
        if(content.contains("'")) {
            StringBuilder res = new StringBuilder();
            String[] x = content.split("'");
            int cont = 0;
            for(String s : x) {
                res.append(s);
                cont++;
                if(cont != x.length) {
                    res.append("''");
                }
            }
            return res.toString();
        } else {
            return content;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
