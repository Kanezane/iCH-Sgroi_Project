package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;


public class RegisterForm extends JFrame{
    private JTextField usernameField;
    private JButton sendButton;
    private JPasswordField passwordField;
    private JPanel principalPanel;
    private JButton loginButton;
    private HttpRequest httpRequest = new HttpRequest();

    public RegisterForm() {
        if(!UserInfo.checkIfRoamingDirExists()) {
            setTitle("Registrazione");
            sendButton.addActionListener(e -> {
                if(e.getActionCommand().equalsIgnoreCase("Send")) {
                    doRegister();
                }
            });

            loginButton.addActionListener(e -> {
                if(e.getActionCommand().equalsIgnoreCase("Login")) {
                    setVisible(false);
                    new LoginForm();
                }
            });
            setContentPane(principalPanel);
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        } else {
            new LoginForm();
        }
    }

    private void doRegister() {
        String response = httpRequest.register(usernameField.getText(), String.valueOf(passwordField.getPassword()));
        if(response.equalsIgnoreCase("Username già in uso!")) {
            JOptionPane.showMessageDialog(this
                    , "Impossibile effettuare la registrazione:\n "
                            + "username " + usernameField.getText() + " già in uso!");
            usernameField.setText("");
            passwordField.setText("");
        } else {
            System.out.println("Utente " + usernameField.getText() + " creato con successo!");
            setVisible(false);
            new LoginForm();
        }
    }
}
