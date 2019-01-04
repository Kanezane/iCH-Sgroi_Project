package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import javax.swing.JOptionPane;

public class RegisterForm extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        sendButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Registrazione");

        sendButton.setText("Registrati");

        jLabel2.setText("Già registrato?");

        loginButton.setText("Login");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(sendButton))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addGap(36, 36, 36)
                                .addComponent(loginButton)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameField))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(loginButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
    private HttpRequest httpRequest = new HttpRequest();

    public RegisterForm() {
        initComponents();
        init();
    }
    
    private void init() {
        if(!UserInfo.checkIfRoamingDirExists()) {
            setTitle("Registrazione");
            sendButton.addActionListener(e -> {
                if(e.getActionCommand().equalsIgnoreCase("Registrati")) {
                    doRegister();
                }
            });

            loginButton.addActionListener(e -> {
                if(e.getActionCommand().equalsIgnoreCase("Login")) {
                    setVisible(false);
                    new LoginForm();
                }
            });

            initFrame();

        } else {
            new LoginForm();
        }
    }
    
    private void initFrame() {
        setLocationRelativeTo(null);
        setVisible(true);
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
            JOptionPane.showMessageDialog(this, "Utente " + usernameField.getText() + " creato con successo!\nOra verrai reindirizzato al form di login!");
            setVisible(false);
            new LoginForm();
        }
    }
}
