package it.ichsugoroi.zexirioshin.GUI;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {

    private JButton button;
    private MainFrame principalInterface;

    public ButtonPanel(MainFrame principalInterface) {
        this.principalInterface = principalInterface;
        init();
    }

    private void init() {
        button = new JButton("Send");
        button.addActionListener(this);
        add(button);
        setLayout(new GridLayout(1,1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
