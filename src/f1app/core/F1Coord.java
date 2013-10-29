package f1app.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Singleton, coordinating object between GUI and core system.
 * @author Charlie
 */
public enum F1Coord {
	INSTANCE;

	private List<Circuit> circuits;
	private static final Path TIMES_FILE = Paths.get(System.getProperty("user.dir") + "/src/Resources/data/f1app.times");
	private Map<Circuits, Map<Tyres, LapTime>> circuitsAndTimes = new HashMap<>();

	/**
	 * @return the instance of F1Coord
	 */
	public static F1Coord getInstance() {
		return F1Coord.INSTANCE;
	}

	/**
	 * Load the circuit data into the program
	 * @return if the load was successful
	 */
	public boolean loadCircuitData() {
		circuits = new ArrayList<>();
		Path path = Paths.get(System.getProperty("user.dir") + "/src/Resources/data/data.txt");

		try (Scanner scanner = new Scanner(path)) {
			while (scanner.hasNextLine()) {
				createCircuit(scanner.nextLine());
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Load the lap times from a file
	 * @return if the load operation was successful
	 */
	@SuppressWarnings("unchecked")
	public boolean loadLapTimes() {
		if (TIMES_FILE.toFile().exists()) {
			try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream((Files.newInputStream(TIMES_FILE))))) {
				circuitsAndTimes = (Map<Circuits, Map<Tyres,LapTime>>) is.readObject();
				
				for(Circuit circuit : circuits) {
					EnumMap<Tyres, LapTime> map = (EnumMap<Tyres, LapTime>) circuitsAndTimes.get(circuit.getCircuitType());
					circuit.setCircuitTimes(map);
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * Saves the lap times to a file
	 * @return if the save operation was successful
	 */
	public boolean saveLapTimes() {
		try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(TIMES_FILE)))) {
			for (Circuit circuit : circuits) {
				circuitsAndTimes.put(circuit.getCircuitType(), circuit.getCircuitTimes());
			}
			os.writeObject(circuitsAndTimes);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Returns the Circuit object that represents aCircuit
	 * @param aCircuit identifies the Circuit object
	 * @return a Circuit object
	 */
	public Circuit getCircuit(Circuits aCircuitType) {
		for (Circuit circuit : circuits) {
			if (circuit.getCircuitType().equals(aCircuitType)) return circuit;
		}
		return null;
	}

	/**
	 * Adds a lap time to the selected circuit and tyre choice. 
	 * @param circuit
	 * @param tyreChoice
	 * @return null if no circuit was changed, or the Circuit that was changed
	 */
	public Circuit addLapTime(LapTime lapTime, Circuits circuitType) {
		for (Circuit circuit : circuits) {
			if (circuit.getCircuitType() == circuitType) {
				if (circuit.addLapTime(lapTime)) return circuit;
			}
		}
		return null;
	}

	private void createCircuit(String line) {
		// TODO NEXT B: CSV file
		// Read the information about the circuit
		try (Scanner scan = new Scanner(line)) {
			scan.useDelimiter(",");

			Circuits circuit = Circuits.valueOf(scan.next());
			String length = scan.next();
			String lapRecord = scan.next();
			String time2013 = scan.next();
			circuits.add(new Circuit(circuit, length, lapRecord, time2013));
		}
	}

	//TODO NEXT: Load + Save track times (object to hold them which is serializable)
}
