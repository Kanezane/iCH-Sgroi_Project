package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.utils.Constant;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FriendFrame extends JFrame implements ActionListener {
    private List<String> friendsList;


    public FriendFrame(String username) {
        init(username);
    }

    private void init(String username) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.updateStatus(username, Constant.ONLINESTATUS);

        friendsList = populateFriendListSearchingByUsername(username);
        friendsList.sort(String::compareToIgnoreCase);
        Object columnNames[] = {"Amici"};
        initJTable(getRowData(), columnNames, username);

        initBarPanel();

        setTitle("Lista amici di " + username);
        setLayout(new GridLayout(1,1));
        setSize(300,400);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                httpRequest.updateStatus(username, Constant.OFFLINESTATUS);
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
        DefaultTableModel dtm = new DefaultTableModel(rowData, columnNames);
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
                        new ChatFrame(username, friendsList.get(row).trim(), row, summoner);
                        rowOpened.add(row);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(friendTable);
        add(scrollPane);
    }

    private void initBarPanel() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu altroMenu = new JMenu("Altro");
        menuBar.add(altroMenu);

        JMenuItem logoutItem = new JMenuItem("Logout");
        altroMenu.add(logoutItem);
        logoutItem.addActionListener(this);
    }

    private Object[][] getRowData() {

        Object rowData[][] = new Object[friendsList.size()][1];
        for(int i=0; i<friendsList.size(); i++) {
            rowData[i][0] = " "+friendsList.get(i);
        }

        return rowData;
    }

    private List<String> populateFriendListSearchingByUsername(String username) {
        HttpRequest httpRequest = new HttpRequest();
        return httpRequest.getFriendsList(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("Logout")) {
            UserInfo.deleteUserNameFolderIfExists();
            setVisible(false);
            new LoginForm();
        }
    }
}
