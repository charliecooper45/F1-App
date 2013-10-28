package f1app.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import f1app.core.Circuit;
import f1app.core.Circuits;
import f1app.core.F1Coord;
import f1app.core.LapTime;

public class MainFrame extends JFrame{
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
				}
			}
		});
		startScreen = new StartPanel();
		startScreen.setScreenChangeListener(new ScreenChangeListener() {
			@Override
			public void screenChanged(ScreenChangedEvent currentScreen) {
				if(currentScreen == ScreenChangedEvent.START_SCREEN) {
					mainScreen = new MainPanel();
					mainScreen.setAddLapListener(new AddLapListener() {
						@Override
						public void buttonPressed() {
							AddLapTimeDialog dialog = new AddLapTimeDialog(MainFrame.this);
							dialog.setNewLapTimeListener(new NewLapTimeListener() {
								
								@Override
								public void addLapTime(LapTime lapTime, Circuits circuit) {
									Circuit circuitChanged = f1Coord.addLapTime(lapTime, circuit);
									if(circuitChanged != null) {
										mainScreen.fireUpdateCircuitTime(circuitChanged);
									}
									else {
										System.out.println("Lap not added");
									}
								}
							});
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