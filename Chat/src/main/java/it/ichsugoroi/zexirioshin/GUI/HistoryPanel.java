package it.ichsugoroi.zexirioshin.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryPanel extends JPanel {

    private JTextArea ta;
    private MainFrame principalInterface;
    private List<String> history = new ArrayList<>();

    public HistoryPanel(MainFrame principalInterface) {
        this.principalInterface = principalInterface;
        init(); }

    private void init() {
        ta = new JTextArea(10,50);
        ta.setEditable(false);
        add(ta);
        setLayout(new GridLayout(1,1));
    }

    public void addNewRowToHistory(String newRow) {
        history.add(newRow);
        repaintHistory();
    }

    private void repaintHistory() {
        ta.setText("");
        int count = 0;
        for(String s : history) {
            ta.append(s);
            ta.append("\n");
            count++;
        }
        if(count==10) {
            history.clear();
        }
        repaint();
    }
}
