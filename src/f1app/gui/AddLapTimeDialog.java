package f1app.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import f1app.core.Circuit;
import f1app.core.Circuits;
import f1app.core.LapTime;
import f1app.core.Tyres;

/**
 * Dialog that appears when the user presses the button to add a new lap time.
 * @author Charlie
 *
 */
public class AddLapTimeDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private NewLapTimeListener newLapTimeListener;
	private JLabel messageLabel;
	private JLabel circuitLabel;
	private JComboBox<Circuits> circuitsCombo;
	private JLabel tyresLabel;
	private JComboBox<Tyres> tyresCombo;
	private JLabel timeLabel;
	private JTextField lapTimeField;
	private JButton confirmButton;

	public AddLapTimeDialog(Frame aFrame) {
		super(aFrame, "Add a lap time", true);
		setSize(new Dimension((aFrame.getWidth() / 3), (aFrame.getHeight() / 3)));
		setLocationRelativeTo(aFrame);
		setup();
	}

	private void setup() {
		setLayout(new GridBagLayout());
		GridBagConstraints gcDialog = new GridBagConstraints();
		Insets comboInsets = new Insets(0, 0, 0, 10);
		Insets defaultInsets = new Insets(0, 0, 0, 0);

		// Add components
		gcDialog.weightx = 1;
		gcDialog.weighty = 1;
		gcDialog.gridx = 1;
		gcDialog.gridy = 1;
		gcDialog.gridwidth = 3;
		gcDialog.anchor = GridBagConstraints.CENTER;
		messageLabel = new JLabel("Please enter the lap details below:");
		add(messageLabel, gcDialog);

		gcDialog.gridy = 2;
		gcDialog.gridwidth = 1;
		circuitLabel = new JLabel("Circuit:");
		add(circuitLabel, gcDialog);

		gcDialog.gridx = 2;
		gcDialog.gridwidth = 2;
		gcDialog.anchor = GridBagConstraints.LINE_START;
		gcDialog.fill = GridBagConstraints.HORIZONTAL;
		gcDialog.insets = comboInsets;
		circuitsCombo = new JComboBox<>(Circuits.values());
		circuitsCombo.setSelectedIndex(0);
		add(circuitsCombo, gcDialog);

		gcDialog.gridx = 1;
		gcDialog.gridy = 3;
		gcDialog.gridwidth = 1;
		gcDialog.anchor = GridBagConstraints.CENTER;
		gcDialog.fill = GridBagConstraints.NONE;
		gcDialog.insets = defaultInsets;
		tyresLabel = new JLabel("Tyres:");
		add(tyresLabel, gcDialog);

		gcDialog.gridx = 2;
		gcDialog.gridwidth = 2;
		gcDialog.anchor = GridBagConstraints.LINE_START;
		gcDialog.fill = GridBagConstraints.HORIZONTAL;
		gcDialog.insets = comboInsets;
		tyresCombo = new JComboBox<>(Tyres.values());
		tyresCombo.setSelectedIndex(0);
		add(tyresCombo, gcDialog);

		gcDialog.gridx = 1;
		gcDialog.gridy = 4;
		gcDialog.gridwidth = 1;
		gcDialog.anchor = GridBagConstraints.CENTER;
		gcDialog.fill = GridBagConstraints.NONE;
		gcDialog.insets = defaultInsets;
		timeLabel = new JLabel("Time:");
		add(timeLabel, gcDialog);

		gcDialog.gridx = 2;
		gcDialog.gridwidth = 2;
		gcDialog.anchor = GridBagConstraints.LINE_START;
		gcDialog.fill = GridBagConstraints.HORIZONTAL;
		gcDialog.insets = comboInsets;
		lapTimeField = new JTextField();
		lapTimeField.addKeyListener(new KeyAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				String text = lapTimeField.getText();

				if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
					if (text.length() == 1) {
						lapTimeField.setText(text + ":");
					} else if (text.length() == 4) {
						lapTimeField.setText(text + ".");
					}
				}
			}

		});
		add(lapTimeField, gcDialog);

		gcDialog.gridx = 1;
		gcDialog.gridy = 5;
		gcDialog.gridwidth = 3;
		gcDialog.weighty = 2;
		gcDialog.anchor = GridBagConstraints.CENTER;
		gcDialog.fill = GridBagConstraints.NONE;
		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean validTime = false;
				String minutes = null;
				String seconds = null;
				String milliseconds = null;
				Circuits circuit = null;
				Tyres tyre = null;

				// Check this is a valid lap time
				String lapTime = lapTimeField.getText();
				
				// TODO NEXT: This needs some more error checking .trim()
				if (lapTime.isEmpty()) {
					JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap Time cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (lapTime.length() < 6) {
					JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap Time too short", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (lapTime.length() > 9) {
					JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap Time too long", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					StringBuilder builder = new StringBuilder(lapTime);
					minutes = builder.substring(0, 1);
					seconds = builder.substring(2, 4);
					milliseconds = builder.substring(5, builder.length());
					validTime = true;

					// Check the lap time does not contain letters
					try {
						Integer.parseInt(minutes);
						if (Integer.parseInt(seconds) >= 60) { 
							JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap Time seconds are invalid", "Error", JOptionPane.ERROR_MESSAGE);
							validTime = false;
						}
						Integer.parseInt(milliseconds);
					} catch (NumberFormatException ex1) {
						JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap Time contains letters", "Error", JOptionPane.ERROR_MESSAGE);
						validTime = false;
					}
				}

				if (validTime) {
					tyre = (Tyres) tyresCombo.getSelectedItem();
					circuit = (Circuits) circuitsCombo.getSelectedItem();
					newLapTimeListener.addLapTime(new LapTime(tyre, minutes, seconds, milliseconds), circuit);
					AddLapTimeDialog.this.dispose();
				}
			}
		});
		gcDialog.insets = defaultInsets;
		add(confirmButton, gcDialog);

		//TODO NEXT B: Configure the lap time field so that is add colons/checks values
	}

	/**
	 * @param newLapTimeListener the newLapTimeListener to set
	 */
	public void setNewLapTimeListener(NewLapTimeListener newLapTimeListener) {
		this.newLapTimeListener = newLapTimeListener;
	}

	//TODO NEXT: There needs to be some extra confirmation step here so the user doesn`t accidentely add a lap time
}
