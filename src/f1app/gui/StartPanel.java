package f1app.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JLabel background;
	private ScreenChangeListener screenChangeListener;

	public StartPanel() {
		setup();
	}

	private void setup() {
		setLayout(new BorderLayout());

		// Create the background JLabel
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/Resources/Images/mclaren.jpg"));
		background = new JLabel(icon);
		background.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		background.add(Box.createGlue(), gc);

		gc.gridx = 2;
		gc.anchor = GridBagConstraints.PAGE_END;
		gc.insets = new Insets(0, 0, 15, 0);
		startButton = new JButton("START");
		startButton.setPreferredSize(new Dimension(MainFrame.WIDTH / 3, MainFrame.HEIGHT / 12));
		background.add(startButton, gc);

		gc.gridx = 3;
		background.add(Box.createGlue(), gc);

		add(background);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (screenChangeListener != null) {
					screenChangeListener.screenChanged(ScreenChangedEvent.START_SCREEN);
				}
			}
		});
	}

	/**
	 * Sets the StartScreen object`s ScreenChangeListener
	 * @param screenChangeListener 
	 */
	public void setScreenChangeListener(ScreenChangeListener screenChangeListener) {
		this.screenChangeListener = screenChangeListener;
	}
}
