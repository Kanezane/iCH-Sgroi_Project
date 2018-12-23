package it.ichsugoroi.zexirioshin.GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel textarea;
    private JPanel textBoxArea;
    private JPanel buttonarea;

    public MainFrame() { init(); }

    private void init() {
        setTitle("Chat");

        textarea = new HistoryPanel(this);
        add(textarea);

        textBoxArea = new TextPanel();
        add(textBoxArea);

        buttonarea = new ButtonPanel(this);
        add(buttonarea);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new GridLayout(3,1));
        pack();
        setVisible(true);
    }



}
