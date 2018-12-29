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
    private List<ChatForm> openedChatInstances = new ArrayList<>();
    private List<String> friendUsernameChatOpened = new ArrayList<>();


    public FriendFrame(String username) {
        this.clientUsername = username;
        init();
    }

    private void init() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.updateStatus(clientUsername, StringReferences.ONLINESTATUS);

        friendsList = populateFriendListSearchingByUsername(clientUsername);
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

        checkNewIncomingFriend = checkNewIncomingFriend();
        checkNewIncomingFriend.start();
        updateFriendTableSometimes = updateFriendTableSometimes();
        updateFriendTableSometimes.start();
    }

    public void removeChatFromOpenedChatList(String friendUsername, ChatForm instance) {
        openedChatInstances.remove(instance);
        friendUsernameChatOpened.remove(friendUsername);
    }

    private boolean checkIfChatIsAlreadyOpened(int row) {
        for(String s : friendUsernameChatOpened) {
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
                        ChatForm cf = new ChatForm(username, friendsList.get(row), summoner);
                        friendUsernameChatOpened.add(friendsList.get(row));
                        openedChatInstances.add(cf);
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
        List<String> res = httpRequest.getAcceptedFriendsList(username);
        res.sort(String::compareToIgnoreCase);
        return res;
    }

    private void shutDownFriendFrame() {
        System.out.println("Shutting down " + clientUsername + " instances()...");
        ThreadableUtils.killThread(updateFriendTableSometimes, checkNewIncomingFriend);
        for(ChatForm cf : openedChatInstances) {
            SwingUtilities.invokeLater(()->cf.closeForm());
        }
        friendUsernameChatOpened.clear();
        openedChatInstances.clear();
        setVisible(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("Logout")) {
            UserInfo.deleteUserNameFolderIfExists();
            shutDownFriendFrame();
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
                    System.out.println("Checking for new friends()...");
                    incomingNewFriendList = httpRequest.checkForIncomingNewFriend(clientUsername);
                    if (incomingNewFriendList.size() > 0) {
                        for (String newPossibleFriend : incomingNewFriendList) {
                            System.out.println(newPossibleFriend);
                            int reply = JOptionPane.showConfirmDialog(summoner, newPossibleFriend + " ti ha aggiunto alla lista amici. Accetti la sua amicizia?");
                            if (reply == JOptionPane.YES_OPTION) {
                                httpRequest.updateFriendStatus(newPossibleFriend, clientUsername, StringReferences.ACCEPTEDFRIENDSTATUS);
                                httpRequest.addNewFriend(clientUsername, newPossibleFriend, StringReferences.ACCEPTEDFRIENDSTATUS);
                                updateFriendJTable();
                                JOptionPane.showMessageDialog(summoner, newPossibleFriend + " aggiunto alla lista amici!");
                            } else {
                                httpRequest.removeFriend(newPossibleFriend, clientUsername);
                                JOptionPane.showMessageDialog(summoner, "Richiesta d'amicizia respinta!");
                            }
                        }
                    }
                    sleep(5000);
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
                    System.out.println("Update friend table()...");
                    updateFriendJTable();
                    sleep(5000);
                } catch (InterruptedException ex) {
                    shouldDie = true;
                }
            }
        });
        return t;
    }
}
