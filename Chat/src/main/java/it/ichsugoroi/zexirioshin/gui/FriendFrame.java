package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.utils.Constant;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FriendFrame extends JFrame implements ActionListener {
    private List<String> friendsList;
    private String clientUsername;
    private DefaultTableModel dtm;
    private HttpRequest httpRequest = new HttpRequest();




    public FriendFrame(String username) {
        this.clientUsername = username;
        init();
    }

    private void init() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.updateStatus(clientUsername, Constant.ONLINESTATUS);

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
                httpRequest.updateStatus(clientUsername, Constant.OFFLINESTATUS);
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private List<Integer> rowOpened = new ArrayList<>();

    public void removeRowFromOpenedRowList(int position) {
        rowOpened.remove(position);
    }

    private boolean checkIfChatIsAlreadyOpened(int row) {
        for(Integer r : rowOpened) {
            if(r == row) {
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
        FriendFrame summoner = this;



        friendTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    int row = friendTable.rowAtPoint(e.getPoint());
                    if(!checkIfChatIsAlreadyOpened(row)) {
                        new ChatForm(username, friendsList.get(row).trim(), row, summoner);
                        rowOpened.add(row);
                    }
                }
            }
        });

        JPopupMenu popupMenu = initJPopupMenu(friendTable, summoner);
        friendTable.setComponentPopupMenu(popupMenu);


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
                httpRequest.removeFriend(clientUsername, friendsList.get(selectedRow).trim());
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
        return httpRequest.getFriendsList(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("Logout")) {
            UserInfo.deleteUserNameFolderIfExists();
            setVisible(false);
            new LoginForm();
        }

        if(e.getActionCommand().equalsIgnoreCase("Aggiungi nuovo amico")) {
            new AddNewFriendPane(this, clientUsername, friendsList);
        }
    }

    public void addNewFriendToFriendsList(String newFriendUsername) {
        friendsList.add(newFriendUsername);
        dtm.setDataVector(getRowData(), getColumnNames());
        dtm.fireTableDataChanged();
        this.repaint();
    }
}
