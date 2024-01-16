// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	public JagApplet shell;

	public GameFrame(JagApplet shell) {
		this.shell = shell;
		setTitle("Jagex");
		setResizable(false);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(765, 503));
		add(this.shell, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		transferFocus();
    }
}
