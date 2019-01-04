package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.User;
import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import javax.swing.JOptionPane;

public class LoginForm extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        memberLoginLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        rememberCBox = new javax.swing.JCheckBox();
        loginButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        registerButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        memberLoginLabel.setText("Member Login");

        rememberCBox.setText("Remember?");

        loginButton.setText("Connettiti");

        jLabel1.setText("Devi registrarti?");

        registerButton.setText("Registrati");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rememberCBox)
                                    .addComponent(jLabel1))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(registerButton)
                                    .addComponent(loginButton)))
                            .addComponent(usernameField)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(memberLoginLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(memberLoginLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rememberCBox)
                    .addComponent(loginButton))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(registerButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        memberLoginLabel.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel memberLoginLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    private javax.swing.JCheckBox rememberCBox;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables

    private HttpRequest httpRequest = new HttpRequest();
    private User user;
    
    public LoginForm() {
        initComponents();
        init();
    }
    
    private void init() {
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
            initFrame();
        }
    }
    
    private void initFrame() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void doLoginFromSavedInfo(String username, String password) {
        String response = httpRequest.login(username, password);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        if(response.equalsIgnoreCase("Nome utente o password non corretti!")) {
            JOptionPane.showMessageDialog(this, "I dati salvati non sono corretti, ripetete nuovamente il login");
            UserInfo.deleteUserNameFolderIfExists();
            initFrame();
        } else {
            System.out.println("Login effettuato con successo attraverso i dati salvati!");
            new FriendForm(username);
        }
    }

    private void doLoginFromButton() {
        String response = httpRequest.login(usernameField.getText(), String.valueOf(passwordField.getPassword()));
        if(response.equalsIgnoreCase("Nome utente o password non corretti!")) {
            JOptionPane.showMessageDialog(this
                    , "Impossibile effettuare il login:\n"
                    + "Dati non corretti!");
            usernameField.setText("");
            passwordField.setText("");
        } else {
            System.out.println("Login effettuato con successo!");
            if(rememberCBox.isSelected()) {
                UserInfo.createRoamingDirIfNotAlreadyExists();
                UserInfo.createUserNameFileInRoamingDir(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                user = UserInfo.getUserInfo();
                new FriendForm(user.getName());
            } else {
                new FriendForm(usernameField.getText());
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
