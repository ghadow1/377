// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@SuppressWarnings("serial")
public class JagApplet extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener,
		FocusListener, WindowListener {

	public void start(int _width, int _height) {
		width = _width;
		screenHeight = _height;
		frame = new GameFrame(this);
		graphics = getParentComponent().getGraphics();
		imageProducer = new JagImageProducer(width, screenHeight, getParentComponent());
		startThread(this, 1);
	}

	public void startNoFrame(int i, int j) {
		width = i;
			screenHeight = j;
			graphics = getParentComponent().getGraphics();
			imageProducer = new JagImageProducer(width, screenHeight, getParentComponent());
			startThread(this, 1);
	}

	public void run() {
		getParentComponent().addMouseListener(this);
		getParentComponent().addMouseMotionListener(this);
		getParentComponent().addKeyListener(this);
		getParentComponent().addFocusListener(this);
		if (frame != null)
			frame.addWindowListener(this);
		drawLoadingText(0, "Loading...");
		load();
		int i = 0;
		int j = 256;
		int k = 1;
		int i1 = 0;
		int j1 = 0;
		for (int k1 = 0; k1 < 10; k1++)
			otim[k1] = System.currentTimeMillis();

		while (state >= 0) {
			if (state > 0) {
				state--;
				if (state == 0) {
					shutdown(aBoolean2);
					return;
				}
			}
			int i2 = j;
			int j2 = k;
			j = 300;
			k = 1;
			long l1 = System.currentTimeMillis();
			if (otim[i] == 0L) {
				j = i2;
				k = j2;
			} else if (l1 > otim[i])
				j = (int) ((2560 * deltime) / (l1 - otim[i]));
			if (j < 25)
				j = 25;
			if (j > 256) {
				j = 256;
				k = (int) (deltime - (l1 - otim[i]) / 10L);
			}
			if (k > deltime)
				k = deltime;
			otim[i] = l1;
			i = (i + 1) % 10;
			if (k > 1) {
				for (int k2 = 0; k2 < 10; k2++)
					if (otim[k2] != 0L)
						otim[k2] += k;

			}
			if (k < mindel)
				k = mindel;
			try {
				Thread.sleep(k);
			} catch (InterruptedException _ex) {
				j1++;
			}
			for (; i1 < 256; i1 += j) {
				mouseClickButton = lastMouseClickButton;
				mouseClickX = lastMouseClickX;
				mouseClickY = lastMouseClickY;
				mouseClickTime = lastMouseClickTime;
				lastMouseClickButton = 0;
				draw();
				keyQueueReadPos = keyQueueWritePos;
			}

			i1 &= 0xff;
			if (deltime > 0)
				fps = (1000 * j) / (deltime * 256);
			if (debug) {
				System.out.println("ntime:" + l1);
				for (int l2 = 0; l2 < 10; l2++) {
					int i3 = ((i - l2 - 1) + 20) % 10;
					System.out.println("otim" + i3 + ":" + otim[i3]);
				}

				System.out.println("fps:" + fps + " ratio:" + j + " count:" + i1);
				System.out.println("del:" + k + " deltime:" + deltime + " mindel:" + mindel);
				System.out.println("intex:" + j1 + " opos:" + i);
				debug = false;
				j1 = 0;
			}
		}
		if (state == -1)
			shutdown(aBoolean2);
	}

	public void shutdown(boolean flag) {
		state = -2;
		if (flag)
			return;
		if (frame != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
			try {
				System.exit(0);
				return;
			} catch (Throwable _ex) {
			}
		}
	}

	public void method4(int i) {
		deltime = 1000 / i;
	}

	@Override
	public void start() {
		if (state >= 0)
			state = 0;
	}

	@Override
	public void stop() {
		if (state >= 0)
			state = 4000 / deltime;
	}

	@Override
	public void destroy() {
		state = -1;
		try {
			Thread.sleep(10000L);
		} catch (Exception _ex) {
		}
		if (state == -1)
			shutdown(aBoolean2);
	}

	@Override
	public void update(Graphics g) {
		if (graphics == null)
			graphics = g;
		refresh = true;
		refresh((byte) -99);
	}

	@Override
	public void paint(Graphics g) {
		if (graphics == null)
			graphics = g;
		refresh = true;
		refresh((byte) -99);
	}

	public void mousePressed(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (frame != null) {
			i -= 4;
			j -= 22;
		}
		idleCycles = 0;
		lastMouseClickX = i;
		lastMouseClickY = j;
		lastMouseClickTime = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			lastMouseClickButton = 2;
			mouseButton = 2;
			return;
		} else {
			lastMouseClickButton = 1;
			mouseButton = 1;
			return;
		}
	}

	public void mouseReleased(MouseEvent mouseevent) {
		idleCycles = 0;
		mouseButton = 0;
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
		idleCycles = 0;
		mouseX = -1;
		mouseY = -1;
	}

	public void mouseDragged(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (frame != null) {
			i -= 4;
			j -= 22;
		}
		idleCycles = 0;
		mouseX = i;
		mouseY = j;
	}

	public void mouseMoved(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (frame != null) {
			i -= 4;
			j -= 22;
		}
		idleCycles = 0;
		mouseX = i;
		mouseY = j;
	}

	public void keyPressed(KeyEvent keyevent) {
		idleCycles = 0;
		int i = keyevent.getKeyCode();
		int j = keyevent.getKeyChar();
		if (j < 30)
			j = 0;
		if (i == 37)
			j = 1;
		if (i == 39)
			j = 2;
		if (i == 38)
			j = 3;
		if (i == 40)
			j = 4;
		if (i == 17)
			j = 5;
		if (i == 8)
			j = 8;
		if (i == 127)
			j = 8;
		if (i == 9)
			j = 9;
		if (i == 10)
			j = 10;
		if (i >= 112 && i <= 123)
			j = (1008 + i) - 112;
		if (i == 36)
			j = 1000;
		if (i == 35)
			j = 1001;
		if (i == 33)
			j = 1002;
		if (i == 34)
			j = 1003;
		if (j > 0 && j < 128)
			actionKey[j] = 1;
		if (j > 4) {
			keyQueue[keyQueueWritePos] = j;
			keyQueueWritePos = keyQueueWritePos + 1 & 0x7f;
		}
	}

	public void keyReleased(KeyEvent keyevent) {
		idleCycles = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (c < '\036')
			c = '\0';
		if (i == 37)
			c = '\001';
		if (i == 39)
			c = '\002';
		if (i == 38)
			c = '\003';
		if (i == 40)
			c = '\004';
		if (i == 17)
			c = '\005';
		if (i == 8)
			c = '\b';
		if (i == 127)
			c = '\b';
		if (i == 9)
			c = '\t';
		if (i == 10)
			c = '\n';
		if (c > 0 && c < '\200')
			actionKey[c] = 0;
	}

	public void keyTyped(KeyEvent keyevent) {
	}

	public int pollKey() {
		int j = -1;
		if (keyQueueWritePos != keyQueueReadPos) {
			j = keyQueue[keyQueueReadPos];
			keyQueueReadPos = keyQueueReadPos + 1 & 0x7f;
		}
		return j;
	}

	public void focusGained(FocusEvent focusevent) {
		focused = true;
		refresh = true;
		refresh((byte) -99);
	}

	public void focusLost(FocusEvent focusevent) {
		focused = false;
		for (int i = 0; i < 128; i++)
			actionKey[i] = 0;

	}

	public void windowActivated(WindowEvent windowevent) {
	}

	public void windowClosed(WindowEvent windowevent) {
	}

	public void windowClosing(WindowEvent windowevent) {
		destroy();
	}

	public void windowDeactivated(WindowEvent windowevent) {
	}

	public void windowDeiconified(WindowEvent windowevent) {
	}

	public void windowIconified(WindowEvent windowevent) {
	}

	public void windowOpened(WindowEvent windowevent) {
	}

	public void load() {
	}

	public void draw() {
	}

	public void method9(int i) {
		if (i > 0)
			;
	}

	public void refresh(byte byte0) {
		if (byte0 == -99)
			;
	}

	public Component getParentComponent() {
		if (frame != null)
			return frame;
		else
			return this;
	}

	public void startThread(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(priority);
	}

	public void drawLoadingText(int percent, String desc) {
		while (graphics == null) {
			graphics = getParentComponent().getGraphics();
			try {
				getParentComponent().repaint();
			} catch (Exception _ex) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		}
		Font font = new Font("Helvetica", 1, 13);
		FontMetrics metrics = getParentComponent().getFontMetrics(font);
		Font font1 = new Font("Helvetica", 0, 13);
		getParentComponent().getFontMetrics(font1);
		if (refresh) {
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, width, screenHeight);
			refresh = false;
		}
		Color color = new Color(140, 17, 17);
		int j = screenHeight / 2 - 18;
		graphics.setColor(color);
		graphics.drawRect(width / 2 - 152, j, 304, 34);
		graphics.fillRect(width / 2 - 150, j + 2, percent * 3, 30);
		graphics.setColor(Color.black);
		graphics.fillRect((width / 2 - 150) + percent * 3, j + 2, 300 - percent * 3, 30);
		graphics.setFont(font);
		graphics.setColor(Color.white);
		graphics.drawString(desc, (width - metrics.stringWidth(desc)) / 2, j + 22);
	}

	public JagApplet() {
		aBoolean2 = false;
		aBoolean3 = false;
		anInt4 = 3;
		deltime = 20;
		mindel = 1;
		otim = new long[10];
		debug = false;
		aClass50_Sub1_Sub1_Sub1Array16 = new RgbSprite[6];
		refresh = true;
		focused = true;
		actionKey = new int[128];
		keyQueue = new int[128];
	}

	public boolean aBoolean2;
	public boolean aBoolean3;
	public int anInt4;
	public int anInt5;
	public int state;
	public int deltime;
	public int mindel;
	public long otim[];
	public int fps;
	public boolean debug;
	public int width;
	public int screenHeight;
	public Graphics graphics;
	public JagImageProducer imageProducer;
	public RgbSprite aClass50_Sub1_Sub1_Sub1Array16[];
	public GameFrame frame;
	public boolean refresh;
	public boolean focused;
	public int idleCycles;
	public int mouseButton;
	public int mouseX;
	public int mouseY;
	public int lastMouseClickButton;
	public int lastMouseClickX;
	public int lastMouseClickY;
	public long lastMouseClickTime;
	public int mouseClickButton;
	public int mouseClickX;
	public int mouseClickY;
	public long mouseClickTime;
	public int actionKey[];
	public int keyQueue[];
	public int keyQueueReadPos;
	public int keyQueueWritePos;
	public static int anInt36;
}
