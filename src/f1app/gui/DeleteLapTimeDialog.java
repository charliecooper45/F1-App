package f1app.gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import f1app.core.Circuits;
import f1app.core.Tyres;

/**
 * Dialog that appears when the user presses the button to delete a lap time.
 * @author Charlie
 *
 */
public class DeleteLapTimeDialog extends LapTimeDialog {

	private static final long serialVersionUID = 1L;
	private JButton deleteButton;

	public DeleteLapTimeDialog(Frame aFrame, String selectedCircuit) {
		super(aFrame, "Delete a lap time", selectedCircuit);
		addComponents();
	}
	
	private void addComponents() {
		gcDialog.gridx = 1;
		gcDialog.gridy = 4;
		gcDialog.gridwidth = 3;
		gcDialog.weighty = 2;
		gcDialog.anchor = GridBagConstraints.CENTER;
		gcDialog.fill = GridBagConstraints.NONE;
		deleteButton = new JButton("Delete Time");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Tyres tyre = (Tyres) tyresCombo.getSelectedItem();
				Circuits circuit = (Circuits) circuitsCombo.getSelectedItem();
				
				int answer = JOptionPane.showConfirmDialog(DeleteLapTimeDialog.this, "Delete " + circuit + " " + tyre + " time?", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if(answer == JOptionPane.YES_OPTION) {
					lapTimeChangeListener.deleteLapTime(circuit, tyre);
					DeleteLapTimeDialog.this.dispose();
				}
			}
		});
		add(deleteButton, gcDialog);
	}
}
