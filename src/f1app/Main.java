package f1app;

import javax.swing.SwingUtilities;

import f1app.gui.MainFrame;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {		
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
	}
}
