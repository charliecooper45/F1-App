package f1app.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Singleton, coordinating object between GUI and core system.
 * @author Charlie
 */
public enum F1Coord {
	INSTANCE;

	private List<Circuit> circuits;

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
		File file = new File(System.getProperty("user.dir") + "/src/Resources/data/data.txt");

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				createCircuit(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
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
			if(circuit.getCircuitType() == circuitType) {
				if(circuit.addLapTime(lapTime))
					return circuit;
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
