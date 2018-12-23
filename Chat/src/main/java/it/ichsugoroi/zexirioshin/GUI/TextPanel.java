package it.ichsugoroi.zexirioshin.GUI;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private JTextField tf;

    public TextPanel() { init(); }

    private void init() {
        tf = new JTextField(50);
        add(tf);
        setLayout(new GridLayout(1,1));
    }

    public String getTextFromTextField() { return tf.getText(); }
    public void removeTextFromTextField() { tf.setText(""); }
}
