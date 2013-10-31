package f1app.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import f1app.core.Circuit;
import f1app.core.Circuits;
import f1app.core.F1Coord;
import f1app.core.LapTime;
import f1app.core.Tyres;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 750;
	public static final int HEIGHT = 600;

	// The different screens to be displayed
	private StartPanel startScreen;
	private MainPanel mainScreen;

	// Object used to coordinate with core system
	private F1Coord f1Coord;

	public MainFrame() {
		super("F1 2013 Lap Time Application");
		setup();
	}

	private void setup() {
		f1Coord = F1Coord.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// TODO NEXT B: Frame not centred correctly
		//setLocationRelativeTo(null);
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				if (!f1Coord.loadCircuitData()) {
					JOptionPane.showMessageDialog(MainFrame.this, "Failed to load data.txt", "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				} else if (!f1Coord.loadLapTimes()) {
					JOptionPane.showMessageDialog(MainFrame.this, "Failed to load previous lap times", "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
				boolean timesSaved = f1Coord.saveLapTimes();
				if (!timesSaved) {
					JOptionPane.showMessageDialog(MainFrame.this, "Failed to save lap times", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		startScreen = new StartPanel();
		startScreen.setScreenChangeListener(new ScreenChangeListener() {
			@Override
			public void screenChanged(ScreenChangedEvent currentScreen) {
				if (currentScreen == ScreenChangedEvent.START_SCREEN) {
					// A listener that listens for requests to add or remove lap times
					final LapTimeChangeListener lapTimeChangeListener = new LapTimeChangeListener() {
						@Override
						public boolean addLapTime(LapTime lapTime, Circuits circuit) {
							Circuit circuitChanged = f1Coord.addLapTime(lapTime, circuit);
							if (circuitChanged != null) {
								mainScreen.fireUpdateCircuitTime(circuitChanged);
								return true;
							} else {
								return false;
							}
						}

						@Override
						public boolean deleteLapTime(Circuits circuit, Tyres tyre) {
							Circuit circuitChanged = f1Coord.deleteLapTime(circuit, tyre);
							if (circuitChanged != null) {
								mainScreen.fireUpdateCircuitTime(circuitChanged);
								return true;
							} else {
								return false;
							}
						}
					};
					mainScreen = new MainPanel();
					// Listens for clicks of the add and remove lap times buttons
					mainScreen.setLapsButtonsListener(new LapButtonsListener() {
						@Override
						public void addLapPressed() {
							LapTimeDialog dialog = new AddLapTimeDialog(MainFrame.this);
							dialog.setNewLapTimeListener(lapTimeChangeListener);
							dialog.setVisible(true);
						}

						@Override
						public void deleteLapPressed() {
							LapTimeDialog dialog = new DeleteLapTimeDialog(MainFrame.this);
							dialog.setNewLapTimeListener(lapTimeChangeListener);
							dialog.setVisible(true);
						}
					});
					remove(startScreen);
					add(mainScreen);
					revalidate();
					repaint();
				}
			}
		});
		add(startScreen);
	}
}
