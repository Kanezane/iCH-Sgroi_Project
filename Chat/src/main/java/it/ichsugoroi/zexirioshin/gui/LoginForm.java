package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.User;
import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JCheckBox rememberCBox;
    private JPanel principalPanel;
    private HttpRequest httpRequest = new HttpRequest();
    private User user;
    private JLabel memberLoginLabel;
    private JButton registerButton;


    public LoginForm() {
        setTitle("Login");

        loginButton.addActionListener(e -> {
            if(e.getActionCommand().equalsIgnoreCase("Connettiti")) {
                doLoginFromButton();
            }
        });

        registerButton.addActionListener(e -> {
            if(e.getActionCommand().equalsIgnoreCase("Registrati")) {
                setVisible(false);
                new RegisterForm();
            }
        });

        if(checkIfThereIsAlreadyUserSaved()) {
            doLoginFromSavedInfo(user.getName(), user.getPassword());
        } else {
            initFrameComponents();
        }

    }

    private void initFrameComponents() {
        setContentPane(principalPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void doLoginFromSavedInfo(String username, String password) {
        String response = httpRequest.login(username, password);
        if(response.equalsIgnoreCase("Nome utente o password non corretti!")) {
            JOptionPane.showMessageDialog(this, "I dati salvati non sono corretti, ripetete nuovamente il login");
            UserInfo.deleteUserNameFolderIfExists();
            initFrameComponents();
        } else {
            System.out.println("Login effettuato con successo attraverso i dati salvati!");
            new FriendFrame(username);
        }
    }

    private void doLoginFromButton() {
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
                new FriendFrame(user.getName());
            } else {
                new FriendFrame(usernameField.getText());
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
