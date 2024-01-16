package com.evergreen;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public final GameShell shell;

    public GameFrame(GameShell shell) {
        this.shell = shell;
        setTitle("Jagex");
        setResizable(false);
        setLayout(new BorderLayout());
        add(shell, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        transferFocus();
    }


}
