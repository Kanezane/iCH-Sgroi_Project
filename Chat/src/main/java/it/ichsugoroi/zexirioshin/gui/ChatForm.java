package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.app.IHttpRequest;
import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.utils.ThreadableUtils;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import it.ichsugoroi.zexirioshin.web.Message;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.*;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class ChatForm extends javax.swing.JFrame {
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        receiverLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyArea = new javax.swing.JTextArea();
        messageField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        receiverLabel.setText("jLabel1");

        statusLabel.setText("Checking status()...");

        historyArea.setEditable(false);
        historyArea.setColumns(20);
        historyArea.setLineWrap(true);
        historyArea.setRows(5);
        historyArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(historyArea);

        messageField.setPreferredSize(new java.awt.Dimension(6, 27));

        sendButton.setText("Send");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(receiverLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(receiverLabel)
                    .addComponent(statusLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea historyArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField messageField;
    private javax.swing.JLabel receiverLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables

    private String senderUsername;
    private String receiverUsername;
    private String receiverStatus;

    private FriendForm summoner;
    private boolean isFrameMinimized;
    private List<String> history = new ArrayList<>();
    private IHttpRequest httpRequest = new HttpRequest();
    private Thread statusCheckerThread;
    private Thread incomingMessageThread;
    private Thread windowSituationThread;
    private List<Message> messageToDelete = new ArrayList<>();


    public ChatForm( String senderUsername
                   , String receiverUsername
                   , FriendForm summoner) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.summoner = summoner;
        initComponents();
        init();
    }
    
    private void removeThisWindowFromOpenedWindow() {
        summoner.removeChatFromOpenedChatList(receiverUsername, this);
    }

    private void init() {
        receiverLabel.setText(receiverUsername + ":   ");

        statusCheckerThread = checkStatus();
        statusCheckerThread.start();
        incomingMessageThread = checkForIncomingMessage();
        incomingMessageThread.start();
        windowSituationThread = windowSituationThread();
        windowSituationThread.start();

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
                shutDownChat();
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
        
        initFrame();
    }

    private void shutDownChat() {
        System.out.println("Shutting down " + receiverUsername + " chat window()...");
        ThreadableUtils.killThread(statusCheckerThread, incomingMessageThread, windowSituationThread);
        removeThisWindowFromOpenedWindow();
        setVisible(false);
    }

    private void initFrame() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String getTextFromTextField() { return messageField.getText(); }

    private void removeTextFromTextField() { messageField.setText(""); }

    private void addNewRowToHistory(String newRow) {
        history.add(newRow);
        repaintHistory();
    }

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
                    //System.out.println("check incoming message()...");
                    msgs = httpRequest.searchIncomingMessage(receiverUsername, senderUsername);
                    if(msgs.size()!=0) {
                        for(Message m : msgs) {
                            SwingUtilities.invokeLater(() -> addNewRowToHistory(m.getMittente() + ": " + m.getContenuto()));
                            if(isFrameMinimized || !isFocused()) {
                                notifyUser(m);
                                httpRequest.updateMsgStatusToReceived(m.getId());
                                messageToDelete.add(m);
                            } else {
                                httpRequest.delete(m);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    shouldDie = true;
                }
            }
        });
    }

    private Thread checkStatus() {
        return new Thread(() -> {
            boolean shouldDie = false;
            while(!shouldDie) {
                try {
                    receiverStatus = httpRequest.checkStatus(receiverUsername);
                    //System.out.println("check " + receiverUsername + " status()...");
                    setStatusToJLabel(receiverStatus);
                    sleep(5000);
                } catch (InterruptedException e) {
                    shouldDie = true;
                }
            }
        });
    }

    private Thread windowSituationThread() {
        return new Thread(()->{
            boolean shouldDie = false;
            while(!shouldDie) {
                try {
                    if(!isFrameMinimized && isFocused()) {
                        for(Message m : messageToDelete) {
                            httpRequest.delete(m);
                        }
                        messageToDelete.clear();
                    }
                    sleep(1000);
                } catch (InterruptedException ex) {
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

    private void sendMessage() {
        String content = getTextFromTextField();
        if(content.isEmpty()){
            //DO NOTHING
        } else {
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
            if(content.trim().endsWith("'")) {
                res.append("''");
            }
            return res.toString();
        } else {
            return content;
        }
    }
    
    public void closeForm() { shutDownChat(); }

}
