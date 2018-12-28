package it.ichsugoroi.zexirioshin.app;

import it.ichsugoroi.zexirioshin.web.Message;

import java.util.List;

public interface IHttpRequest {
    void send(Message msg);
    void delete(Message msg);
    List<Message> searchIncomingMessage(String mittente, String destinatario);
    void updateStatus(String username, String status);
    String checkStatus(String username);
    String register(String username, String password);
    String login(String username, String password);
    List<String> getAcceptedFriendsList(String username);
    String checkIfUserExists(String username);
    void addNewFriend(String usernameAdder, String usernameAdded, String status);
    void removeFriend(String clientUsername, String friendUsername);
    List<String> checkForIncomingNewFriend(String clientUsername);
    void updateFriendStatus(String clientUsername, String newFriend, String relationship_status);
}
