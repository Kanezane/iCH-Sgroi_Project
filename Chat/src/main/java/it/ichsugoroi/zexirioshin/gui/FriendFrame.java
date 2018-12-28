package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.utils.StringReferences;
import it.ichsugoroi.zexirioshin.utils.ThreadableUtils;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Thread.sleep;


public class FriendFrame extends JFrame implements ActionListener {
    private List<String> friendsList;
    private String clientUsername;
    private DefaultTableModel dtm;
    private HttpRequest httpRequest = new HttpRequest();
    private FriendFrame summoner = this;
    private Thread checkNewIncomingFriend;
    private Thread updateFriendTableSometimes;


    public FriendFrame(String username) {
        this.clientUsername = username;
        init();
    }

    private void init() {
        checkNewIncomingFriend = checkNewIncomingFriend();
        checkNewIncomingFriend.start();
        updateFriendTableSometimes = updateFriendTableSometimes();
        updateFriendTableSometimes.start();

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.updateStatus(clientUsername, StringReferences.ONLINESTATUS);

        friendsList = populateFriendListSearchingByUsername(clientUsername);
        friendsList.sort(String::compareToIgnoreCase);
        Object columnNames[] = getColumnNames();
        initJTable(getRowData(), columnNames, clientUsername);

        initBarPanel();

        setTitle("Lista amici di " + clientUsername);
        setLayout(new GridLayout(1,1));
        setSize(300,400);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                httpRequest.updateStatus(clientUsername, StringReferences.OFFLINESTATUS);
                ThreadableUtils.killThread(updateFriendTableSometimes, checkNewIncomingFriend);
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private List<String> chatOpened = new ArrayList<>();

    public void removeChatFromOpenedChatList(String friendUsername) {
        System.out.println(friendUsername);
        if(chatOpened.remove(friendUsername)) {
            System.out.println("ho rimosso");
        } else {
            System.out.println("non ho rimosso");
        }
    }

    private boolean checkIfChatIsAlreadyOpened(int row) {
        for(String s : chatOpened) {
            if(s.equalsIgnoreCase(friendsList.get(row))) {
                return true;
            }
        }
        return false;
    }

    private void initJTable(Object rowData[][], Object columnNames[], String username) {
        dtm = new DefaultTableModel(rowData, columnNames);
        JTable friendTable = new JTable(dtm) {
            public boolean editCellAt( int row
                                     , int column
                                     , java.util.EventObject e ) {
                return false;
            }
        };
        friendTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    int row = friendTable.rowAtPoint(e.getPoint());
                    if(!checkIfChatIsAlreadyOpened(row)) {
                        new ChatForm(username, friendsList.get(row), summoner);
                        chatOpened.add(friendsList.get(row));
                    } else {
                        System.out.println("Chat già aperta!");
                    }
                }
            }
        });

        friendTable.setComponentPopupMenu(initJPopupMenu(friendTable, summoner));


        JScrollPane scrollPane = new JScrollPane(friendTable);
        add(scrollPane);
    }

    int selectedRow;
    private JPopupMenu initJPopupMenu(JTable friendTable, JFrame summoner) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Rimuovi amico");
        deleteItem.addActionListener(e -> {
            int reply = JOptionPane.showConfirmDialog(summoner, "Vuoi davvero eliminare " + friendsList.get(selectedRow) + " dalla lista amici?");
            if(reply==JOptionPane.YES_OPTION) {
                httpRequest.removeFriend(clientUsername, friendsList.get(selectedRow));
                friendsList.remove(selectedRow);
                dtm.setDataVector(getRowData(), getColumnNames());
                dtm.fireTableDataChanged();
                summoner.repaint();
                JOptionPane.showMessageDialog(summoner, "Amico rimosso con successo!");
            }

        });
        popupMenu.add(deleteItem);

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    selectedRow = friendTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), friendTable));
                    if (selectedRow > -1) {
                        friendTable.setRowSelectionInterval(selectedRow, selectedRow);
                    }
                    System.out.println("hai selezionato la riga " + selectedRow );
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        return popupMenu;
    }

    private void initBarPanel() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu addMenu = new JMenu("Aggiungi");
        menuBar.add(addMenu);

        JMenuItem addNewFriendItem = new JMenuItem("Aggiungi nuovo amico");
        addMenu.add(addNewFriendItem);
        addNewFriendItem.addActionListener(this);

        JMenu questionMenu = new JMenu("?");
        menuBar.add(questionMenu);

        JMenuItem logoutItem = new JMenuItem("Logout");
        questionMenu.add(logoutItem);
        logoutItem.addActionListener(this);
    }

    private Object[][] getRowData() {
        Object rowData[][] = new Object[friendsList.size()][1];
        for(int i=0; i<friendsList.size(); i++) {
            rowData[i][0] = " "+friendsList.get(i);
        }

        return rowData;
    }

    private Object[] getColumnNames() {
        return new Object[]{"Amici"};

    }

    private List<String> populateFriendListSearchingByUsername(String username) {
        return httpRequest.getAcceptedFriendsList(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("Logout")) {
            UserInfo.deleteUserNameFolderIfExists();
            setVisible(false);
            ThreadableUtils.killThread(updateFriendTableSometimes, checkNewIncomingFriend);
            System.out.println("Shutting down " + clientUsername + " instances()...");
            new LoginForm();
        }

        if(e.getActionCommand().equalsIgnoreCase("Aggiungi nuovo amico")) {
            new AddNewFriendPane(this, clientUsername, friendsList);
        }
    }

    public void updateFriendJTable() {
        friendsList = populateFriendListSearchingByUsername(clientUsername);
        dtm.setDataVector(getRowData(), getColumnNames());
        dtm.fireTableDataChanged();
        this.repaint();
    }

    private List<String> incomingNewFriendList;
    private Thread checkNewIncomingFriend() {
        Thread t = new Thread(() -> {
            boolean shouldDie = false;
            while (!shouldDie) {
                try {
                    sleep(10000);
                    System.out.println("Checking for new friends()...");
                    incomingNewFriendList = httpRequest.checkForIncomingNewFriend(clientUsername);
                    if (incomingNewFriendList.size() > 0) {
                        for (String newPossibleFriend : incomingNewFriendList) {
                            int reply = JOptionPane.showConfirmDialog(summoner, newPossibleFriend + " ti ha aggiunto alla lista amici. Accetti la sua amicizia?");
                            if (reply == JOptionPane.YES_OPTION) {
                                httpRequest.updateFriendStatus(newPossibleFriend, clientUsername, StringReferences.ACCEPTEDFRIENDSTATUS);
                                httpRequest.addNewFriend(clientUsername, newPossibleFriend, StringReferences.ACCEPTEDFRIENDSTATUS);
                                updateFriendJTable();
                            } else {
                                httpRequest.removeFriend(newPossibleFriend, clientUsername);
                            }
                        }
                    }
                } catch(InterruptedException ex){
                    shouldDie = true;
                }
            }
        });
        return t;
    }

    private Thread updateFriendTableSometimes() {
        Thread t = new Thread(() -> {
            boolean shouldDie = false;
            while(!shouldDie) {
                try {
                    sleep(5000);
                    System.out.println("Update friend table()...");
                    friendsList = populateFriendListSearchingByUsername(clientUsername);
                    dtm.setDataVector(getRowData(), getColumnNames());
                    dtm.fireTableDataChanged();
                    this.repaint();
                } catch (InterruptedException ex) {
                    shouldDie = true;
                }
            }
        });
        return t;
    }
}
