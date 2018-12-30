package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.utils.StringReferences;
import it.ichsugoroi.zexirioshin.utils.ThreadableUtils;
import it.ichsugoroi.zexirioshin.web.HttpRequest;
import it.ichsugoroi.zexirioshin.web.MessageNotification;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class FriendForm extends javax.swing.JFrame implements ActionListener{

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        friendTable = new javax.swing.JTable() {
            private Border outside = new MatteBorder(1, 0, 1, 0, Color.BLACK);
            private Border inside = new EmptyBorder(0, 1, 0, 1);
            private Border border = new CompoundBorder(outside, inside);

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                JComponent jc = (JComponent) c;
                // Add a border to the selected row
                if (isRowSelected(row)) jc.setBorder(border);
                return c;
            }
        };
        menuBar = new javax.swing.JMenuBar();
        addMenu = new javax.swing.JMenu();
        addNewFriendItem = new javax.swing.JMenuItem();
        questionMenu = new javax.swing.JMenu();
        logoutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        friendTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        friendTable.setShowHorizontalLines(false);
        friendTable.setShowVerticalLines(false);
        friendTable.getTableHeader().setResizingAllowed(false);
        friendTable.getTableHeader().setReorderingAllowed(false);
        friendTable.setUpdateSelectionOnSort(false);
        friendTable.setVerifyInputWhenFocusTarget(false);
        scrollPane.setViewportView(friendTable);

        addMenu.setText("Aggiungi");

        addNewFriendItem.setText("Aggiungi nuovo amico");
        addMenu.add(addNewFriendItem);

        menuBar.add(addMenu);

        questionMenu.setText("?");

        logoutItem.setText("Logout");
        questionMenu.add(logoutItem);

        menuBar.add(questionMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu addMenu;
    private javax.swing.JMenuItem addNewFriendItem;
    private javax.swing.JTable friendTable;
    private javax.swing.JMenuItem logoutItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu questionMenu;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
    
    private List<String> friendsList;
    private List<MessageNotification> msgNotificationList;
    private String clientUsername;
    private DefaultTableModel dtm;
    private HttpRequest httpRequest = new HttpRequest();
    private FriendForm summoner = this;
    private Thread checkNewIncomingFriend;
    private Thread updateFriendTableSometimes;
    private List<ChatForm> openedChatInstances = new ArrayList<>();
    private List<String> friendUsernameChatOpened = new ArrayList<>();
    
    public FriendForm(String username) {
        this.clientUsername = username;
        initComponents();
        init();
    }
    
    private void init() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.updateStatus(clientUsername, StringReferences.ONLINESTATUS);

        addActionListenerToJMenuItem(logoutItem, addNewFriendItem);
        friendsList = populateFriendListSearchingByUsername(clientUsername);
        msgNotificationList = populateNotificationListByClientUsername(clientUsername);
        Object columnNames[] = getColumnNames();
        initJTable(getRowData(), columnNames, clientUsername);

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

    /*********************************** ROBA PER IL MENU *********************************/
    private void addActionListenerToJMenuItem(JMenuItem... toAdd) {
        for(JMenuItem i : toAdd) {
            i.addActionListener(this);
        }
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
    /***************************************************************************************/

    /********************************** ROBA PER LA TABELLA  **********************************/
    private Object[][] getRowData() {
        Object rowData[][] = new Object[friendsList.size()][2];
        String friend;
        for(int i=0; i<friendsList.size(); i++) {
            friend = friendsList.get(i);
            rowData[i][0] = " "+friend;
            for(MessageNotification mn : msgNotificationList) {
                if(friend.equalsIgnoreCase(mn.getFriendUsername().trim())) {
                    rowData[i][1] = " " + mn.getnMsg().trim();
                    break;
                } else {
                    rowData[i][1] = " ";
                }
            }
        }
        return rowData;
    }

    private Object[] getColumnNames() { return new Object[]{"Amici"," "}; }

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
        dtm = new DefaultTableModel(rowData, columnNames) {
            public boolean isCellEditable(int row, int column)     {
                return false;//This causes all cells to be not editable
            }
        };
        friendTable.setModel(dtm);
        friendTable.getTableHeader().setVisible(false);
        friendTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    int row = friendTable.rowAtPoint(e.getPoint());
                    if(!checkIfChatIsAlreadyOpened(row)) {
                        ChatForm cf = new ChatForm(username, friendsList.get(row), summoner);
                        friendUsernameChatOpened.add(friendsList.get(row));
                        openedChatInstances.add(cf);
                        SwingUtilities.invokeLater(()-> updateFriendJTable());
                    } else {
                        System.out.println("Chat già aperta!");
                    }
                }
            }
        });
        friendTable.setComponentPopupMenu(initJPopupMenu(friendTable, summoner));
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
            @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }
            @Override public void popupMenuCanceled(PopupMenuEvent e) { }
        });
        return popupMenu;
    }

    private List<String> populateFriendListSearchingByUsername(String username) {
        List<String> res = httpRequest.getAcceptedFriendsList(username);
        res.sort(String::compareToIgnoreCase);
        return res;
    }

    private List<MessageNotification> populateNotificationListByClientUsername(String clientUsername) {
        List<MessageNotification> res = httpRequest.checkForIncomingMessage(clientUsername);
        Collections.sort(res);
        return res;
    }

    public void updateFriendJTable() {
        System.out.println("I'm updating friend table()...");
        friendsList = populateFriendListSearchingByUsername(clientUsername);
        msgNotificationList = populateNotificationListByClientUsername(clientUsername);
        dtm.setDataVector(getRowData(), getColumnNames());
        dtm.fireTableDataChanged();
        this.repaint();
    }

    /****************************************************************************************************/

    private void shutDownFriendFrame() {
        System.out.println("Shutting down " + clientUsername + " instances()...");
        ThreadableUtils.killThread(updateFriendTableSometimes, checkNewIncomingFriend);
        for(ChatForm cf : openedChatInstances) {
            SwingUtilities.invokeLater(()->cf.closeForm());
        }
        friendUsernameChatOpened.clear();
        openedChatInstances.clear();
        httpRequest.updateStatus(clientUsername, StringReferences.OFFLINESTATUS);
        setVisible(false);
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