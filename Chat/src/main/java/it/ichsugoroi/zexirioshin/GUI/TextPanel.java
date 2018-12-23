package it.ichsugoroi.zexirioshin.GUI;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private JTextField tf;
    private MainFrame principalInterface;

    public TextPanel() { init(); }

    private void init() {
        tf = new JTextField(50);
        add(tf);
        setLayout(new GridLayout(1,1));
    }

    public void setTextToTextField(String text) { tf.setText(text); }
    public String getTextFromTextField() { return tf.getText(); }
}
