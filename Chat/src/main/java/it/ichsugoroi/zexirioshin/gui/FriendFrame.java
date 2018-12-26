package it.ichsugoroi.zexirioshin.gui;

import it.ichsugoroi.zexirioshin.main.UserInfo;
import it.ichsugoroi.zexirioshin.web.HttpRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FriendFrame extends JFrame implements ActionListener {
    private List<String> friendsList;


    public FriendFrame(String username) {
        init(username);
    }

    private void init(String username) {
        friendsList = populateFriendListSearchingByUsername(username);
        Object columnNames[] = { "Amici"};
        initJTable(getRowData(), columnNames, username);

        setTitle("Lista amici di " + username);
        initBarPanel();
        setLayout(new GridLayout(1,1));
        setSize(300,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
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

        friendTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = friendTable.rowAtPoint(e.getPoint());
                new ChatFrame(username, friendsList.get(row).trim());
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
