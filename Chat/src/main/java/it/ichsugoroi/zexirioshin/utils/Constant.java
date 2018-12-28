package it.ichsugoroi.zexirioshin.utils;

import java.io.File;

public class Constant {
    public static final String SENDERLINK = "http://dprssn.altervista.org/SendMessage.php";
    public static final String SEARCHERLINK = "http://dprssn.altervista.org/MessageList.php";
    public static final String DELETERLINK = "http://dprssn.altervista.org/DeleteMessage.php";
    public static final String SETSTATUSLINK = "http://dprssn.altervista.org/SetStatus.php";
    public static final String CHECKSTATUSLINK = "http://dprssn.altervista.org/CheckStatus.php";
    public static final String REGISTERLINK = "http://dprssn.altervista.org/Register.php";
    public static final String LOGINLINK = "http://dprssn.altervista.org/Login.php";
    public static final String FRIENDLISTLINK = "http://dprssn.altervista.org/FriendList.php";
    public static final String CHECKIFUSEREXISTSLINK = "http://dprssn.altervista.org/CheckIfUserExists.php";
    public static final String ADDINGNEWFRIENDLINK = "http://dprssn.altervista.org/AddingNewFriend.php";
    public static final String REMOVELINK = "http://dprssn.altervista.org/RemoveFriend.php";

    public static final String APPDATAROAMINGPATH = System.getenv("APPDATA") + File.separator + "infochat";
    public static final String USERNAMEFILE = APPDATAROAMINGPATH + File.separator + "username.txt";

    public static final String OFFLINESTATUS = "OFFLINE";
    public static final String ONLINESTATUS = "ONLINE";
}
