package f1app.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import f1app.core.Circuits;
import f1app.core.Tyres;

/**
 * An abstract class that is the parent for Dialog boxes which appear when the user presses the buttons to add or remove a lap time. Sets up the common components.
 * @author Charlie
 *
 */
public abstract class LapTimeDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	protected LapTimeChangeListener lapTimeChangeListener;
	protected GridBagConstraints gcDialog;
	protected Insets comboInsets;
	protected Insets defaultInsets;
	private JLabel messageLabel;
	private JLabel circuitLabel;
	protected JComboBox<Circuits> circuitsCombo;
	private JLabel tyresLabel;
	protected JComboBox<Tyres> tyresCombo;

	public LapTimeDialog(Frame aFrame, String title) {
		super(aFrame, title, true);
		setSize(new Dimension((aFrame.getWidth() / 3), (aFrame.getHeight() / 3)));
		setLocationRelativeTo(aFrame);
		setup();
	}

	private void setup() {
		setLayout(new GridBagLayout());
		gcDialog = new GridBagConstraints();
		comboInsets = new Insets(0, 0, 0, 10);
		defaultInsets = new Insets(0, 0, 0, 0);

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
	}

	//TODO NEXT: Check public methods for javadoc
	/**
	 * @param newLapTimeListener the newLapTimeListener to set
	 */
	public void setNewLapTimeListener(LapTimeChangeListener newLapTimeListener) {
		this.lapTimeChangeListener = newLapTimeListener;
	}
}
