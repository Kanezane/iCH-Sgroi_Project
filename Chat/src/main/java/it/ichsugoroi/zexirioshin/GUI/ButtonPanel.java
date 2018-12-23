package it.ichsugoroi.zexirioshin.GUI;

import it.ichsugoroi.zexirioshin.utils.ApplicationUtils;
import it.ichsugoroi.zexirioshin.web.HttpMessage;
import it.ichsugoroi.zexirioshin.web.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {

    private JButton button;
    private MainFrame principalInterface;

    private String senderUsername;
    private String receiverUsername;

    public ButtonPanel(MainFrame principalInterface, String senderUsername, String receiverUsername) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.principalInterface = principalInterface;
        init();
    }

    private void init() {
        button = new JButton("Send");
        button.addActionListener(this);
        add(button);
        setLayout(new GridLayout(1,1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().trim().equalsIgnoreCase("Send")) {
            Message message = new Message();
            message.setId(ApplicationUtils.getUUID());
            message.setMittente(senderUsername);
            message.setDestinatario(receiverUsername);
            message.setContenuto(principalInterface.getTextFromTextField());
            message.setDataInvio(ApplicationUtils.getCurrentDate());
            message.setOraInvio(ApplicationUtils.getCurrentTime());
            HttpMessage httpMessage = new HttpMessage();
            httpMessage.send(message);
            principalInterface.addNewRowToHistory(message.getMittente() + ": " + message.getContenuto());
            principalInterface.removeTextFromTextField();
        }
    }
}
