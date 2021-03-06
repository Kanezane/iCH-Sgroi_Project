package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.utils.StringReferences;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;
import java.util.List;

public class AddNewFriendPane {

    private List<String> friendsList;
    private final HttpRequest httpRequest = new HttpRequest();

    public AddNewFriendPane(FriendForm summoner, String clientUsername, List<String> friendList) {
        this.friendsList = friendList;

        String newFriend = JOptionPane.showInputDialog(summoner, "Inserisci il nickname dell'amico da aggiungere!");
        if(newFriend.equalsIgnoreCase(clientUsername)) {
            JOptionPane.showMessageDialog(summoner, "Non puoi aggiungere te stesso!");
        } else {
            String possibleNewFriend = checkIfUserExists(newFriend);
            System.out.println(possibleNewFriend);
            if( possibleNewFriend.equalsIgnoreCase("")
                    || possibleNewFriend.equalsIgnoreCase("Alcuni parametri mancanti, impossibile cercare un utente così.")
                    || possibleNewFriend.contains("ERROR: could not")
                    || possibleNewFriend == null) {
                JOptionPane.showMessageDialog(summoner, "Non esiste nessun utente con username " + newFriend + ".");
            } else {
                if(!checkIfIsAlreadyAFriendOfMine(possibleNewFriend)) {
                    int reply = JOptionPane.showConfirmDialog(summoner, "Si vuole aggiungere " + possibleNewFriend + " alla lista amici?");
                    if(reply == JOptionPane.YES_OPTION) {
                        httpRequest.addNewFriend(clientUsername, possibleNewFriend, StringReferences.NEWFRIENDSTATUS);
                        JOptionPane.showMessageDialog(summoner, "Amico aggiunto alla lista amici! ");
                        summoner.updateFriendJTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(summoner, "Esiste già questo amico nella tua lista amici!");
                }
            }
        } 
    }

    private String checkIfUserExists(String username) {
        return httpRequest.checkIfUserExists(username);
    }

    private boolean checkIfIsAlreadyAFriendOfMine(String possibleFriendUsername) {
        for(String s : friendsList) {
            if(s.trim().equalsIgnoreCase(possibleFriendUsername.trim())) {
                return true;
            }
        }
        return false;
    }
}
