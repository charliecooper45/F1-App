package f1app.core;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class Circuit {
	private Circuits circuitType;
	private String length;
	private String lapRecord;
	private String time2013;
	private Map<Tyres, LapTime> circuitTimes;

	public Circuit(Circuits circuitType, String length, String lapRecord, String time2013) {
		this.circuitType = circuitType;
		this.length = length;
		this.lapRecord = lapRecord;
		this.time2013 = time2013;
		this.circuitTimes = new EnumMap<>(Tyres.class);
	}

	/**
	 * @return the circuitType
	 */
	public Circuits getCircuitType() {
		return circuitType;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @return the lapRecord
	 */
	public String getLapRecord() {
		return lapRecord;
	}

	/**
	 * @return the time2013
	 */
	public String getTime2013() {
		return time2013;
	}

	/**
	 * @return the circuitTimes
	 */
	public Map<Tyres, LapTime> getCircuitTimes() {
		return circuitTimes;
	}

	/**
	 * @param circuitTimes the circuitTimes to set
	 */
	public void setCircuitTimes(Map<Tyres, LapTime> circuitTimes) {
		this.circuitTimes = circuitTimes;

		// Add values for the remaining times
		for (Tyres tyre : Tyres.values()) {
			if (!circuitTimes.containsKey(tyre)) {
				circuitTimes.put(tyre, null);
			}
		}
	}

	/**
	 * Add a new lap time to the Circuits times. If a previous lap time exists then checks if the new time is faster.
	 * @param lapTime
	 * @return true if the lap times was faster and added, false if it was not.
	 */
	public boolean addLapTime(LapTime lapTime) {
		Tyres tyreChoice = lapTime.getTyreChoice();

		if (circuitTimes.containsKey(tyreChoice)) {
			LapTime previousTime = circuitTimes.get(tyreChoice);
			// Compare the two times and check the new one is faster
			if (lapTime.compareTo(previousTime) >= 0) {
				return false;
			}
		}
		circuitTimes.put(tyreChoice, lapTime);
		return true;
	}

	public boolean deleteLapTime(Tyres tyre) {
		LapTime lapTime = circuitTimes.get(tyre);

		if (lapTime == null) { 
			return false;
		} else {
			// Remove the lap time
			circuitTimes.put(tyre, null);
			return true;
		}
	}
}
