package it.ichsugoroi.zexirioshin.utils;

import java.io.File;

public class Constant {
    public static String SENDERLINK = "http://dprssn.altervista.org/SendMessage.php";
    public static String SEARCHERLINK = "http://dprssn.altervista.org/MessageList.php";
    public static String DELETERLINK = "http://dprssn.altervista.org/DeleteMessage.php";

    public static String APPDATAROAMINGPATH = System.getenv("APPDATA") + File.separator + "infochat";
    public static String USERNAMEFILE = APPDATAROAMINGPATH + File.separator + "username.txt";
}
