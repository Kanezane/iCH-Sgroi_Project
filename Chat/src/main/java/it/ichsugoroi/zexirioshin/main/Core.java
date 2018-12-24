package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.gui.MainFrame;


public class Core {
    private static String senderUsername;
    private static String receiverUsername;

    public static void main(String[] argv) {
        doMain();
    }

    private static void doMain() {
        senderUsername = UserInfo.getUserNameInfo().trim();
        if(senderUsername.equalsIgnoreCase("Shin")) {
            receiverUsername = "Zetto";
        } else {
            receiverUsername = "Shin";
        }

        new MainFrame(senderUsername, receiverUsername);
    }
}
