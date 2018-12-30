package it.ichsugoroi.zexirioshin.web;

public class MessageNotification implements Comparable<MessageNotification>{
    private String friendUsername;
    private String nMsg;

    public String getFriendUsername() { return friendUsername; }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getnMsg() {
        return nMsg;
    }

    public void setnMsg(String nMsg) {
        this.nMsg = nMsg;
    }

    @Override
    public int compareTo(MessageNotification o) {
        return this.friendUsername.compareTo(o.getFriendUsername());
    }
}
