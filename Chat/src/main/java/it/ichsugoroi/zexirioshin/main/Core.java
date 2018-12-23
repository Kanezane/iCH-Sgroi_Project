package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.GUI.MainFrame;
import it.ichsugoroi.zexirioshin.web.HttpMessage;
import it.ichsugoroi.zexirioshin.web.IPAddress;


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

        MainFrame mf = new MainFrame(senderUsername, receiverUsername);
        mf.run();

        HttpMessage httpMessage = new HttpMessage();
//      httpMessage.send(getParams());

        httpMessage.search(IPAddress.getMyIPAddress(), IPAddress.getMyIPAddress());

        System.out.println(System.getenv("APPDATA"));

    }
}
