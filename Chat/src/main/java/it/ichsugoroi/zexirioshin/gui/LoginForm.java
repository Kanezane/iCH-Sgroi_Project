package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.User;
import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JButton sendButton;
    private JPasswordField passwordField;
    private JCheckBox rememberCBox;
    private JPanel principalPanel;
    private HttpRequest httpRequest = new HttpRequest();
    private User user;
    private JLabel memberLoginLabel;
    private JButton registerButton;


    public LoginForm() {
        setTitle("Login");

        sendButton.addActionListener(e -> {
            if(e.getActionCommand().equalsIgnoreCase("Send")) {
                doLogin();
            }
        });

        registerButton.addActionListener(e -> {
            if(e.getActionCommand().equalsIgnoreCase("Registrati")) {
                setVisible(false);
                new RegisterForm();
            }
        });

        if(checkIfThereIsAlreadyUserSaved()) {
            String senderUsername = user.getName();
            new MainFrame(senderUsername, getReceiverUserNameBySenderUsername(senderUsername));
        } else {
            setContentPane(principalPanel);
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

    }

    private String getReceiverUserNameBySenderUsername(String senderUsername) {
        String res;
        if(senderUsername.equalsIgnoreCase("Shin")) {
            res = "Zetto";
        } else {
            res = "Shin";
        }
        return res;
    }

    private void doLogin() {
        String response = httpRequest.login(usernameField.getText(), String.valueOf(passwordField.getPassword()));
        if(response.equalsIgnoreCase("Nome utente o password non corretti!")) {
            JOptionPane.showMessageDialog(this
                    , "Impossibile effettuare il login:\n "
                            + "Dati non corretti!");
            usernameField.setText("");
            passwordField.setText("");
        } else {
            System.out.println("Login effettuato con successo!");
            if(rememberCBox.isSelected()) {
                UserInfo.createRoamingDirIfNotAlreadyExists();
                UserInfo.createUserNameFileInRoamingDir(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                user = UserInfo.getUserInfo();
                new MainFrame(user.getName(), getReceiverUserNameBySenderUsername(user.getName()));
            } else {
                UserInfo.deleteUserNameFolderIfExists();
            }
            setVisible(false);
        }
    }

    private boolean checkIfThereIsAlreadyUserSaved() {
        if(UserInfo.checkIfRoamingDirExists()) {
            user = UserInfo.getUserInfo();
            return true;
        } else {
            user = null;
            return false;
        }
    }
}
