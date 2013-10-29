package f1app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import f1app.core.Circuit;
import f1app.core.Circuits;
import f1app.core.F1Coord;
import f1app.core.LapTime;
import f1app.core.Tyres;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private F1Coord f1Coord;
	private List<CircuitPanel> circuits;
	private CircuitPanel selectedCircuitPanel;
	private JPanel upperPanel;
	// Listeners for different events
	private CircuitPanelListener circuitListener;
	private AddLapListener addLapListener;
	private JScrollPane scrollPane;
	private JPanel lowerPanel;
	private JPanel informationPanel;
	private JPanel circuitPanel;
	private JLabel circuitMap;
	// Components for the informationPanel, these need to be set by information from the core system
	private JLabel trackLabel;
	private JLabel trackLength;
	private JLabel lapRecord;
	private JLabel seasonRecord;
	private JLabel optionTyreTime;
	private JLabel primeTyreTime;
	private JLabel intermediateTyreTime;
	private JLabel wetTyreTime;
	private JButton addTimeButton;

	public MainPanel() {
		setup();
	}

	private void setup() {
		f1Coord = F1Coord.getInstance();
		circuits = new ArrayList<>();
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// upperPanel
		upperPanel = new JPanel();
		setupUpperPanel();
		scrollPane = new JScrollPane(upperPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		add(scrollPane, gc);

		// lowerPanel
		lowerPanel = new JPanel();
		setupLowerPanel();
		gc.gridy = 2;
		add(lowerPanel, gc);
		
		// load Australia CircuitPanel
		fireUpdateCircuitTime(f1Coord.getCircuit(Circuits.AUSTRALIA));
	}

	private void setupUpperPanel() {
		circuitListener = new CircuitPanelListener();
		upperPanel.setLayout(new GridBagLayout());
		GridBagConstraints gcUpper = new GridBagConstraints();
		int gridXNumber = 1;
		gcUpper.weightx = 1;
		gcUpper.weighty = 1;
		gcUpper.gridx = gridXNumber;
		gcUpper.gridy = 1;
		gcUpper.fill = GridBagConstraints.VERTICAL;
		gcUpper.anchor = GridBagConstraints.FIRST_LINE_START;

		for (Circuits circuit : Circuits.values()) {
			gridXNumber++;
			gcUpper.gridx = gridXNumber;
			CircuitPanel circuitPanel = new CircuitPanel(circuit.getCountry(), circuit.getCircuitName());
			circuits.add(circuitPanel);
			circuitPanel.addMouseListener(circuitListener);
			upperPanel.add(circuitPanel, gcUpper);
		}
	}

	private void setupLowerPanel() {
		lowerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gcLower = new GridBagConstraints();

		gcLower.weightx = 1;
		gcLower.weighty = 1;
		gcLower.gridx = 1;
		gcLower.gridy = 1;
		gcLower.fill = GridBagConstraints.BOTH;
		setupInformationPanel();
		lowerPanel.add(informationPanel, gcLower);

		setupCircuitPanel();
		gcLower.gridx = 2;
		lowerPanel.add(circuitPanel, gcLower);

		selectedCircuitPanel = circuits.get(0);
	}

	private void setupInformationPanel() {
		// TODO NEXT: The information panel is resizing automatically, need to stop this
		informationPanel = new JPanel();
		informationPanel.setPreferredSize(new Dimension(250, 100));
		informationPanel.setBackground(Color.GREEN);
		informationPanel.setLayout(new GridBagLayout());
		GridBagConstraints gcInformationPanel = new GridBagConstraints();

		// Add components to the information panel
		gcInformationPanel.weightx = 1;
		gcInformationPanel.weighty = 1;
		gcInformationPanel.gridx = 1;
		gcInformationPanel.gridy = 1;
		gcInformationPanel.gridwidth = 2;
		trackLabel = new JLabel("Track Name Label");
		informationPanel.add(trackLabel, gcInformationPanel);

		gcInformationPanel.gridy = 2;
		gcInformationPanel.gridwidth = 1;
		gcInformationPanel.anchor = GridBagConstraints.LINE_END;
		informationPanel.add(new JLabel("Length: "), gcInformationPanel);
		gcInformationPanel.gridy = 3;
		informationPanel.add(new JLabel("Record: "), gcInformationPanel);
		gcInformationPanel.gridy = 4;
		informationPanel.add(new JLabel("2013 Time: "), gcInformationPanel);
		gcInformationPanel.gridy = 2;
		gcInformationPanel.gridx = 2;
		gcInformationPanel.anchor = GridBagConstraints.LINE_START;
		trackLength = new JLabel("1.5 Miles");
		informationPanel.add(trackLength, gcInformationPanel);
		gcInformationPanel.gridy = 3;
		gcInformationPanel.gridx = 2;
		lapRecord = new JLabel("1:10:43 - Michael Schumacher");
		informationPanel.add(lapRecord, gcInformationPanel);
		gcInformationPanel.gridy = 4;
		gcInformationPanel.gridx = 2;
		seasonRecord = new JLabel("1:12:01 - Sebastien Vettel");
		informationPanel.add(seasonRecord, gcInformationPanel);

		gcInformationPanel.gridx = 1;
		gcInformationPanel.gridy = 5;
		gcInformationPanel.anchor = GridBagConstraints.LAST_LINE_END;
		// TODO NEXT B: Make this "Lap Times" text bold/underlined
		informationPanel.add(new JLabel("Lap Times: "), gcInformationPanel);
		gcInformationPanel.gridx = 1;
		gcInformationPanel.gridy = 6;
		gcInformationPanel.anchor = GridBagConstraints.LINE_END;
		informationPanel.add(new JLabel(Tyres.OPTION + ": "), gcInformationPanel);
		gcInformationPanel.gridy = 7;
		informationPanel.add(new JLabel(Tyres.PRIME + ": "), gcInformationPanel);
		gcInformationPanel.gridy = 8;
		informationPanel.add(new JLabel(Tyres.INTER + ": "), gcInformationPanel);
		gcInformationPanel.gridy = 9;
		informationPanel.add(new JLabel(Tyres.WET + ": "), gcInformationPanel);
		gcInformationPanel.gridx = 2;
		gcInformationPanel.gridy = 6;
		gcInformationPanel.anchor = GridBagConstraints.LINE_START;
		optionTyreTime = new JLabel();
		informationPanel.add(optionTyreTime, gcInformationPanel);
		gcInformationPanel.gridy = 7;
		primeTyreTime = new JLabel();
		informationPanel.add(primeTyreTime, gcInformationPanel);
		gcInformationPanel.gridy = 8;
		intermediateTyreTime = new JLabel();
		informationPanel.add(intermediateTyreTime, gcInformationPanel);
		gcInformationPanel.gridy = 9;
		wetTyreTime = new JLabel();
		informationPanel.add(wetTyreTime, gcInformationPanel);
		// TODO NEXT: Add functionality for adding a new time
		gcInformationPanel.gridx = 1;
		gcInformationPanel.gridy = 10;
		gcInformationPanel.gridwidth = 2;
		gcInformationPanel.anchor = GridBagConstraints.CENTER;
		addTimeButton = new JButton("Add New Time");
		addTimeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO NEXT B: Add an argument with the current circuit, this can be used to set the drop down menu
				addLapListener.buttonPressed();
			}
		});
		informationPanel.add(addTimeButton, gcInformationPanel);

		// Set the first track displayed (Australia)
		Circuits selectedCircuitType = Circuits.AUSTRALIA;
		Circuit circuit = f1Coord.getCircuit(selectedCircuitType);
		trackLabel.setText(selectedCircuitType.getCircuitName());
		trackLength.setText(circuit.getLength());
		lapRecord.setText(circuit.getLapRecord());
		seasonRecord.setText(circuit.getTime2013());
	}

	private void updateInformationPanel() {
		System.out.println("Update information panel");
		// Two switches, one to add a lap time and one to clear the displayed lap time if there is none stored
		System.out.println(selectedCircuitPanel.times);
		String text = null;
		for (Map.Entry<Tyres, LapTime> entry : selectedCircuitPanel.times.entrySet()) {
				if(entry.getValue() == null) 
					text = "";
				else 
					text = entry.getValue().toString();
				
				switch (entry.getKey()) {
				case OPTION:
					optionTyreTime.setText(text);
					break;
				case PRIME:
					primeTyreTime.setText(text);
					break;
				case INTER:
					intermediateTyreTime.setText(text);
					break;
				case WET:
					wetTyreTime.setText(text);
					break;
				}
		}
	}

	private void setupCircuitPanel() {
		circuitPanel = new JPanel();
		circuitPanel.setBackground(Color.ORANGE);
		circuitPanel.setLayout(new GridBagLayout());
		GridBagConstraints gcCircuitPanel = new GridBagConstraints();
		gcCircuitPanel.weightx = 1;
		gcCircuitPanel.weighty = 1;
		gcCircuitPanel.gridx = 1;
		gcCircuitPanel.gridy = 1;
		gcCircuitPanel.anchor = GridBagConstraints.CENTER;
		circuitMap = new JLabel(new ImageIcon(this.getClass().getResource("/Resources/Images/" + "australia" + "track.png")));
		circuitPanel.add(circuitMap, gcCircuitPanel);
	}

	private ImageIcon loadImage(String imageName) {
		return new ImageIcon(this.getClass().getResource("/Resources/Images/" + imageName + ".png"));
	}

	/**
	 * @param addLapListener the addLapListener to set
	 */
	public void setAddLapListener(AddLapListener addLapListener) {
		this.addLapListener = addLapListener;
	}

	public void fireUpdateCircuitTime(Circuit circuit) {
		for (CircuitPanel circuitPanel : circuits) {
			if (circuitPanel.countryName.equals(circuit.getCircuitType().getCountry())) {
				circuitPanel.updateTimes(circuit.getCircuitTimes());
			}
		}
	}

	/**
	 * JPanel that displays a circuit`s name, location and image
	 * @author Charlie
	 *
	 */
	private class CircuitPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private String countryName;
		private String circuitName;
		private JLabel circuitLabel;
		private JLabel countryLabel;
		private JLabel circuitPicture;
		private ImageIcon circuitImage;
		private Border border;
		private Map<Tyres, LapTime> times;

		public CircuitPanel(String countryName, String circuitName) {
			Circuits circuit = Circuits.valueOf(countryName.toUpperCase().replace(" ", "_"));
			times = f1Coord.getCircuit(circuit).getCircuitTimes();
			this.countryName = countryName;
			this.circuitName = circuitName;
			setup();
		}

		private void setup() {
			setPreferredSize(new Dimension((MainFrame.WIDTH / 4), 40));
			setLayout(new GridBagLayout());
			GridBagConstraints gcCircuit = new GridBagConstraints();

			countryLabel = new JLabel(countryName);
			gcCircuit.weightx = 1;
			gcCircuit.weighty = 1;
			gcCircuit.gridx = 1;
			gcCircuit.gridy = 1;
			add(countryLabel, gcCircuit);

			circuitLabel = new JLabel(circuitName);
			gcCircuit.gridy = 2;
			add(circuitLabel, gcCircuit);

			String imageName = countryName.replace(" ", "_");
			circuitImage = loadImage(imageName + "icon");
			circuitPicture = new JLabel(circuitImage);
			gcCircuit.gridy = 3;
			add(circuitPicture, gcCircuit);
			border = BorderFactory.createLineBorder(Color.black);
			setBorder(border);

			// If Australia then displayed as default
			if (countryName.equals("Australia")) {
				setFontColor(Color.RED);
				setBackground(Color.GRAY);
			}
		}

		/**
		 * Sets the font color for the circuit and country JLabels
		 * @param aColor the color to set the text to
		 */
		public void setFontColor(Color aColor) {
			circuitLabel.setForeground(aColor);
			countryLabel.setForeground(aColor);
		}

		/**
		 * Returns the color of the font of the circuit and country JLabels
		 * @return the font color of the circuitLabel
		 */
		public Color getFontColor() {
			return circuitLabel.getForeground();
		}

		// TODO NEXT B: give images a see through background for hightlighting
		// TODO NEXT B: Need to fit four circuits across the top of the MainPanel perfectly.

		public void updateTimes(Map<Tyres, LapTime> times) {
			this.times = times;

			// Check if the current circuit is displayed
			if (selectedCircuitPanel == this) {
				updateInformationPanel();
			}
		}
	}

	/***
	 * Listens for mouse clicks on the CircuitPanels and changes the user interface to reflect the selected circuit
	 * @author Charlie
	 *
	 */
	private class CircuitPanelListener extends MouseAdapter {

		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			CircuitPanel clicked = (CircuitPanel) e.getSource();

			// Set the previously clicked CircuitPanel back to the default colours
			for (CircuitPanel circuitPanel : circuits) {
				if (circuitPanel.getFontColor() == Color.RED) {
					circuitPanel.setFontColor(Color.BLACK);
					circuitPanel.setBackground(Color.WHITE);
				}
			}

			// Highlight the newly selected CircuitPanel
			clicked.setFontColor(Color.RED);
			clicked.setBackground(Color.GRAY);

			// Change the circuit picture
			circuitMap.setIcon(loadImage(clicked.countryName.toLowerCase().replace(" ", "_") + "track"));

			// Change the information displayed
			Circuits selectedCircuitType = Circuits.valueOf(clicked.countryName.toUpperCase().replace(" ", "_"));
			Circuit circuit = f1Coord.getCircuit(selectedCircuitType);
			trackLabel.setText(selectedCircuitType.getCircuitName());
			trackLength.setText(circuit.getLength());
			lapRecord.setText(circuit.getLapRecord());
			seasonRecord.setText(circuit.getTime2013());

			// Set the currently selected circuit panel
			selectedCircuitPanel = clicked;

			updateInformationPanel();
		}
	}

	//TODO NEXT B: Circuit name at the top could maybe be on a new JPanel centred?
}
