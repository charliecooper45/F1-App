package f1app.gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import f1app.core.Circuits;
import f1app.core.LapTime;
import f1app.core.Tyres;

/**
 * Dialog that appears when the user presses the button to add a new lap time.
 * @author Charlie
 *
 */
public class AddLapTimeDialog extends LapTimeDialog {

	private static final long serialVersionUID = 1L;
	private JLabel timeLabel;
	private JTextField lapTimeField;
	private JButton addButton;

	public AddLapTimeDialog(Frame aFrame) {
		super(aFrame, "Add a lap time");
		addComponents();
	}

	private void addComponents() {
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
		addButton = new JButton("Confirm");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean validTime = false;
				String minutes = null;
				String seconds = null;
				String milliseconds = null;
				Circuits circuit = null;
				Tyres tyre = null;

				// Check this is a valid lap time
				String lapTime = lapTimeField.getText().trim();
				
				if (lapTime.isEmpty()) {
					JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap time cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (lapTime.length() < 6) {
					JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap time too short", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (lapTime.length() > 9) {
					JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap time too long", "Error", JOptionPane.ERROR_MESSAGE);
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
							JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap time seconds are invalid", "Error", JOptionPane.ERROR_MESSAGE);
							validTime = false;
						}
						Integer.parseInt(milliseconds);
					} catch (NumberFormatException ex1) {
						JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap time contains letters", "Error", JOptionPane.ERROR_MESSAGE);
						validTime = false;
					}
				}

				if (validTime) {
					tyre = (Tyres) tyresCombo.getSelectedItem();
					circuit = (Circuits) circuitsCombo.getSelectedItem();
					int answer = JOptionPane.showConfirmDialog(AddLapTimeDialog.this, circuit + " " + tyre + " " + lapTime, "Confirm", JOptionPane.YES_NO_OPTION);
					
					if(answer == JOptionPane.YES_OPTION) {
						boolean timeAdded = lapTimeChangeListener.addLapTime(new LapTime(tyre, minutes, seconds, milliseconds), circuit);
						
						if(timeAdded)
							AddLapTimeDialog.this.dispose();
						else
							JOptionPane.showMessageDialog(AddLapTimeDialog.this, "Lap time is not faster than the previous time", "Error", JOptionPane.ERROR_MESSAGE);
					} 
				}
			}
		});
		gcDialog.insets = defaultInsets;
		add(addButton, gcDialog);
	}
}
